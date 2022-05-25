const StudentModel = require("../models/student.model");

//get all students
exports.getStudentList = (req, res) => {
  StudentModel.getAllStudents((err, students) => {
    if (err) {
      res.send(err);
    }
    console.log("Students", students);
    res.send(students);
  });
};

//get student by ID
exports.getStudentByID = (req, res) => {
  StudentModel.getStudentByID(req.params.id, (err, student) => {
    if (err) {
      res.send(err);
    }
    console.log("single student data", student);
    res.send(student);
  });
};

//get student by Email
exports.getStudentByEmail = (req, res) => {
  StudentModel.getStudentByEmail(req.params.email, (err, student) => {
    if (err) {
      res.send(err);
    }
    console.log("single student data", student);
    res.send(student);
  });
};

//
exports.getActiveStudents = (req, res) => {
  StudentModel.getActiveStudents(req.params.id, (err, students) => {
    if (err) {
      res.send(err);
    }
    console.log("active students array: ", students);
    res.send(students);
  });
};

//login
exports.loginStudent = (req, res) => {
  const email = req.body.email;
  const password = req.body.password;

  if (req.body.constructor === Object && Object.keys(req.body).length === 0) {
    res.send(400).send({ success: false, message: "Please fill all fields" });
  } else {
    StudentModel.loginStudent(email, password, (err, login_result) => {
      if (err) {
        res.send(err);
      }
      res.send(login_result);
    });
  }
  console.log("req data", req.body.email);
};

//update online status
exports.updateStudentStatus = (req, res) => {
  const email = req.params.email;
  const status = req.body.status;

  StudentModel.updateStatus(email, status, (err, result) => {
    if (err) {
      res.send(err);
    }
    res.send(result);
    console.log(result);
  });
};
