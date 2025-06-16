import { useState } from 'react';
import { Eye } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { API_BASE_URL } from '../config';

function Register() {
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: '',
    password: '',
    firstname: '',
    lastname: '',
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(`/autenticacion/register`, user);
      // Assuming the backend returns a token
      localStorage.setItem('token', response.data.token);
      navigate('/login'); // Redirect to login after successful registration
      // Alternatively, navigate to '/orden-control' for auto-login
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-md flex w-full max-w-3xl items-center">
        <div className="w-1/2 bg-gray-300 rounded-xl flex items-center justify-center h-full py-8">
          <svg className="w-24 h-24 text-gray-500" fill="currentColor" viewBox="0 0 24 24">
            <path d="M12 12c2.7 0 5-2.3 5-5s-2.3-5-5-5-5 2.3-5 5 2.3 5 5 5zm0 2c-3.3 0-10 1.7-10 5v3h20v-3c0-3.3-6.7-5-10-5z" />
          </svg>
        </div>
        <div className="w-1/2 flex flex-col px-6 space-y-4">
          {error && <p className="text-red-500">{error}</p>}
          <form onSubmit={handleSubmit}>
            <div>
              <label className="block text-sm font-medium">Nombre de Usuario</label>
              <input
                type="text"
                name="username"
                value={user.username}
                onChange={handleChange}
                className="w-full border rounded px-2 py-1 mt-1"
                required
              />
            </div>
            <div className="relative">
              <label className="block text-sm font-medium">Contraseña</label>
              <input
                type={showPassword ? 'text' : 'password'}
                name="password"
                value={user.password}
                onChange={handleChange}
                className="w-full border rounded px-2 py-1 mt-1"
                required
              />
              <button
                type="button"
                className="absolute right-2 bottom-2"
                onClick={() => setShowPassword(!showPassword)}
              >
                <Eye size={18} />
              </button>
            </div>
            <div>
              <label className="block text-sm font-medium">Nombre</label>
              <input
                type="text"
                name="firstname"
                value={user.firstname}
                onChange={handleChange}
                className="w-full border rounded px-2 py-1 mt-1"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium">Apellido</label>
              <input
                type="text"
                name="lastname"
                value={user.lastname}
                onChange={handleChange}
                className="w-full border rounded px-2 py-1 mt-1"
                required
              />
            </div>
            <div className="flex justify-between space-x-4 mt-4">
              <button
                type="submit"
                className="flex-1 bg-gray-700 text-white px-4 py-2 rounded hover:bg-gray-800"
              >
                Registrarse
              </button>
              <button
                type="button"
                onClick={() => navigate('/login')}
                className="flex-1 bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
              >
                Cancelar
              </button>
            </div>
          </form>
          <div className="text-center mt-2">
            <button
              onClick={() => navigate('/login')}
              className="text-gray-700 hover:underline"
            >
              ¿Ya tienes una cuenta? Inicia sesión
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;