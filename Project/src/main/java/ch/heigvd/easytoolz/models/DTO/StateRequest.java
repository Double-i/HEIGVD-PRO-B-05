package ch.heigvd.easytoolz.models.DTO;

public class StateRequest {
    private String state;

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "StateRequest{" +
                "state='" + state + '\'' +
                '}';
    }
}
