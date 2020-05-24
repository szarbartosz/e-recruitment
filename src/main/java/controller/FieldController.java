package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.config.StandardResponse;
import controller.config.Status;
import model.Faculty;
import model.Field;
import spark.Route;

import java.util.Collection;

public class FieldController extends Controller{
    public static Route addField = (request, response) -> {
        response.type("application/json");
        JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
        try {
            universityDao.addField(object.get("facultyId").getAsInt(), object.get("name").getAsString(),
                    object.get("capacity").getAsInt());
        } catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };

    public static Route getAllFields = (request, response) -> {
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
    };
}
