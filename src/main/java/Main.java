import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.*;
import model.Student;

import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {

        post("/register", AuthenticationController.register);
        get("/students", StudentController.getAllStudents);
        get("/autenthicate", AuthenticationController.authenticate);
        post("/candidates", StudentController.apply);
        post("/exams", ExamController.addExam);
        post("/faculties", FacultyController.addFaculty);
        get("/faculties", FacultyController.getAllFaculties);
        post("fields", FieldController.addField);
        get("/fields", FieldController.getAllFields);
        get("/candidacies/:studentId", StudentController.getAllCandidacies);

//        Student student = new Student("Krzysztof", "Nalepa", "12345678910", "knalepa@gmail.com", "polna", "36", "36-420", "Krakóœ");
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(student);
//        System.out.println(json);
    }
}
