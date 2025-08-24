import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import OrdenInspeccion from './components/OrdenInspeccion';
import OrdenControl from './components/OrdenControl';
import Register from './components/Register';

// Clase de nuestro analisis que representara el Boundary de nuestro sistema, la cual contendra las rutas de navegacion entre las distintas paginas
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<OrdenInspeccion />} /> 
        <Route path="/Login" element={<Login />} />
        <Route path="/ordencontrol" element={<OrdenControl />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </Router>
  );
}

export default App;
