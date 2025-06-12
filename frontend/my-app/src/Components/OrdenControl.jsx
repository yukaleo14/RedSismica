// src/components/OrdenControl.jsx

import { useState } from 'react';
import OrdenControlUI from '../public/OrdenControlUI';

const OrdenControl = () => {
  const [selectedOrden, setSelectedOrden] = useState(null);
  const [observacion, setObservacion] = useState('');
  const [estadoSismografo, setEstadoSismografo] = useState(3);

  const ordenes = ['Orden de Inspección 1', 'Orden de Inspección 1', 'Orden de Inspección 1', 'Orden de Inspección 1'];

  const handleConfirm = () => {
    console.log('Observación confirmada:', observacion);
  };

  const handleCancel = () => {
    setObservacion('');
  };

  const handleActualizar = () => {
    console.log('Estado sismógrafo actualizado:', estadoSismografo);
  };

  const handleConfirmarOrden = () => {
    console.log('Orden confirmada');
  };

  return (
    <OrdenControlUI
      ordenes={ordenes}
      selectedOrden={selectedOrden}
      setSelectedOrden={setSelectedOrden}
      observacion={observacion}
      setObservacion={setObservacion}
      estadoSismografo={estadoSismografo}
      setEstadoSismografo={setEstadoSismografo}
      onConfirm={handleConfirm}
      onCancel={handleCancel}
      onActualizar={handleActualizar}
      onConfirmarOrden={handleConfirmarOrden}
    />
  );
};

export default OrdenControl;
