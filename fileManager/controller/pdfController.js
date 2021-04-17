const router = require('express').Router()
const bodyParser = require('body-parser')
const fs = require('fs')
const pdf2table = require('pdf2table');
const md5 = require('md5');


router.use(bodyParser.json())

router.post("/upload", async (req, res) => {
    console.log("Recibio solicitud de subida PDF")

    var bin = Base64.atob(req.body.file);
    var datetime = new Date();

    file_name = `extract_${md5(datetime.toUTCString())}}.pdf`

    fs.writeFile(file_name, bin, 'binary', error => {
        if (error)
            throw error;
        else {
            console.log('Archivo recibido correctamente!');
            fs.readFile(file_name, function (err, buffer) {
                if (err) return console.error(err);

                pdf2table.parse(buffer, function (err, rows, rowsdebug) {
                    if (err) return console.error(err)
                    x = 0
                    for (r of rows)
                        if (r[0].indexOf('/') != -1)
                            console.log(r)
                    res.status(200).send("Información subida con exito")
                });
            });
        }
    });
});

module.exports = router