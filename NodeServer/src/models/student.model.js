var dbConn = require("../../config/dbconfig");

var Student = function (student) {
  this.id = student.id;
  this.first_name = student.first_name;
  this.parent_name = student.parent_name;
  this.last_name = student.last_name;
  this.email = student.email;
  this.password = student.password;
  this.gender = student.gender;
  this.school_name = student.school_name;
  this.grades = student.grades;
  this.phone_number = student.phone_number;
  this.is_online = student.is_online;
  this.picture_path = student.picture_path;
};

//get all students
Student.getAllStudents = (result) => {
  dbConn.query("SELECT * FROM students", (err, res) => {
    if (err) {
      console.log("Error while fetching students!", err);
      result(null, err);
    } else {
      console.log("Students fetched successfully");
      result(null, res);
    }
  });
};

//get student by ID
Student.getStudentByID = (id, result) => {
  dbConn.query("SELECT * FROM students WHERE id=?", id, (err, res) => {
    if (err) {
      console.log("Error while fetching student by id!", err);
      result(null, err);
    } else {
      result(null, res);
    }
  });
};

//get student by Email
Student.getStudentByEmail = (email, result) => {
  dbConn.query("SELECT * FROM students WHERE email=?", email, (err, res) => {
    if (err) {
      console.log("Error while fetching student by email!", err);
      result(null, err);
    } else {
      result(null, res);
    }
  });
};

Student.getActiveStudents = (id, result) => {
  dbConn.query(
    "SELECT * FROM students WHERE is_online=1 AND NOT id=?",
    id,
    (err, res) => {
      if (err) {
        console.log("Error while getting active students!", err);
        result(null, err);
      } else {
        result(null, res);
      }
    }
  );
};

//login
Student.loginStudent = (email, password, result) => {
  dbConn.query(
    "SELECT * FROM students WHERE email=? AND password=?",
    [email, password],
    (err, res) => {
      if (err) {
        result(null, err);
      } else if (res.length > 0) {
        result(200);
      } else {
        result(404);
      }
    }
  );
};

//update student status
Student.updateStatus = (email, status, result) => {
  dbConn.query(
    "UPDATE students SET is_online=? WHERE email=?",
    [status, email],
    (err, res) => {
      if (err) {
        result(null, err);
      } else {
        result(null, res);
      }
    }
  );
};

module.exports = Student;
