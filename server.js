const express = require('express');
const dotenv = require('dotenv');
const cors = require('cors');

// importar las rutas
const alumnosRoutes = require('./routes/alumnosRoutes');
const materiasRoutes = require('./routes/materiasRoutes');
const turnosRoutes = require('./routes/turnosRoutes');
const maestrosRoutes = require('./routes/maestrosRoutes');
const asignacionesRoutes = require('./routes/asignacionesRoutes');
const authRoutes = require('./routes/authRoutes');

// cargar configuración
dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// configuraciones básicas
app.use(cors());
app.use(express.json());

// usar las rutas
app.use('/api/alumnos', alumnosRoutes);
app.use('/api/materias', materiasRoutes);
app.use('/api/turnos', turnosRoutes);
app.use('/api/maestros', maestrosRoutes);
app.use('/api/asignaciones', asignacionesRoutes);
app.use('/api/login', authRoutes);

// ruta de prueba para ver si jala
app.get('/', (req, res) => {
    res.json({ message: 'API funcionando correctamente' });
});

// mensaje si la ruta no existe
app.use((req, res) => {
    res.status(404).json({ error: 'Ruta no encontrada' });
});

app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});