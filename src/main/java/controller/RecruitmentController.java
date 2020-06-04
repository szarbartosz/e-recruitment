package controller;

import com.google.gson.Gson;
import controller.config.StandardResponse;
import controller.config.Status;
import spark.Route;

public class RecruitmentController extends Controller {
    public static Route startRecruitment = (request, response) -> {
        response.type("application/json");
        try {
            universityDao.markAccepted(Integer.parseInt(request.params(":id")));
        } catch (Exception e){
            return new Gson().toJson(new StandardResponse(Status.ERROR, e.toString()));
        }
        return new Gson().toJson(new StandardResponse(Status.SUCCESS));
    };
}
