package controller;

import com.google.gson.*;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Candidate;
import model.Student;
import spark.Route;

import java.util.Collection;
import java.util.List;

public class StudentController extends Controller {
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

