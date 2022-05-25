package Client;

import java.util.Map;

public class Student {
    private int id;
    private String first_name;
    private String parent_name;
    private String last_name;
    private String email;
    private String password;
    private char gender;
    private String school_name;
    private Map<String, String> grades;
    private String phone_number;
    private int is_online;
    private String picture_path;

    public Student(int id, String first_name, String parent_name, String last_name, String email, String password, char gender, String school_name, Map<String, String> grades, String phone_number, int is_online, String picture_path) {
        this.id = id;
        this.first_name = first_name;
        this.parent_name = parent_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.school_name = school_name;
        this.grades = grades;
        this.phone_number = phone_number;
        this.is_online = is_online;
        this.picture_path = picture_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public Map<String, String> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, String> grades) {
        this.grades = grades;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", parent_name='" + parent_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", school_name='" + school_name + '\'' +
                ", grades=" + grades +
                ", phone_number='" + phone_number + '\'' +
                ", is_online=" + is_online +
                ", picture_path='" + picture_path + '\'' +
                '}';
    }
}
