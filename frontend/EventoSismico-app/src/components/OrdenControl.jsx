import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { format } from 'date-fns';

function OrdenControl() {
  const [eventos, setEventos] = useState([]);
  const [selectedEvento, setSelectedEvento] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [selectedMotivo, setSelectedMotivo] = useState(null);
  const [estadoSismografo, setEstadoSismografo] = useState(3);
  const [currentTime, setCurrentTime] = useState('');
  const [error, setError] = useState('');
  const [username, setUsername] = useState('');
  const [seriesTemporales, setSeriesTemporales] = useState([]);
  const [eventoDetalles, setEventoDetalles] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch username
    const storedUsername = localStorage.getItem('username') || 'Usuario';
    setUsername(storedUsername);

    // Check authentication
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    // Fetch pending events
    const fetchEventos = async () => {
      try {
        const response = await axios.get('/api/eventos/pendientes', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setEventos(response.data);
      } catch (err) {
        setError('Error al cargar los eventos pendientes');
        console.error('Error fetching events:', err);
      }
    };

    fetchEventos();

    // Update time
    const updateTime = () => {
      const now = new Date();
      const date = now.toLocaleDateString('es-ES');
      const time = now.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });
      setCurrentTime(`${date} ${time} hrs`);
    };
    updateTime();
    const interval = setInterval(updateTime, 10000);
    return () => clearInterval(interval);
  }, [navigate]);

  // Fetch detalles y series temporales al seleccionar evento
  useEffect(() => {
    const fetchDetallesYSeries = async () => {
      if (!selectedEvento) {
        setEventoDetalles(null);
        setSeriesTemporales([]);
        return;
      }
      try {
        const token = localStorage.getItem('token');
        const [detallesRes, seriesRes] = await Promise.all([
          axios.get(`/api/eventos/${selectedEvento.id}`, {
            headers: { Authorization: `Bearer ${token}` },
          }),
          axios.get(`/api/eventos/${selectedEvento.id}/series-temporales`, {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);
        setEventoDetalles(detallesRes.data);
        setSeriesTemporales(seriesRes.data);
      } catch (err) {
        setError('Error al cargar detalles del evento');
      }
    };
    fetchDetallesYSeries();
  }, [selectedEvento]);

  const handleConfirmObservacion = async () => {
    if (!selectedEvento) return;
    try {
      const clasificacionDTO = { descripcion: observacion };
      await axios.post(`/api/eventos/${selectedEvento.id}/clasificar`, clasificacionDTO, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      });
      setObservacion('');
      alert('Observación guardada');
    } catch (err) {
      setError('Error al guardar la observación');
    }
  };

  const handleUpdateEstado = async () => {
    if (!selectedEvento) return;
    try {
      await axios.post(
        `/api/eventos/${selectedEvento.id}/cambiar-estado`,
        {},
        {
          params: { nuevoEstadoId: estadoSismografo },
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        }
      );
      alert('Estado actualizado');
    } catch (err) {
      setError('Error al actualizar el estado');
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-gray-100 p-6">
      <div className="flex justify-between bg-gray-300 p-4 rounded-t-lg">
        <span>{username}</span>
        <span>Estado Orden: ABIERTO</span>
        <span>{currentTime}</span>
        <button
          onClick={() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }}
          className="bg-red-600 text-white hover:bg-red-800 px-4 py-1 rounded"
        >
          Cerrar Sesión
        </button>
      </div>
      <div className="flex flex-1 mt-4 gap-4">
        {/* Columna izquierda: eventos */}
        <div className="bg-gray-200 rounded-lg p-2 flex flex-col gap-2 w-1/4">
          {eventos.length === 0 ? (
            <p className="text-gray-500">No hay eventos pendientes</p>
          ) : (
            eventos.map((evento) => (
              <button
                key={evento.id}
                className={`p-2 rounded ${
                  selectedEvento?.id === evento.id ? 'bg-blue-700 text-white' : 'bg-gray-300 hover:bg-gray-400'
                }`}
                onClick={() => setSelectedEvento(evento)}
              >
                Evento {evento.id} - {format(new Date(evento.fechaHoraOcurrencia), 'dd/MM/yyyy HH:mm')}
                <div className="text-xs text-gray-700">
                  Estado: {evento.estadoEventoId === 1 ? 'AutoDetectado' : evento.estadoEventoId === 2 ? 'PendienteRevision' : 'Otro'}
                </div>
              </button>
            ))
          )}
        </div>
        {/* Centro: detalles del evento o mensaje de selección */}
        <div className="flex-1 flex flex-col gap-4">
          {error && <p className="text-red-500">{error}</p>}
          {!selectedEvento ? (
            <div className="flex-1 flex flex-col items-center justify-center bg-white rounded shadow p-8">
              <span className="text-gray-500 text-lg">Seleccione un evento sísmico para ver los detalles</span>
            </div>
          ) : (
            <div className="flex-1 flex flex-col gap-2 bg-white rounded shadow p-4">
              <h2 className="font-bold text-lg mb-2">Datos del Evento Sísmico</h2>
              <div className="grid grid-cols-2 gap-2">
                <div><b>ID:</b> {eventoDetalles?.id}</div>
                <div><b>Fecha/Hora:</b> {eventoDetalles?.fechaHoraOcurrencia ? format(new Date(eventoDetalles.fechaHoraOcurrencia), 'dd/MM/yyyy HH:mm') : ''}</div>
                <div><b>Magnitud:</b> {eventoDetalles?.magnitud}</div>
                <div><b>Alcance:</b> {eventoDetalles?.alcance}</div>
                <div><b>Origen:</b> {eventoDetalles?.origenGeneracion}</div>
                <div><b>Estado:</b> {eventoDetalles?.estadoEventoId}</div>
              </div>
              <div className="mt-4">
                <label className="font-medium">Ingresar observación:</label>
                <textarea
                  className="w-full border rounded h-24 mt-1 p-2 resize-none"
                  value={observacion}
                  onChange={(e) => setObservacion(e.target.value)}
                />
                <div className="flex justify-end gap-4 mt-2">
                  <button
                    onClick={handleConfirmObservacion}
                    className="bg-gray-600 text-white hover:bg-gray-700 px-4 py-1 rounded"
                  >
                    Confirmar
                  </button>
                  <button
                    onClick={() => setObservacion('')}
                    className="bg-gray-400 text-white hover:bg-gray-700 px-4 py-1 rounded"
                  >
                    Cancelar
                  </button>
                </div>
              </div>
              <div className="bg-gray-400 rounded p-4 flex items-center justify-between mt-4">
                <div className="text-white justify-start">Tipo de motivos Fuera de Servicio</div>
                <div className="text-white bg-gray-700 px-4 py-2 rounded">Motivo 1</div>
                <button className="bg-gray-300 px-4 hover:bg-gray-700 py-2 rounded">Comentario</button>
              </div>
            </div>
          )}
          {/* Parte inferior: detalles del sismo y series temporales */}
          <div className="mt-4 bg-white rounded shadow p-4">
            {!selectedEvento ? (
              <span className="text-gray-500">Seleccione un evento para ver los datos detallados del sismo y sus series temporales</span>
            ) : (
              <>
                <h3 className="font-semibold mb-2">Datos Detallados del Sismo</h3>
                <div className="grid grid-cols-2 gap-2 mb-2">
                  <div><b>Latitud Epicentro:</b> {eventoDetalles?.latitudEpicentro}</div>
                  <div><b>Longitud Epicentro:</b> {eventoDetalles?.longitudEpicentro}</div>
                  <div><b>Latitud Hipocentro:</b> {eventoDetalles?.latitudHipocentro}</div>
                  <div><b>Longitud Hipocentro:</b> {eventoDetalles?.longitudHipocentro}</div>
                  <div><b>Fecha Fin:</b> {eventoDetalles?.fechaHoraFin ? format(new Date(eventoDetalles.fechaHoraFin), 'dd/MM/yyyy HH:mm') : ''}</div>
                </div>
                <h4 className="font-semibold mt-4 mb-2">Series Temporales</h4>
                {seriesTemporales.length === 0 ? (
                  <span className="text-gray-500">No hay series temporales para este evento</span>
                ) : (
                  <table className="w-full text-sm border">
                    <thead>
                      <tr className="bg-gray-200">
                        <th>ID</th>
                        <th>Frecuencia Muestreo</th>
                        <th>Condición Alarma</th>
                        <th>Fecha Registro</th>
                      </tr>
                    </thead>
                    <tbody>
                      {seriesTemporales.map((serie) => (
                        <tr key={serie.id} className="border-t">
                          <td>{serie.id}</td>
                          <td>{serie.frecuenciaMuestreo}</td>
                          <td>{serie.condicionAlarma}</td>
                          <td>{serie.fechaHoraRegistroMuestra ? format(new Date(serie.fechaHoraRegistroMuestra), 'dd/MM/yyyy HH:mm') : ''}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}
              </>
            )}
          </div>
        </div>
        {/* Derecha: situación sismógrafo */}
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Evento Sismico</h3>
            {[0, 1, 2, 3, 4].map((id) => (
              <div key={id} className="flex items-center gap-2 mb-2">
                <div
                  className={`w-4 h-4 rounded-full border cursor-pointer ${
                    estadoSismografo === id ? 'bg-green-600 border-black' : 'bg-gray-400'
                  }`}
                  onClick={() => setEstadoEvento(id)}
                />
                <span>Estado {id}</span>
              </div>
            ))}
            <button
              onClick={handleUpdateEstado}
              className="bg-gray-600 text-white w-full hover:bg-gray-700 mt-2 py-1 rounded"
            >
              Actualizar
            </button>
          </div>
        </div>
      </div>
      {/* Botón Generar Simograma debajo de todo */}
      <div className="flex justify-center mt-8">
        <button className="bg-blue-600 text-white hover:bg-blue-800 px-8 py-3 rounded text-lg shadow">
          Generar Simograma
        </button>
      </div>
    </div>
  );
}

export default OrdenControl;