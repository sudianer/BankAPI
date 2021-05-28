package model;

import java.util.List;

public class User {

    private long id;
    private String firstName;
    private String secondName;
    private List<Account> accounts;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public List<Account> getAccounts(){ return accounts; }

    public void setAccounts(List<Account> accounts){ this.accounts = accounts; }

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
        this.accounts = builder.accountList;
    }

    public static class Builder {
        private final long id;
        private String firstName;
        private String secondName;
        private List<Account> accountList;

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

        public Builder withAccountList(List<Account> accountList){
            this.accountList = accountList;
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
                ", accounts=" + accounts +
                '}';
    }
}
