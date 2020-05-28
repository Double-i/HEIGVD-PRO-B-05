package ch.heigvd.easytoolz.models;

public enum State {
    pending("pending"),
    refused("refused"),
    accepted("accepted"),
    cancel("cancel");

    private final String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "State{" +
                "state='" + state + '\'' +
                '}';
    }
}
