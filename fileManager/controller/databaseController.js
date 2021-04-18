const mysql = require('mysql');
const config = require('../config/config')

const connection = mysql.createConnection({
    host: config.databaseAccess.host,
    user: config.databaseAccess.user,
    password: config.databaseAccess.password,
    database: config.databaseAccess.database
});

connection.connect(function (err) {
    if (err) throw err;
    console.log("Connected!");
});

exports.insertMovements = (data, email, extracto) => {
    for (d of data) {
        let query = `INSERT INTO DBOneconomy.Movimiento(idMovimiento, valor, fecha, descripcion, Categoria_idCategoria, MetodoDePago_idMetodoDePago, Perfil_email, extracto_id) VALUES (${Math.floor(Math.random() * 98942142) + 455672}, ${getPaymentValue(d[2])}, STR_TO_DATE('${d[0]+'/'+new Date().getFullYear()+`','%d/%m/%Y'`}), '${d[1]}', ${getCategory(d)}, ${getPaymentMethod(d)}, '${email}', ${extracto});`
        connection.query(query, function (err, result) {
            if (err) throw err;
        });
    }
    console.log("Termino de ingresar los datos")
}

function getCategory(data) {
    let ocio = ["RUMBA", "COMIDA", "RESTAURANTE", "BAR", "DIVERSION", "PARQUE", "PARK"]
    let ingresos = ["ABONO", "NOM", "TRANSFERENCIA DESDE", "INTERESES", "CONSIG"]
    let facturas = ["PAGO", "CARGA", "CARGUE", "IMPTO", "TRASLADO A", "ZINOBE","TRANSFERENCIA A","EN TELMEX COL"]

    for (o of ocio)
        if (data[1].indexOf(o) != -1)
            return 2

    for (i of ingresos)
        if (data[1].indexOf(i) != -1)
            return 4

    for (f of facturas)
        if (data[1].indexOf(f) != -1)
            return 1
    return 5
}

let getPaymentMethod = data => {
    return (data[1].indexOf("EPREPAGO") != -1) ? 9806 : 9807
}

let getPaymentValue = value => {
    value = value.replace(/\.00/g, '')
    value = value.replace(/,/g, '')
    return (value[0] == ".") ? "0" + value : value
}