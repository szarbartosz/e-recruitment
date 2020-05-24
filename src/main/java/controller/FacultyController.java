package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Faculty;
import spark.Route;

import java.util.Collection;

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

    public static Route getAllFaculties = (request, response) -> {
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
    };
}
