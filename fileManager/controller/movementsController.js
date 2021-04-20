const router = require('express').Router()
const bodyParser = require('body-parser');
const dbController = require('./databaseController')


router.use(bodyParser.json());

router.get("/get/:method/:entity", async (req, res) => {
    console.log("Recibio solicitud de consulta movimientos")
    let query = `
                Select m.idMovimiento,m.valor,m.fecha,m.descripcion 
                from Movimiento m inner join MetodoDePago mdp 
                    on m.MetodoDePago_idMetodoDePago = mdp.idMetodoDePago
                inner join Tipo t 
                    on mdp.Tipo_idTipo = t.idTipo 
                inner join Entidad e 
                    on e.idBanco = mdp.Entidad_idBanco 
                where `
    if (req.params.method == "all")
        query += "TRUE"
    else
        query += `t.tipo ='${req.params.method}'`

    if (req.params.entity == "all")
        query += ` and TRUE`
    else
        query += ` and e.nombre_entidad = '${req.params.entity}'`

    dbController.getMovements(query).then(data => {
        let response = ''
        for (d of data)
            response += `${d.idMovimiento},${d.valor},${getFecha(d.fecha)},${d.descripcion}|`
        res.send(response)
    });
});

function getFecha(date) {
    date = date.toString()
    let month = "Ene "

    switch (date.slice(4, 7)) {
        case "Apr":
            month = "Abr "
        case "Aug":
            month = "Ago "
        case "Dec":
            month = "Dic "
    }

    return month + date.slice(8,15)
}

module.exports = router