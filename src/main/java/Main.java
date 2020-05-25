import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.*;
import model.Student;

import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        get("/students", StudentController.getAllStudents);

        post("/register", AuthenticationController.register);
        post("/exams", ExamController.addExam);
        get("/authenticate", AuthenticationController.authenticate);

        post("/candidacies", StudentController.apply);

        get("/candidacies/:studentId", StudentController.getAllCandidacies);

        post("/faculties", FacultyController.addFaculty);
        get("/faculties", FacultyController.getAllFaculties);
        post("/fields", FieldController.addField);
        post("/fields/:id", FieldController.addSubject);
        get("/fields", FieldController.getAllFields);



    }
}
