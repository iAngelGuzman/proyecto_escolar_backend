const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// traer los turnos disponibles
router.get('/', async (req, res) => {
    try {
        const { data, error } = await supabase.from('turnos').select('*');
        if (error) throw error;
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// agregar un turno nuevo
router.post('/', async (req, res) => {
    const { nombre_turno } = req.body;
    try {
        const { data, error } = await supabase
            .from('turnos')
            .insert([{ nombre_turno }])
            .select();
        
        if (error) throw error;
        res.status(201).json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;