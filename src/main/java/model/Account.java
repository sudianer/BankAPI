package model;

public class Account {

    long id;
    long userID;
    String number;

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public String getNumber() {
        return number;
    }

    public Account(){

    }

    public Account(long id, long userID, String number){
        this.id = id;
        this.userID = userID;
        this.number = number;
    }

    private Account(Builder builder){
        this.id = builder.id;
        this.userID = builder.userID;
        this.number = builder.number;
    }

    public static class Builder {
        private final long id;
        private long userID;
        private String number;

        public Builder(long id){
            this.id = id;
        }

        public Builder withUserID(long userID){
            this.userID = userID;
            return this;
        }

        public Builder withNumber(String number){
            this.number = number;
            return this;
        }

        public Account build(){
            return new Account(this);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userID=" + userID +
                ", number='" + number + '\'' +
                '}';
    }
}
