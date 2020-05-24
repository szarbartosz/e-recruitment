import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.StandardResponse;
import controller.Status;
import dao.StudentDao;
import dao.UniversityDao;
import model.Faculty;
import model.Field;
import model.Student;

import java.util.Collection;

import static spark.Spark.post;
import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();
        UniversityDao universityDao = new UniversityDao();

        post("/students", (request, response) -> {
            response.type("application/json");
            Student student = new Gson().fromJson(request.body(), Student.class);
            try {
                studentDao.addStudent(student.getFirstName(), student.getSecondName(), student.getPesel(),
                        student.getEmail(), student.getAddress().getStreet(), student.getAddress().getBuildingNumber(),
                        student.getAddress().getZipCode(), student.getAddress().getCity());
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

        get("/students", (request, response) -> {
            response.type("application/json");
            Collection<Student> collection;
            try {
                collection = studentDao.getAllStudents();
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(
                    new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().create().toJsonTree(collection))
            );
        });

        get("/autenthicate/:pesel", (request, response) -> {
            response.type("application/json");
            Student student;
            try {
                student = studentDao.authenticate(request.params(":pesel"));

            } catch (Exception e) {
                return new Gson().toJson(new StandardResponse(Status.ERROR, "Authentication refused"));
            }
            return new Gson().toJson(
                    new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation().create().
                                    toJsonTree(student))
            );
        });

        post("/exams", (request, response) -> {
            response.type("application/json");
            JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
            try {
                studentDao.addExam(object.get("userId").getAsInt(), object.get("subject").getAsString()
                        , object.get("result").getAsDouble());
            }catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

        post("/candidates", (request, response) -> {
            response.type("application/json");
            JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
            try {
                studentDao.studentApply(object.get("studentId").getAsInt(),
                        object.get("fieldId").getAsInt());
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

        post("/faculties", (request, response) -> {
            response.type("application/json");
            Faculty faculty = new Gson().fromJson(request.body(), Faculty.class);
            try {
                universityDao.addFaculty(faculty.getName());
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });

        get("/faculties", (request, response) -> {
            response.type("application/json");
            Collection<Faculty> collection;
            try {
                collection = universityDao.getAllFaculties();
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(
                    new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation().create().toJsonTree(collection))
            );
        });

        post("/fields", (request, response) -> { // dodaje kierunek do wydziału o ID facultyID
            response.type("application/json");
            JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
            try {
                universityDao.addField(object.get("facultyId").getAsInt(), object.get("name").getAsString(),
                        object.get("capacity").getAsInt());
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(new StandardResponse(Status.SUCCESS));
        });


        get("/fields", (request, response) -> {
            response.type("application/json");
            Collection<Field> collection;
            try {
                collection = universityDao.getAllFields();
            } catch (Exception e){
                return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
            }
            return new Gson().toJson(
                    new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation().create().toJsonTree(collection)));
        });
    }
}
