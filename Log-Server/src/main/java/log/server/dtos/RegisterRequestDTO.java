package log.server.dtos;

public class RegisterRequestDTO {
    String service;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
