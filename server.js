const express = require('express');
const dotenv = require('dotenv');
const cors = require('cors');

// Importar rutas
const alumnosRoutes = require('./routes/alumnosRoutes');
const materiasRoutes = require('./routes/materiasRoutes');
const turnosRoutes = require('./routes/turnosRoutes');
const maestrosRoutes = require('./routes/maestrosRoutes');
const asignacionesRoutes = require('./routes/asignacionesRoutes');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

// Usar las rutas
app.use('/api/alumnos', alumnosRoutes);
app.use('/api/materias', materiasRoutes);
app.use('/api/turnos', turnosRoutes);
app.use('/api/maestros', maestrosRoutes);
app.use('/api/asignaciones', asignacionesRoutes);

app.get('/', (req, res) => {
    res.send('Servidor Backend del Proyecto Escolar Funcionando.');
});

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});