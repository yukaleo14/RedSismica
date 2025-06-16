import { useEffect, useState } from 'react';
import axios from 'axios';
import { API_BASE_URL } from '../config';

function OrdenControl() {
  const [eventos, setEventos] = useState([]);
  const [selectedEvento, setSelectedEvento] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [selectedMotivo, setSelectedMotivo] = useState(null);
  const [estadoSismografo, setEstadoSismografo] = useState(3); // Default state
  const [currentTime, setCurrentTime] = useState('');
  const [error, setError] = useState('');

  // Fetch pending seismic events
  useEffect(() => {
    const fetchEventos = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/pendientes`, {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        });
        setEventos(response.data);
      } catch (err) {
        setError('Error fetching events');
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
  }, []);

  // Handle observation submission
  const handleConfirmObservacion = async () => {
    if (!selectedEvento) return;
    try {
      const clasificacionDTO = { descripcion: observacion }; // Adjust based on ClasificacionDTO
      await axios.post(`${API_BASE_URL}/${selectedEvento.id}/clasificar`, clasificacionDTO, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      });
      setObservacion('');
      alert('Observación guardada');
    } catch (err) {
      setError('Error saving observation');
    }
  };

  // Handle seismograph status update
  const handleUpdateEstado = async () => {
    if (!selectedEvento) return;
    try {
      await axios.post(
        `${API_BASE_URL}/${selectedEvento.id}/cambiar-estado`,
        {},
        {
          params: { nuevoEstadoId: estadoSismografo },
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        }
      );
      alert('Estado actualizado');
    } catch (err) {
      setError('Error updating status');
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-gray-100 p-6">
      {/* Header */}
      <div className="flex justify-between bg-gray-300 p-4 rounded-t-lg">
        <span>Nombre Usuario</span>
        <span>Estado Orden: ABIERTO</span>
        <span>{currentTime}</span>
      </div>
      {/* Main Content */}
      <div className="flex flex-1 mt-4 gap-4">
        {/* Left Panel: Event List */}
        <div className="bg-gray-200 rounded-lg p-2 flex flex-col gap-2 w-1/4">
          {eventos.map((evento) => (
            <button
              key={evento.id}
              className={`p-2 rounded ${
                selectedEvento?.id === evento.id ? 'bg-blue-700 text-white' : 'bg-gray-300 hover:bg-gray-400'
              }`}
              onClick={() => setSelectedEvento(evento)}
            >
              Evento {evento.id} - {evento.fechaHora}
            </button>
          ))}
        </div>

        {/* Central Panel */}
        <div className="flex-1 flex flex-col gap-4">
          {error && <p className="text-red-500">{error}</p>}
          {/* Observation */}
          <div>
            <label className="font-medium">Ingresar observación:</label>
            <textarea
              className="w-full border rounded h-24 mt-1 p-2 resize-none"
              value={observacion}
              onChange={(e) => setObservacion(e.target.value)}
            />
            <div className="flex justify-end gap-4 mt-2">
              <button
                onClick={handleConfirmObservacion}
                className="bg-gray-600 text-white px-4 py-1 rounded"
              >
                Confirmar
              </button>
              <button
                onClick={() => setObservacion('')}
                className="bg-gray-400 text-white px-4 py-1 rounded"
              >
                Cancelar
              </button>
            </div>
          </div>

          {/* Motivo (Placeholder for future implementation) */}
          <div className="bg-gray-400 rounded p-4 flex items-center justify-between">
            <div className="text-white justify-start">Tipo de motivos Fuera de Servicio</div>
            <div className="text-white bg-gray-700 px-4 py-2 rounded">Motivo 1</div>
            <button className="bg-gray-300 px-4 hover:bg-gray-700 py-2 rounded">Comentario</button>
          </div>
        </div>

        {/* Right Panel: Seismograph Status */}
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Sismógrafo</h3>
            {[0, 1, 2, 3, 4].map((id) => (
              <div key={id} className="flex items-center gap-2 mb-2">
                <div
                  className={`w-4 h-4 rounded-full border cursor-pointer ${
                    estadoSismografo === id ? 'bg-green-600 border-black' : 'bg-gray-400'
                  }`}
                  onClick={() => setEstadoSismografo(id)}
                />
                <span>Estado {id}</span>
              </div>
            ))}
            <button
              onClick={handleUpdateEstado}
              className="bg-gray-600 text-white w-full mt-2 py-1 rounded"
            >
              Actualizar
            </button>
          </div>
        </div>
      </div>

      {/* Confirm Order */}
      <div className="flex justify-end mt-4">
        <button className="bg-gray-800 text-white px-6 py-2 rounded">
          CONFIRMAR ORDEN DE INSPECCIÓN
        </button>
      </div>
    </div>
  );
}

export default OrdenControl;
