import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Components/Login';
import OrdenInspeccion from './Components/OrdenInspeccion';
import OrdenControl from './Components/OrdenControl';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/EventoSismico" element={<OrdenInspeccion />} />
        <Route path="/EventoSismico/login" element={<Login />} />
        <Route path="/EventoSismico/orden-control" element={<OrdenControl />} />
      </Routes>
    </Router>
  );
}

export default App;
