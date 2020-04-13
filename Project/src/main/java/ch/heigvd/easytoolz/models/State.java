package ch.heigvd.easytoolz.models;

public enum State {
    pending("pending"),
    unvailable("unavailable"),
    available("available");

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
