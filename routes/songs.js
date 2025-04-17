const express = require('express');
const router = express.Router();
const Song = require('../models/Song');

/**
 * @swagger
 * /songs:
 *   get:
 *     summary: Get all songs
 *     description: Retrieve a list of all songs in the database.
 *     responses:
 *       200:
 *         description: A JSON array of songs.
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   id:
 *                     type: integer
 *                     example: 1
 *                   title:
 *                     type: string
 *                     example: "Shape of You"
 *                   artist:
 *                     type: string
 *                     example: "Ed Sheeran"
 *                   imageUrl:
 *                     type: string
 *                     example: "http://localhost:5000/public/images/shape_of_you.jpg"
 *                   audioUrl:
 *                     type: string
 *                     example: "http://localhost:5000/public/audio/shape_of_you.mp3"
 */
router.get('/', async (req, res) => {
    try {
        const songs = await Song.findAll();
        res.json(songs);
    } catch (error) {
        res.status(500).json({ error: "Failed to retrieve songs", details: error.message });
    }
});

/**
 * @swagger
 * /songs:
 *   post:
 *     summary: Add a new song
 *     description: Creates a new song entry in the database.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               title:
 *                 type: string
 *                 example: "Blinding Lights"
 *               artist:
 *                 type: string
 *                 example: "The Weeknd"
 *               imageFileName:
 *                 type: string
 *                 example: "blinding_lights.jpg"
 *               audioFileName:
 *                 type: string
 *                 example: "blinding_lights.mp3"
 *     responses:
 *       201:
 *         description: Successfully added a new song.
 */
router.post('/', async (req, res) => {
    try {
        const { title, artist, imageFileName, audioFileName } = req.body;

        const imageUrl = `http://localhost:5000/public/images/${imageFileName}`;
        const audioUrl = `http://localhost:5000/public/audio/${audioFileName}`;

        const newSong = await Song.create({ title, artist, imageUrl, audioUrl });
        res.status(201).json(newSong);
    } catch (error) {
        res.status(500).json({ error: "Failed to add song", details: error.message });
    }
});

module.exports = router;
