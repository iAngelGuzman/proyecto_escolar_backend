const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// GET: Ver turnos
router.get('/', async (req, res) => {
    try {
        const { data, error } = await supabase.from('turnos').select('*');
        if (error) throw error;
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// POST: Crear turno
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