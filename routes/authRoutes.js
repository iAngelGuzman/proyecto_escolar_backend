const express = require('express');
const router = express.Router();
const supabase = require('../supabaseClient');

router.post('/', async (req, res) => {
    const { email, password } = req.body;

    // rol de admin fijo 
    if (email === 'admin@escuela.com' && password === 'admin123') {
        return res.json({ 
            mensaje: 'Login exitoso', 
            usuario: { nombre: 'Administrador', rol: 'admin' } 
        });
    }

    // buscamos un maestro que coincida con el email y el password recibidos
    const { data, error } = await supabase
        .from('maestros')
        .select('*')
        .eq('email', email)
        .eq('password', password) 
        .single(); // esperamos solo 1 resultado

    if (error || !data) {
        return res.status(401).json({ error: 'Credenciales incorrectas' });
    }

    // si encontramos al maestro, devolvemos sus datos
    res.json({ 
        mensaje: 'Login exitoso', 
        usuario: { ...data, rol: 'docente' } 
    });
});

module.exports = router;