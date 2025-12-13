const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// traer asignaciones con toda la info (el join)
router.get('/', async (req, res) => {
    // hacemos el select anidado para no ver solo numeros de id
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

// guardar una nueva asignacion
router.post('/', async (req, res) => {
    // aqui si ocupamos los ids para guardar la relacion
    const { maestro_id, materia_id, turno_id } = req.body;
    
    const { data, error } = await supabase
        .from('asignaciones')
        .insert([{ maestro_id, materia_id, turno_id }])
        .select();

    if (error) return res.status(500).json({ error: error.message });
    res.status(201).json(data);
});

module.exports = router;