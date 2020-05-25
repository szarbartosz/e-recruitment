package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Student;
import spark.Route;

//not really JWT authentication XD

public class AuthenticationController extends Controller{
    public static Route authenticate = (request, response) -> {
        response.type("application/json");
        Student student;
        try {
            if (request.headers("login") == null || request.headers("hashCode") == null){
                throw new Exception("Headers incomplete");
            }
            student = studentDao.authenticate(request.headers("login"), request.headers("hashCode"));
        } catch (Exception e) {
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.getMessage()));
        }
        return new Gson().toJson(
                new StandardResponse(Status.SUCCESS, "ok", new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation().create().
                                toJsonTree(student))
        );
    };

    public static Route register = (request, response) -> {
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

}
