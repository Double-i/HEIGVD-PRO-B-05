package ch.heigvd.easytoolz.models;

public enum Creator {
    owner("owner"),
    borrower("borrrower");

    private final String creator;

    Creator(String creator) {
        this.creator = creator;
    }

    public String getState() {
        return creator;
    }
    public Creator opposite(){
        if(this.getState().equals("owner")){
            return borrower;
        }else{
            return owner;
        }
    }

    @Override
    public String toString() {
        return "Creator{" +
                "creator='" + creator + '\'' +
                '}';
    }
}

