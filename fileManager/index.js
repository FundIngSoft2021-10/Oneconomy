const express = require('express');
const pdfController = require('./controller/pdfController.js');
const movementsController = require('./controller/movementsController');
const dbController = require('./controller/databaseController')

const app = express();
const port = 4200;


app.use("/pdf", pdfController)
app.use("/movements", movementsController)



app.listen(port, () => {
    console.log("Servidor esperando en el puerto " + port)
})

module.exports = app