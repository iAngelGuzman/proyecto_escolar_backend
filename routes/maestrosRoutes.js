const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// GET: Ver maestros
router.get('/', async (req, res) => {
    const { data, error } = await supabase.from('maestros').select('*');
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

// POST: Registrar maestro
router.post('/', async (req, res) => {
    const { nombre, apellido, email, telefono, password } = req.body;
    const passwordFinal = password || '12345'; // contraseña por defecto 
    const { data, error } = await supabase
        .from('maestros')
        .insert([{ nombre, apellido, email, telefono, password: passwordFinal }]) 
        .select();
    if (error) return res.status(500).json({ error: error.message });
    res.status(201).json(data);
});

module.exports = router;