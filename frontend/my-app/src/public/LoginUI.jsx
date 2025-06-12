// src/public-components/LoginUI.jsx

export default function LoginUI({ username, setUsername, password, setPassword, showPassword, toggleShowPassword, onLogin, onCancel }) {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-lg shadow-md flex gap-6">
        <div className="w-48 h-48 bg-gray-300 rounded-lg" />
        <div className="flex flex-col gap-2 w-80">
          <label className="font-medium">Nombre Usuario</label>
          <input className="border rounded p-2" value={username} onChange={e => setUsername(e.target.value)} />
          <label className="font-medium">Contraseña</label>
          <div className="relative">
            <input
              type={showPassword ? "text" : "password"}
              className="border rounded p-2 w-full"
              value={password}
              onChange={e => setPassword(e.target.value)}
            />
            <span className="absolute right-2 top-2 cursor-pointer" onClick={toggleShowPassword}>👁️</span>
          </div>
          <div className="flex justify-between mt-4">
            <button className="bg-gray-600 text-white px-4 py-1 rounded" onClick={onLogin}>Ingresar</button>
            <button className="bg-gray-400 text-white px-4 py-1 rounded" onClick={onCancel}>Cancelar</button>
          </div>
        </div>
      </div>
    </div>
  );
}
