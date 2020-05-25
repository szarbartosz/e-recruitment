package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Candidate;
import model.Student;
import spark.Route;

import java.util.Collection;

public class StudentController extends Controller {
    public static Route addStudent = (request, response) -> {
        response.type("application/json");
        Student student = new Gson().fromJson(request.body(), Student.class);
        try {
            studentDao.addStudent(student.getFirstName(), student.getLastName(), student.getPesel(),
                    student.getEmail(), student.getAddress().getStreet(), student.getAddress().getBuildingNumber(),
                    student.getAddress().getZipCode(), student.getAddress().getCity(), student.getHashCode());
        } catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };

    public static Route getAllStudents = (request, response) -> {
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
    };

    public static Route apply = (request, response) -> {
        response.type("application/json");
        JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
        try {
            studentDao.studentApply(object.get("studentId").getAsInt(),
                    object.get("fieldId").getAsInt());
        } catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };

    public static Route getAllCandidacies = (request, response) -> {
      response.type("application/json");
      Collection collection;
      try {
          collection = studentDao.getAllCandidacies(Integer.parseInt(request.params(":studentId")));
      } catch (Exception e){
          return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
      }
        return new Gson().toJson(
                new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation().create().toJsonTree(collection))
        );
    };
}

