const express = require("express");
const bodyParser = require("body-parser");

const app = express();

//app.use(express.json()); //??

const port = process.env.port || 5000;

//parse request data, contet type: application/x-www-form-rulencoded
app.use(bodyParser.urlencoded({ extended: false }));

//parse request data, content type: application/json
app.use(bodyParser.json());

app.get("/", (req, res) => {
  res.send("Hello world");
});

//import student routes
const studentRoutes = require("./src/routes/student.route");

app.use("/api/v1/students", studentRoutes);

app.listen(port, () => {
  console.log(`Express Server is running on port ${port}`);
});
