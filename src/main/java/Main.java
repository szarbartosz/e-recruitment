import controller.*;
import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {

        post("/students", StudentController.addStudent);
        get("/students", StudentController.getAllStudents);
        get("/autenthicate/:pesel", AuthenticationController.authenticate);
        post("/candidates", StudentController.apply);
        post("/exams", ExamController.addExam);
        post("fields", FieldController.addField);
        get("/fields", FieldController.getAllFields);
        post("/faculty", FacultyController.addFaculty);
        get("/faculty", FacultyController.getAllFaculties);
    }
}
