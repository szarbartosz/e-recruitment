package controller.config;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardResponse {
    private Status status;
    private String message;
    private JsonElement data;

    public StandardResponse(Status status) {
        this.status = status;
    }

    public StandardResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public StandardResponse(Status status, String message, JsonElement data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
