// src/components/OrdenInspeccion.jsx

import { useNavigate } from 'react-router-dom';
import OrdenInspeccionUI from '../public/OrdenInpeccionUI';

const OrdenInspeccion = () => {
  const navigate = useNavigate();

  const handleNext = () => {
    navigate('/eventosismico/login');
  };

  return <OrdenInspeccionUI onNext={handleNext} />;
};

export default OrdenInspeccion;
