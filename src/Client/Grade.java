package Client;

public class Grade {
    private String className;
    private String grade;

    public Grade(String className, String grade) {
        this.className = className;
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public String getGrade() {
        return grade;
    }
}
