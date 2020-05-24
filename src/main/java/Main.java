import controller.*;
import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        post("/students", StudentController.addStudent);
        get("/students", StudentController.getAllStudents);
        get("/autenthicate/:pesel", AuthenticationController.authenticate);
        post("/exams", ExamController.addExam);
        get("/fields", FieldController.getAllFields);
        post("/candidates", StudentController.apply);
        post("/faculty", FacultyController.addFaculty);
    }
}
