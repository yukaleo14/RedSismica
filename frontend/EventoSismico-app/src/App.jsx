import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import OrdenInspeccion from './components/OrdenInspeccion';
import OrdenControl from './components/OrdenControl';
import Register from './components/Register';

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
