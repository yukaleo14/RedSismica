import { useEffect, useState } from "react";

function OrdenControl() {
  const [selectedOrden, setSelectedOrden] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [selectedMotivo, setSelectedMotivo] = useState(null);
  const [estadoSismografo, setEstadoSismografo] = useState(3); // ejemplo activo
  const motivos = ['Motivo 1'];

  const ordenes = ['Orden de Inspección 1', 'Orden de Inspección 1', 'Orden de Inspección 1', 'Orden de Inspección 1'];

   const [currentTime, setCurrentTime] = useState("");

  useEffect(() => {
    const updateTime = () => {
      const now = new Date();
      const date = now.toLocaleDateString("es-ES");
      const time = now.toLocaleTimeString("es-ES", {
        hour: "2-digit",
        minute: "2-digit",
      });
      setCurrentTime(`${date} ${time} hrs`);
    };

    updateTime(); // inicial
    const interval = setInterval(updateTime, 10000); // cada 10 seg

    return () => clearInterval(interval);
  }, []);


  return (
    <div className="min-h-screen flex flex-col bg-gray-100 p-6">
      {/* Encabezado */}
        <div className="flex justify-between bg-gray-300 p-4 rounded-t-lg">
            <span>Nombre Usuario</span>
            <span>Estado Orden: ABIERTO</span>
            <span>{currentTime}</span>
        </div>
      {/* Cuerpo principal */}
      <div className="flex flex-1 mt-4 gap-4">

        {/* Panel Izquierdo: Lista de órdenes */}
        <div className="bg-gray-200 rounded-lg p-2 flex flex-col gap-2 w-1/4">
          {ordenes.map((orden, index) => (
            <button
              key={index}
              className={`p-2 rounded ${selectedOrden === index ? 'bg-blue-700 text-white' : 'bg-gray-300 hover:bg-gray-400'}`}
              onClick={() => setSelectedOrden(index)}
            >
              {orden}
            </button>
          ))}
        </div>

        {/* Panel Central */}
        <div className="flex-1 flex flex-col gap-4">
          {/* Observación */}
          <div>
            <label className="font-medium">Ingresar observación:</label>
            <textarea
              className="w-full border rounded h-24 mt-1 p-2 resize-none"
              value={observacion}
              onChange={(e) => setObservacion(e.target.value)}
            />
            <div className="flex justify-end gap-4 mt-2">
              <button className="bg-gray-600 text-white px-4 py-1 rounded">Confirmar</button>
              <button className="bg-gray-400 text-white px-4 py-1 rounded">Cancelar</button>
            </div>
          </div>

          {/* Tipos de motivo */}
          <div className="bg-gray-400 rounded p-4 flex items-center justify-between">
            <div className="text-white justify-start">Tipo de motivos Fuera de Servicio</div>
            <div className="text-white bg-gray-700  px-4 py-2 rounded">Motivo 1</div>
            <button className="bg-gray-300 px-4 hover:bg-gray-700 py-2 rounded">Comentario</button>
          </div>
        </div>

        {/* Panel Derecho: Estado del sismógrafo */}
        <div className="bg-gray-200 p-4 rounded-lg w-1/4 flex flex-col justify-between">
          <div>
            <h3 className="font-medium mb-2">Situación Sismógrafo</h3>
            {[0, 1, 2, 3, 4].map((id) => (
              <div key={id} className="flex items-center gap-2 mb-2">
                <div
                  className={`w-4 h-4 rounded-full border cursor-pointer ${
                    estadoSismografo === id
                      ? 'bg-green-600 border-black'
                      : 'bg-gray-400'
                  }`}
                  onClick={() => setEstadoSismografo(id)}
                />
                <span>Fuera de Servicio</span>
              </div>
            ))}
            <button className="bg-gray-600 text-white w-full mt-2 py-1 rounded">Actualizar</button>
          </div>
        </div>
      </div>

      {/* Confirmar Orden */}
      <div className="flex justify-end mt-4">
        <button className="bg-gray-800 text-white px-6 py-2 rounded">
          CONFIRMAR ORDEN DE INSPECCIÓN
        </button>
      </div>
    </div>
  );
}

export default OrdenControl;
