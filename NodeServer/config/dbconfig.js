const mysql = require("mysql2");

const dbConn = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "1234",
  database: "db_ssh",
});

dbConn.connect(function (error) {
  if (error) throw error;
  console.log("Connected successfully to the database!");
});

module.exports = dbConn;
