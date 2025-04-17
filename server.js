const express = require('express');
const path = require('path');
const sequelize = require('./config/database');
const cors = require('cors');
const swaggerJsDoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');

const app = express();
app.use(cors());
app.use(express.json());

// 🔹 Swagger Configuration
const swaggerOptions = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Music Player API',
            version: '1.0.0',
            description: 'API documentation for the Music Player app',
        },
        servers: [
            { url: 'http://localhost:5000', description: 'Local Server' },
            { url: 'https://styla-backend.onrender.com', description: 'Production Server' }
        ]
    },
    apis: [path.join(__dirname, 'routes', '**/*.js')], // ✅ Includes all route files
};

const swaggerDocs = swaggerJsDoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

console.log('✅ Swagger Docs Loaded:', Object.keys(swaggerDocs.paths));

// 🔹 Root Route
app.get('/', (req, res) => {
    res.send(`
        <h1>Welcome to the Music Player API 🎵</h1>
        <p>Visit <a href="/api-docs">/api-docs</a> for API documentation.</p>
    `);
});

// 🔹 API Routes
app.use('/songs', require('./routes/songs'));

// 🔹 Sync Database and Start Server
sequelize.sync()
    .then(() => {
        console.log('✅ Database connected successfully.');
        app.listen(5000, () => console.log('🚀 Server running on http://localhost:5000'));
    })
    .catch(error => {
        console.error('❌ Database connection failed:', error);
        process.exit(1); // Exit process if DB connection fails
    });
