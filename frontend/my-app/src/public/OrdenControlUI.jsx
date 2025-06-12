// src/public-components/OrdenControlUI.jsx

export default function OrdenControlUI({
  ordenes,
  selectedOrden,
  setSelectedOrden,
  observacion,
  setObservacion,
  onConfirm,
  onCancel,
  estadoSismografo,
  setEstadoSismografo,
  onActualizar,
  onConfirmarOrden
}) {
  return (
    <div className="min-h-screen flex flex-col bg-gray-100 p-6">
      {/* Header */}
      <div className="flex justify-between items-center bg-gray-300 p-2 rounded">
        <span className="font-medium">Nombre Usuario</span>
        <span className="font-medium">Estado Orden: ABIERTO</span>
        <span className="font-medium">dd/mm/aaaa&nbsp;&nbsp;&nbsp;&nbsp;oo:oohrs</span>
      </div>

      {/* Main */}
      <div className="flex flex-1 mt-4 gap-4">

        {/* Ordenes */}
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

        {/* Observaciones y motivos */}
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
              <button className="bg-gray-600 text-white px-4 py-1 rounded" onClick={onConfirm}>Confirmar</button>
              <button className="bg-gray-400 text-white px-4 py-1 rounded" onClick={onCancel}>Cancelar</button>
            </div>
          </div>

          {/* Motivos */}
          <div className="bg-gray-400 rounded p-4 flex items-center justify-between">
            <div className="text-white bg-gray-700 px-4 py-2 rounded">Motivo 1</div>
            <div className="bg-gray-300 px-4 py-2 rounded">Comentario</div>
          </div>
        </div>

        {/* Estado del sismógrafo */}
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
                <span>Fuera de Servicio</span>
              </div>
            ))}
            <button className="bg-gray-600 text-white w-full mt-2 py-1 rounded" onClick={onActualizar}>
              Actualizar
            </button>
          </div>
        </div>
      </div>

      {/* Confirmar orden */}
      <div className="flex justify-end mt-4">
        <button className="bg-gray-800 text-white px-6 py-2 rounded" onClick={onConfirmarOrden}>
          CONFIRMAR ORDEN DE INSPECCIÓN
        </button>
      </div>
    </div>
  );
}
