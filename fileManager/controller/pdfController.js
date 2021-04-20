exports.handler = async (event) => {
    const pdf2table = require('pdf2table');
    const {
        Base64
    } = require('js-base64');

    console.log("Recibio solicitud de subida PDF")

    var bin = Base64.atob(event.file);
    let buffer = Buffer.from(bin, 'binary')

    const promise = new Promise(function (resolve, reject) {
        pdf2table.parse(buffer, function (err, rows, rowsdebug) {
            console.log("Termino de parsear")
            if (err) return console.error(err)
            //rows = rows.filter(e => e[0].indexOf('/') != -1)
            resolve({
                status: 200
            })
        });
    })
    return promise

};