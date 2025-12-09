const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// GET: Ver asignaciones con DATOS REALES (Joins)
router.get('/', async (req, res) => {
    // Pedimos la asignación y los datos de las tablas relacionadas
    const { data, error } = await supabase
        .from('asignaciones')
        .select(`
            id,
            maestros (id, nombre, apellido),
            materias (id, nombre_materia),
            turnos (id, nombre_turno)
        `);
    
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

// POST: Crear asignación (Aquí sí enviamos IDs)
router.post('/', async (req, res) => {
    const { maestro_id, materia_id, turno_id } = req.body;
    const { data, error } = await supabase
        .from('asignaciones')
        .insert([{ maestro_id, materia_id, turno_id }])
        .select();

    if (error) return res.status(500).json({ error: error.message });
    res.status(201).json(data);
});

module.exports = router;