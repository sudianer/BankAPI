package model;

public class User {

    private long id;
    private String firstName;
    private String secondName;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public User(){
    }

    public User(long id, String firstName, String secondName){
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    private User(Builder builder){
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
    }

    public static class Builder {
        private final long id;
        private String firstName;
        private String secondName;

        public Builder(long id){
            this.id = id;
        }

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withSecondName(String secondName){
            this.secondName = secondName;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                '}';
    }
}
