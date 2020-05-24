package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.config.StandardResponse;
import controller.config.Status;
import dao.StudentDao;
import model.Student;
import spark.Route;

public class AuthenticationController extends Controller{
    public static Route authenticate = (request, response) -> {
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
    };
}
