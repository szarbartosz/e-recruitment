package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.config.StandardResponse;
import controller.config.Status;
import dao.StudentDao;
import spark.Route;

public class ExamController extends Controller {
    public static Route addExam = (request, response) -> {
        response.type("application/json");
        JsonObject object = new JsonParser().parse(request.body()).getAsJsonObject();
        try {
            studentDao.addExam(object.get("userId").getAsInt(), object.get("subject").getAsString(),
                    object.get("result").getAsDouble());
        }catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };
}
