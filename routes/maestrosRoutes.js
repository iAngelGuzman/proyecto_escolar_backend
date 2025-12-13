const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// lista de todos los maestros
router.get('/', async (req, res) => {
    const { data, error } = await supabase.from('maestros').select('*');
    if (error) return res.status(500).json({ error: error.message });
    res.json(data);
});

// dar de alta un maestro
router.post('/', async (req, res) => {
    const { nombre, apellido, email, telefono, password } = req.body;
    
    // si no mandan pass, ponemos 12345 por default para que puedan entrar
    const passwordFinal = password || '12345'; 

    const { data, error } = await supabase
        .from('maestros')
        .insert([{ nombre, apellido, email, telefono, password: passwordFinal }]) 
        .select();
        
    if (error) return res.status(500).json({ error: error.message });
    res.status(201).json(data);
});

module.exports = router;