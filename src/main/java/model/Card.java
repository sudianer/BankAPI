package model;

public class Card {

    private long id;
    private long accountId;
    private long balance;
    private String number;

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public long getBalance() {
        return balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setBalance(long balance) { this.balance = balance; }

    public Card() {
    }

    public Card(long id, String number, long balance, long accountId) {
        this.id = id;
        this.number = number;
        this.balance = balance;
        this.accountId = accountId;
    }

    public Card(Builder builder){
        this.id = builder.id;
        this.number = builder.number;
        this.balance = builder.balance;
        this.accountId = builder.accountId;
    }

    public static class Builder {
        private final long id;
        private long accountId;
        private long balance;
        private String number;

        public Builder(long id){
            this.id = id;
        }

        public Builder withAccountID(long userID){
            this.accountId = userID;
            return this;
        }

        public Builder withBalance(long balance){
            this.balance = balance;
            return this;
        }

        public Builder withNumber(String number){
            this.number = number;
            return this;
        }

        public Card build(){
            return new Card(this);
        }
    }

}
