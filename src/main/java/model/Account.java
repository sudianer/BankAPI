package model;

import java.util.List;

public class Account {

    long id;
    long userID;
    String number;
    List<Card> cards;

    public long getId() {
        return id;
    }

    public long getUserID() {
        return userID;
    }

    public String getNumber() {
        return number;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards){
        this.cards = cards;
    }

    public Account(){

    }

    public Account(long id, long userID, String number, List<Card> cards){
        this.id = id;
        this.userID = userID;
        this.number = number;
        this.cards = cards;
    }

    private Account(Builder builder){
        this.id = builder.id;
        this.userID = builder.userID;
        this.number = builder.number;
        this.cards = builder.cards;
    }

    public static class Builder {
        private final long id;
        private long userID;
        private String number;
        private List<Card> cards;

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

        public Builder withCards(List<Card> cards){
            this.cards = cards;
            return this;
        }

        public Account build(){
            return new Account(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userID=" + userID +
                ", number='" + number + '\'' +
                ", cardList=" + cards +
                '}';
    }
}
