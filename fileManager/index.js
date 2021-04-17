const express = require('express');
const pdfController = require('./controller/pdfController.js');

const app = express();
const port = 4200;


app.use("/pdf", pdfController)

app.listen(port, () => {
    console.log("Servidor esperando en el puerto " + port)
})


module.exports = app