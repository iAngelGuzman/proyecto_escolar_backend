const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

// ver catalogo de materias
router.get('/', async (req, res) => {
    try {
        const { data, error } = await supabase
            .from('materias')
            .select('*');

        if (error) throw error;
        res.json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// dar de alta una materia nueva
router.post('/', async (req, res) => {
    const { nombre_materia, clave_materia, descripcion } = req.body;

    try {
        const { data, error } = await supabase
            .from('materias')
            .insert([
                { nombre_materia, clave_materia, descripcion }
            ])
            .select();

        if (error) throw error;
        res.status(201).json(data);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

module.exports = router;