// src/components/Login.jsx

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginUI from '../public/LoginUI';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const toggleShowPassword = () => setShowPassword(prev => !prev);

  const handleLogin = () => {
    if (username && password) {
      // Suponiendo validación OK
      navigate('/eventosismico/orden-control');
    }
  };

  const handleCancel = () => {
    setUsername('');
    setPassword('');
  };

  return (
    <LoginUI
      username={username}
      setUsername={setUsername}
      password={password}
      setPassword={setPassword}
      showPassword={showPassword}
      toggleShowPassword={toggleShowPassword}
      onLogin={handleLogin}
      onCancel={handleCancel}
    />
  );
};

export default Login;
