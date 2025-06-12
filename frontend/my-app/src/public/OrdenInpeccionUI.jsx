// src/public-components/OrdenInspeccionUI.jsx

export default function OrdenInspeccionUI({ onNext }) {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-100">
      <div className="bg-white w-[90%] max-w-5xl p-6 rounded-lg shadow-md flex justify-between items-center">
        <button onClick={onNext} className="bg-gray-600 text-white px-4 py-2 rounded">
          Cerrar Orden de Inspección
        </button>
        <div className="w-48 h-48 bg-gray-300 rounded-lg" />
      </div>
    </div>
  );
}
