package services;

import dao.CardDAO;
import model.Card;
import java.util.List;

public class CardService{

    CardDAO cardDAO;

    public CardService(CardDAO cardDAO){
        this.cardDAO = cardDAO;
    }

    public Card get(long id){
        return cardDAO.get(id);
    }

    public List<Card> getAll(){
        return cardDAO.getAll();
    }

    public void create(Card card){
        cardDAO.create(card);
    }

    public List<Card> getByAccountId(long accountId){
        return cardDAO.getByAccountID(accountId);
    }

    public long addBalance(long id, long balance){
        return cardDAO.addBalance(id, balance);
    }
}