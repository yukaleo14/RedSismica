import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { format, isValid } from 'date-fns';

function OrdenControl() {
  const [eventos, setEventos] = useState([]);
  const [selectedEvento, setSelectedEvento] = useState(null);
  const [confirmedEvento, setConfirmedEvento] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [estadoSismografo, setEstadoSismografo] = useState('Pendiente'); // Estado inicial como Pendiente
  const [currentTime, setCurrentTime] = useState('');
  const [error, setError] = useState('');
  const [username, setUsername] = useState('');
  const [seriesTemporales, setSeriesTemporales] = useState([]);
  const [eventoDetalles, setEventoDetalles] = useState(null);
  const [revisionData, setRevisionData] = useState(null);
  const [actionCompleted, setActionCompleted] = useState(false); // Nuevo estado para rastrear si se completó una acción final
  const navigate = useNavigate();

  const estadoMap = {
    'Pendiente': 1,
    'Autodetectado': 2,
    'Bloqueado En Revision': 3,
    'Rechazado': 4,
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    if (Array.isArray(dateString)) {
      try {
        const [year, month, day, hour, minute] = dateString.map(Number);
        const date = new Date(year, month - 1, day, hour, minute);
        if (!isValid(date)) {
          console.warn(`Invalid parsed date: ${dateString}`);
          return 'Fecha inválida';
        }
        return format(date, 'dd/MM/yyyy HH:mm');
      } catch (err) {
        console.warn(`Error parsing array date: ${dateString}`, err);
        return 'Fecha inválida';
      }
    }
    try {
      const date = new Date(dateString);
      if (!isValid(date)) {
        console.warn(`Invalid date: ${dateString}`);
        return 'Fecha inválida';
      }
      return format(date, 'dd/MM/yyyy HH:mm');
    } catch (err) {
      console.warn(`Error parsing date: ${dateString}`, err);
      return 'Fecha inválida';
    }
  };

  useEffect(() => {
    const storedUsername = localStorage.getItem('username') || 'Usuario';
    setUsername(storedUsername);

    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }

    const fetchEventos = async () => {
      try {
        const res = await axios.get('/api/eventos/pendientes', {
          headers: { Authorization: `Bearer ${token}` },
        });
        // Asegurarse de que todos los eventos tengan estado Pendiente inicialmente
        const updatedEventos = res.data.map(evento => ({
          ...evento,
          estadoEventoId: 1, // Pendiente
        }));
        setEventos(updatedEventos);
      } catch (err) {
        setError('Error al cargar los eventos pendientes');
        console.error('Error fetching events:', err);
      }
    };

    fetchEventos();

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
      setConfirmedEvento(null);
      setRevisionData(null);
      setActionCompleted(false);
      return;
    }

    const token = localStorage.getItem('token');
    console.log('Fetching details for evento ID:', selectedEvento.id);

    const fetchEventoDetalles = async () => {
      try {
        const response = await axios.get(`/api/eventos/${selectedEvento.id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log('Evento detalles response:', JSON.stringify(response.data, null, 2));
        setEventoDetalles(response.data);
      } catch (err) {
        console.error('Error fetching event details:', err);
        if (err.response?.status === 403) {
          setError('Sesión expirada. Por favor, inicia sesión nuevamente.');
          setTimeout(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }, 3000);
        } else {
          setError('Error al cargar los detalles del evento');
          setEventoDetalles(null);
        }
      }
    };

    const fetchSeriesTemporales = async () => {
      try {
        const response = await axios.get(`/api/eventos/${selectedEvento.id}/series-temporales`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log('Series temporales response:', JSON.stringify(response.data, null, 2));
        setSeriesTemporales(response.data);
      } catch (err) {
        console.error('Error fetching time series:', err);
        if (err.response?.status === 403) {
          setError('Sesión expirada. Por favor, inicia sesión nuevamente.');
          setTimeout(() => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
            navigate('/login');
          }, 3000);
        } else {
          setError('Error al cargar las series temporales');
          setSeriesTemporales([]);
        }
      }
    };

    fetchEventoDetalles();
    fetchSeriesTemporales();
  }, [selectedEvento, navigate]);

  const handleConfirmSelection = () => {
    setConfirmedEvento(selectedEvento);
    setEstadoSismografo('Bloqueado En Revision'); // Cambiar estado al confirmar
    // Aquí podrías agregar una llamada a la API para actualizar el estado
  };

  const handleFinalAction = () => {
    setActionCompleted(true); // Marca que se completó una acción final
    setConfirmedEvento(null); // Permite volver a seleccionar eventos
    setEstadoSismografo('Pendiente'); // Restablece el estado inicial
    // Aquí podrías agregar una llamada a la API para guardar el estado final
  };

  const handleRejectEvent = () => {
    setRevisionData({
      responsable: username,
      fechaHoraRevision: format(new Date(), 'dd/MM/yyyy HH:mm'),
    });
    setEstadoSismografo('Rechazado');
    handleFinalAction();
    alert('Evento rechazado y actualizado.');
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
        <div className="bg-gray-200 rounded-lg p-2 flex flex-col gap-2 w-1/4">
          {eventos.length === 0 ? (
            <p className="text-gray-500">No hay eventos pendientes</p>
          ) : (
            eventos.map((evento, index) => (
              <button
                key={evento.id}
                className={`p-2 rounded ${
                  selectedEvento?.id === evento.id ? 'bg-[#29675B] text-white' : 'bg-gray-300 hover:bg-gray-400'
                }`}
                onClick={() => {
                  if (!confirmedEvento || actionCompleted) {
                    setSelectedEvento(evento);
                    setActionCompleted(false); // Restablecer al cambiar evento
                  }
                }}
                disabled={confirmedEvento && !actionCompleted}
              >
                <div className="text-xs flex items-center">
                  <span className="bg-gray-600 text-white px-2 py-1 rounded mr-2">
                    EventoSismico {index + 1}
                  </span>
                  <span>{formatDate(evento.fechaHoraOcurrencia)}</span>
                </div>
              </button>
            ))
          )}
          {selectedEvento && !confirmedEvento && (
            <button
              className="bg-[#ADBAC0] text-white hover:bg-[#29675B] px-4 py-2 rounded mt-2"
              onClick={handleConfirmSelection}
            >
              Confirmar Selección
            </button>
          )}
        </div>
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
                <div><b>Fecha/Hora:</b> {formatDate(eventoDetalles?.fechaHoraOcurrencia)}</div>
                <div><b>Ubicación:</b> {eventoDetalles?.ubicacion}</div>
                <div><b>Magnitud:</b> {eventoDetalles?.magnitud}</div>
              </div>
              {revisionData && (
                <div className="mt-2 p-2 bg-gray-100 rounded">
                  <div><b>Responsable:</b> {revisionData.responsable}</div>
                  <div><b>Fecha y Hora Revisión:</b> {revisionData.fechaHoraRevision}</div>
                </div>
              )}
            </div>
          )}
        </div>
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Evento Sismico</h3>
            {["Pendiente", "Autodetectado", "Bloqueado En Revision", "Rechazado"].map((estado) => (
              <div key={estado} className="flex items-center gap-2 mb-2">
                <span
                  className={`px-2 py-1 rounded ${
                    estadoSismografo === estado
                      ? estado === 'Bloqueado En Revision'
                        ? 'bg-red-600 text-white'
                        : estado === 'Rechazado'
                        ? 'bg-green-600 text-white'
                        : 'bg-[#ADBAC0] text-white'
                      : 'bg-[#373737] text-white'
                  }`}
                >
                  {estado}
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>
      <div className="mt-4 bg-white rounded shadow p-4 w-full">
        {!selectedEvento ? (
          <span className="text-gray-500">Seleccione un evento para ver los datos detallados del sismo y sus series temporales</span>
        ) : (
          <div className="grid grid-cols-3 gap-4">
            {/* Datos Sismo */}
            <div className="col-span-1">
              <h3 className="font-semibold mb-2">Datos Sismo</h3>
              <div className="grid grid-cols-2 gap-2 mb-2">
                <div><b>Alcance:</b> {eventoDetalles?.alcance || 'N/A'}</div>
                <div><b>Clasificación:</b> {eventoDetalles?.clasificacion || 'N/A'}</div>
                <div><b>Origen de Generación:</b> {eventoDetalles?.origenGeneracion || 'N/A'}</div>
                <div><b>Magnitud:</b> {eventoDetalles?.magnitud || 'N/A'}</div>
              </div>
              <div className="mt-4 flex flex-col gap-2">
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-[#bf6777] hover:bg-[#29675B]' : 'bg-[#373737]'
                  }`}
                  onClick={() => confirmedEvento && alert('Modificar datos activado')}
                  disabled={!confirmedEvento}
                >
                  Modificar Magnitud, Alcance y Origen
                </button>
              </div>
            </div>
            {/* Situación Evento Sísmico (Series Temporales) */}
            <div className="col-span-1">
              <h3 className="font-semibold mb-2">Situación Evento Sísmico</h3>
              {seriesTemporales.length === 0 ? (
                <p className="text-gray-500">No hay series temporales para este evento</p>
              ) : (
                <div className="flex flex-col gap-2">
                  {seriesTemporales.map((serie) => (
                    <div key={serie.id} className="border p-2 rounded">
                      <h4 className="font-medium">Estación: {serie.estacionSismologica || 'Sin estación'}</h4>
                      <div><b>Velocidad de Onda:</b> {serie.velocidadOnda || 'N/A'}</div>
                      <div><b>Frecuencia de Onda:</b> {serie.frecuenciaOnda || 'N/A'}</div>
                      <div><b>Longitud:</b> {serie.longitud || 'N/A'}</div>
                    </div>
                  ))}
                </div>
              )}
              <div className="mt-4 flex flex-col gap-2">
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-[#415f6e] hover:bg-[#29675B]' : 'bg-[#373737]'
                  }`}
                  onClick={() => {
                    if (confirmedEvento) {
                      handleFinalAction();
                      alert('Evento confirmado');
                    }
                  }}
                  disabled={!confirmedEvento}
                >
                  Confirmar Evento
                </button>
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-[#415f6e] hover:bg-[#29675B]' : 'bg-[#373737]'
                  }`}
                  onClick={handleRejectEvent}
                  disabled={!confirmedEvento}
                >
                  Rechazar Evento
                </button>
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-[#415f6e] hover:bg-[#29675B]' : 'bg-[#373737]'
                  }`}
                  onClick={() => {
                    if (confirmedEvento) {
                      handleFinalAction();
                      alert('Revisión solicitada a experto');
                    }
                  }}
                  disabled={!confirmedEvento}
                >
                  Solicitar Revisión a Experto
                </button>
              </div>
            </div>
            {/* Visualizar Simograma */}
            <div className="col-span-1">
              <h3 className="font-semibold mb-2">Visualizar Simograma</h3>
              <p id="sismograma-output" className="mb-4">No se ha generado el sismograma aún...</p>
              <div className="mt-4 flex flex-col gap-2">
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-blue-600 hover:bg-blue-800' : 'bg-[#373737]'
                  }`}
                  onClick={() => {
                    if (confirmedEvento) {
                      const output = document.getElementById('sismograma-output');
                      output.textContent = 'Generando sismograma...';
                      setTimeout(() => {
                        output.textContent = 'Sismograma generado con éxito para las estaciones.';
                      }, 2000);
                    }
                  }}
                  disabled={!confirmedEvento}
                >
                  Generar Simograma
                </button>
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-indigo-600 hover:bg-indigo-800' : 'bg-[#373737]'
                  }`}
                  onClick={() => confirmedEvento && alert('Visualizar en mapa')}
                  disabled={!confirmedEvento}
                >
                  Visualizar en Mapa
                </button>
                <button
                  className={`text-white px-3 py-1.5 rounded text-sm font-medium ${
                    confirmedEvento ? 'bg-gray-600 hover:bg-gray-800' : 'bg-[#373737]'
                  }`}
                  onClick={() => confirmedEvento && alert('No visualizar en mapa')}
                  disabled={!confirmedEvento}
                >
                  No Visualizar en Mapa
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

// Asegúrate de importar isValid si no está disponible globalmente
// import { isValid } from 'date-fns';

export default OrdenControl;