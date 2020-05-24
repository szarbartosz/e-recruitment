package controller.config;

public enum Status {
    SUCCESS ("Success"),
    ERROR ("Error");
    private String status;

    Status(String status) {
        this.status = status;
    }
}
