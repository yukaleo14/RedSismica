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

<<<<<<< HEAD
=======
  // Map state labels to estadoEventoId
  const estadoMap = {
    'Pendiente': 1,
    'Autodetectado': 2,
    'Bloqueado En Revision': 3,
    'Rechazado': 4,
  };

  // Utility to format dates safely
/*   const formatDate = (dateString) => {
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
}; */
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

  // Handle ISO string or other standard date formats
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

>>>>>>> aacb70f77a47ad518b720ff9c65cc65ded1ec49b
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
        await axios.get('/api/eventos/pendientes', {
          headers: { Authorization: `Bearer ${token}` },
        }).then(res => setEventos(res.data));
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
<<<<<<< HEAD
    if (selectedEvento) {
      const token = localStorage.getItem('token');
      fetch(`/api/eventos/${selectedEvento.id}`, {
        headers: { Authorization: `Bearer ${token}` }
      })
        .then(res => {
          if (!res.ok) throw new Error('No autorizado');
          return res.json();
        })
        .then(data => setEventoDetalles(data))
        .catch(() => setEventoDetalles(null));

      fetch(`/api/series/${selectedEvento.id}/series-temporales`, {
        headers: { Authorization: `Bearer ${token}` }
      })
        .then(res => {
          if (!res.ok) throw new Error('No autorizado');
          return res.json();
        })
        .then(data => setSeriesTemporales(data))
        .catch(() => setSeriesTemporales([]));
    } else {
=======
    if (!selectedEvento) {
      console.log('No evento seleccionado');
>>>>>>> aacb70f77a47ad518b720ff9c65cc65ded1ec49b
      setEventoDetalles(null);
      setSeriesTemporales([]);
    }
<<<<<<< HEAD
  }, [selectedEvento]);
=======

    const token = localStorage.getItem('token');
    console.log('Fetching details for evento ID:', selectedEvento.id);

    // Fetch event details
    const fetchEventoDetalles = async () => {
      try {
        const response = await axios.get(`/api/eventos/${selectedEvento.id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        console.log('Evento detalles response:', JSON.stringify(response.data, null, 2)); // Debug log
        setEventoDetalles(response.data);
      } catch (err) {
        console.error('Error fetching event details:', err);
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
        console.log('Series temporales response:', JSON.stringify(response.data, null, 2)); // Debug log
        setSeriesTemporales(response.data);
      } catch (err) {
        console.error('Error fetching time series:', err);
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
>>>>>>> aacb70f77a47ad518b720ff9c65cc65ded1ec49b

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
                <div className="text-xs text-gray-700">
                  Estado: {evento.estadoEventoId === 1 ? 'AutoDetectado' : evento.estadoEventoId === 2 ? 'PendienteRevision' : 'Otro'}
                </div>
              </button>
            ))
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
                <div><b>Fecha/Hora:</b> {eventoDetalles?.fechaHoraOcurrencia ? format(new Date(eventoDetalles.fechaHoraOcurrencia), 'dd/MM/yyyy HH:mm') : ''}</div>
                <div><b>Origen:</b> {eventoDetalles?.origenGeneracion}</div>
                <div><b>Magnitud:</b> {eventoDetalles?.magnitud}</div>
              </div>
            </div>
          )}
        </div>
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Evento Sismico</h3>
            {["Pendiente Revision", "Autodetectado", "Bloqueado En Revision", "Rechazado"].map((id) => (
              <div key={id} className="flex items-center gap-2 mb-2">
                <div
                  className={`w-4 h-4 rounded-full border cursor-pointer ${
                    estadoSismografo === id ? 'bg-green-600 border-black' : 'bg-gray-400'
                  }`}
                  onClick={() => setEstadoSismografo(id)} // Corrección aquí
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
      <div className="mt-4 bg-white rounded shadow p-4 w-full">
        {!selectedEvento ? (
          <span className="text-gray-500">Seleccione un evento para ver los datos detallados del sismo y sus series temporales</span>
        ) : (
          <div className="grid grid-cols-3 gap-4">
            {/* Datos Sismo */}
            <div className="col-span-1">
              <h3 className="font-semibold mb-2">Datos Sismo</h3>
              <div className="grid grid-cols-2 gap-2 mb-2">
                <div><b>Alcance:</b> {eventoDetalles?.alcance}</div>
                <div><b>Clasificación:</b> {eventoDetalles?.clasificacion}</div>
                <div><b>Origen de Generación:</b> {eventoDetalles?.origenGeneracion}</div>
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
                      <h4 className="font-medium">Estación: {serie.estacionSismologica}</h4>
                      <div><b>Velocidad de Onda:</b> {serie.velocidadOnda || 'N/A'}</div>
                      <div><b>Frecuencia de Onda:</b> {serie.frecuenciaOnda || 'N/A'}</div>
                      <div><b>Longitud:</b> {serie.longitud || 'N/A'}</div>
                    </div>
                  ))}
                </div>
              )}
            </div>
            {/* Visualizar Simograma */}
            <div className="col-span-1">
              <h3 className="font-semibold mb-2">Visualizar Simograma</h3>
              <p id="sismograma-output" className="mb-4">No se ha generado el sismograma aún...</p>
              <div className="mt-4 flex justify-center">
                <button
                  className="bg-blue-600 text-white hover:bg-blue-800 px-8 py-3 rounded text-lg shadow"
                  onClick={() => {
                    // Llamada simulada al caso de uso Generar Sismograma
                    const output = document.getElementById('sismograma-output');
                    output.textContent = 'Generando sismograma...';
                    setTimeout(() => {
                      output.textContent = 'Sismograma generado con éxito para las estaciones.';
                    }, 2000); // Simulación de 2 segundos para generación
                  }}
                >
                  Generar Simograma
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default OrdenControl;