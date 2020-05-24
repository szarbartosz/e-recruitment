package controller;

import com.google.gson.Gson;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Faculty;
import spark.Route;

public class FacultyController extends Controller{

    public static Route addFaculty = (request, response) -> {
        response.type("application/json");
        Faculty faculty = new Gson().fromJson(request.body(), Faculty.class);
        try {
            universityDao.addFaculty(faculty.getName());
        } catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };
}
