const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// GET: Obtener alumnos
router.get('/', async (req, res) => {
    try {
        const { data, error } = await supabase.from('alumnos').select('*');
        if (error) throw error;
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// POST: Crear alumno 
router.post('/', async (req, res) => {
    const { matricula, nombre, apellido, fecha_nacimiento, direccion } = req.body;

    try {
        const { data, error } = await supabase
            .from('alumnos')
            .insert([
                { 
                  matricula, 
                  nombre, 
                  apellido,
                  fecha_nacimiento, 
                  direccion         
                }
            ])
            .select();

        if (error) throw error;
        res.status(201).json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;