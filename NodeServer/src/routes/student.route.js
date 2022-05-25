const express = require("express");
const router = express.Router();

const studentController = require("../controllers/student.controller");

//get all students
router.get("/", studentController.getStudentList);

//get student by id
router.get("/id=:id", studentController.getStudentByID);

//get student by email
router.get("/email=:email", studentController.getStudentByEmail);

//get active students
router.get("/active/id=:id", studentController.getActiveStudents);

//login
router.post("/", studentController.loginStudent);

//patch student by email, *********mundemi me perdor edhe te perditesimi
router.patch("/:email", studentController.updateStudentStatus);

module.exports = router;
