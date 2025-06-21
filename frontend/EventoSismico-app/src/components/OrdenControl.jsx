import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { format, isValid } from 'date-fns';

function OrdenControl() {
  const [eventos, setEventos] = useState([]);
  const [selectedEvento, setSelectedEvento] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [estadoSismografo, setEstadoSismografo] = useState(3);
  const [currentTime, setCurrentTime] = useState('');
  const [error, setError] = useState('');
  const [username, setUsername] = useState('');
  const [seriesTemporales, setSeriesTemporales] = useState([]);
  const [eventoDetalles, setEventoDetalles] = useState(null);
  const navigate = useNavigate();

  // Map state labels to estadoEventoId
  const estadoMap = {
    'Pendiente': 1,
    'Autodetectado': 2,
    'Bloqueado En Revision': 3,
    'Rechazado': 4,
  };

  // Utility to format dates safely
  const formatDate = (dateString) => {
  if (!dateString) return '';

  // Handle comma-separated format like "2025,6,20,12,0"
  if (typeof dateString === 'string' && dateString.includes(',')) {
    try {
      const parts = dateString.split(',').map(Number);
      if (parts.length !== 5) {
        console.warn(`Invalid date parts: ${dateString}`);
        return 'Fecha inválida';
      }
      const [year, month, day, hour, minute] = parts;
      // JavaScript months are 0-based, so subtract 1 from month
      const date = new Date(year, month - 1, day, hour, minute);
      if (!isValid(date)) {
        console.warn(`Invalid parsed date: ${dateString}`);
        return 'Fecha inválida';
      }
      return format(date, 'dd/MM/yyyy HH:mm');
    } catch (err) {
      console.warn(`Error parsing date: ${dateString}`, err);
      return 'Fecha inválida';
    }
  }

  // Handle standard date strings
  const date = new Date(dateString);
  if (!isValid(date)) {
    console.warn(`Invalid date: ${dateString}`);
    return 'Fecha inválida';
  }
  return format(date, 'dd/MM/yyyy HH:mm');
};

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
        console.log('Eventos pendientes response:', JSON.stringify(response.data, null, 2)); // Detailed log
        setEventos(response.data);
      } catch (err) {
        if (err.response?.status === 403) {
          setError('Sesión expirada. Por favor, inicia sesión nuevamente.');
          setTimeout(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }, 3000); // Delay redirect to show error
        } else {
          setError('Error al cargar los eventos pendientes');
          console.error('Error fetching events:', err);
        }
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

  useEffect(() => {
    if (!selectedEvento) {
      setEventoDetalles(null);
      setSeriesTemporales([]);
      return;
    }

    const token = localStorage.getItem('token');

    // Fetch event details
    const fetchEventoDetalles = async () => {
      try {
        const response = await axios.get(`/api/eventos/${selectedEvento.id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log('Evento detalles response:', response.data); // Debug log
        setEventoDetalles(response.data);
      } catch (err) {
        if (err.response?.status === 403) {
          setError('Sesión expirada. Por favor, inicia sesión nuevamente.');
          setTimeout(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }, 3000); // Delay redirect to show error
        } else {
          setError('Error al cargar los detalles del evento');
          console.error('Error fetching event details:', err);
          setEventoDetalles(null);
        }
      }
    };

    // Fetch time series
    const fetchSeriesTemporales = async () => {
      try {
        const response = await axios.get(`/api/eventos/${selectedEvento.id}/series-temporales`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log('Series temporales response:', response.data); // Debug log
        setSeriesTemporales(response.data);
      } catch (err) {
        if (err.response?.status === 403) {
          setError('Sesión expirada. Por favor, inicia sesión nuevamente.');
          setTimeout(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }, 3000); // Delay redirect to show error
        } else {
          setError('Error al cargar las series temporales');
          console.error('Error fetching time series:', err);
          setSeriesTemporales([]);
        }
      }
    };

    fetchEventoDetalles();
    fetchSeriesTemporales();
  }, [selectedEvento, navigate]);

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
      console.error('Error saving observation:', err);
    }
  };

  const handleUpdateEstado = async () => {
    if (!selectedEvento) return;
    try {
      const nuevoEstadoId = estadoMap[estadoSismografo] || estadoSismografo;
      await axios.post(
        `/api/eventos/${selectedEvento.id}/cambiar-estado`,
        {},
        {
          params: { nuevoEstadoId },
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        }
      );
      alert('Estado actualizado');
    } catch (err) {
      setError('Error al actualizar el estado');
      console.error('Error updating state:', err);
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
                Evento {evento.id} - {formatDate(evento.fechaHoraOcurrencia)}
                <div className="text-xs text-gray-700">
                  Estado: {evento.estadoEventoId === 1 ? 'Pendiente' : evento.estadoEventoId === 2 ? 'Autodetectado' : 'Otro'}
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
              {eventoDetalles ? (
                <div className="grid grid-cols-2 gap-2">
                  <div><b>ID:</b> {eventoDetalles.id}</div>
                  <div>
                    <b>Fecha/Hora:</b> {formatDate(eventoDetalles.fechaHoraOcurrencia)}
                  </div>
                  <div><b>Magnitud:</b> {eventoDetalles.magnitud || 'N/A'}</div>
                  <div><b>Alcance:</b> {eventoDetalles.alcance || 'N/A'}</div>
                  <div><b>Origen:</b> {eventoDetalles.origenGeneracion || 'N/A'}</div>
                  <div>
                    <b>Estado:</b>{' '}
                    {eventoDetalles.estadoEventoId === 1
                      ? 'Pendiente'
                      : eventoDetalles.estadoEventoId === 2
                      ? 'Autodetectado'
                      : 'Otro'}
                  </div>
                </div>
              ) : (
                <p className="text-gray-500">Cargando detalles...</p>
              )}
            </div>
          )}
        </div>
        {/* Derecha: situación sismógrafo */}
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Evento Sísmico</h3>
            {['Pendiente', 'Autodetectado', 'Bloqueado En Revision', 'Rechazado'].map((estado) => (
              <div key={estado} className="flex items-center gap-2 mb-2">
                <div
                  className={`w-4 h-4 rounded-full border cursor-pointer ${
                    estadoSismografo === estado ? 'bg-green-600 border-black' : 'bg-gray-400'
                  }`}
                  onClick={() => setEstadoSismografo(estado)}
                />
                <span>{estado}</span>
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
      <div className="w-full flex justify-center mt-4">
        {/* Parte inferior: detalles del sismo y series temporales */}
        <div className="mt-4 bg-white rounded shadow p-4 w-full">
          {!selectedEvento ? (
            <span className="text-gray-500">Seleccione un evento para ver los datos detallados del sismo y sus series temporales</span>
          ) : (
            <>
              <h3 className="font-semibold mb-2">Datos Detallados del Sismo</h3>
              {eventoDetalles ? (
                <div className="grid grid-cols-2 gap-2 mb-2">
                  <div><b>Latitud Epicentro:</b> {eventoDetalles.latitudEpicentro || 'N/A'}</div>
                  <div><b>Longitud Epicentro:</b> {eventoDetalles.longitudEpicentro || 'N/A'}</div>
                  <div><b>Latitud Hipocentro:</b> {eventoDetalles.latitudHipocentro || 'N/A'}</div>
                  <div><b>Longitud Hipocentro:</b> {eventoDetalles.longitudHipocentro || 'N/A'}</div>
                  <div>
                    <b>Fecha Fin:</b> {formatDate(eventoDetalles.fechaHoraFin)}
                  </div>
                </div>
              ) : (
                <p className="text-gray-500">Cargando datos detallados...</p>
              )}
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
                        <td>{serie.frecuenciaMuestreo || 'N/A'}</td>
                        <td>{serie.condicionAlarma || 'N/A'}</td>
                        <td>{formatDate(serie.fechaHoraRegistroMuestra)}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              )}
            </>
          )}
        </div>
      </div>
      {/* Botón Generar Simograma */}
      <div className="flex justify-center mt-8">
        <button className="bg-blue-600 text-white hover:bg-blue-800 px-8 py-3 rounded text-lg shadow">
          Generar Simograma
        </button>
      </div>
    </div>
  );
}

export default OrdenControl;