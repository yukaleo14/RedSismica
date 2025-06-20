import { useNavigate } from 'react-router-dom';

function OrdenInspeccion() {
  const navigate = useNavigate();

  const handleCerrar = () => {
    navigate('/login');
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-md flex justify-between w-full max-w-4xl items-center">
        <div className="flex-1 flex justify-center">
          <button
            onClick={handleCerrar}
            className="bg-gray-700 text-white px-4 py-2 rounded hover:bg-gray-800"
          >
            Registrar resultado de revisión manual
          </button>
        </div>
        <div className="w-40 h-48 bg-gray-300 rounded-xl flex items-center justify-center">
          <svg
            className="w-20 h-20 text-gray-500"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path d="M12 12c2.7 0 5-2.3 5-5s-2.3-5-5-5-5 2.3-5 5 2.3 5 5 5zm0 2c-3.3 0-10 1.7-10 5v3h20v-3c0-3.3-6.7-5-10-5z" />
          </svg>
        </div>
      </div>
    </div>
  );
}

export default OrdenInspeccion;
