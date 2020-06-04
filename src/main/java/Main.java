import controller.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/students", StudentController.getAllStudents);

        post("/register", AuthenticationController.register);
        post("/exams", ExamController.addExam);
        get("/authenticate", AuthenticationController.authenticate);

        post("/candidacies", StudentController.apply);

        patch("/candidacies/:id", RecruitmentController.startRecruitment);

        post("/faculties", FacultyController.addFaculty);
        get("/faculties", FacultyController.getAllFaculties);
        post("/fields", FieldController.addField);
        post("/fields/:id", FieldController.addSubject);
        get("/fields", FieldController.getAllFields);

    }
}
