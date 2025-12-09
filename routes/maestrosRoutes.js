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
    const { nombre, apellido, email, telefono } = req.body;
    const { data, error } = await supabase
        .from('maestros')
        .insert([{ nombre, apellido, email, telefono }]) 
        .select();
    if (error) return res.status(500).json({ error: error.message });
    res.status(201).json(data);
});

module.exports = router;