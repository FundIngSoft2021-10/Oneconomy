const router = require('express').Router()
const bodyParser = require('body-parser')
const fs = require('fs')
const pdf2table = require('pdf2table');
const md5 = require('md5');
const dbController = require('./databaseController')

const {
    Base64
} = require('js-base64');


router.use(bodyParser.json({
    limit: '50mb'
}));

router.post("/upload", async (req, res) => {
    console.log("Recibio solicitud de subida PDF")
    var bin = Base64.atob(req.body.file);
    
    var buffer = Buffer.from(bin,'binary');
    pdf2table.parse(buffer, function (err, rows, rowsdebug) {
       if(err) res.status(500).send("Hubo un error subiendo el PDF")
        rows = rows.filter(e => e[0].indexOf('/') != -1)
        dbController.insertMovements(rows, req.body.email, 154597)
        res.status(200).send("Información subida con exito")
    });
});



module.exports = router