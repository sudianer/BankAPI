package repository;

import dao.CardDAO;
import model.Card;
import java.util.List;

public class CardRepository {

    private final CardDAO cardDAO;

    public CardRepository(CardDAO cardDAO){ this.cardDAO = cardDAO; }

    public List<Card> getAll(){ return cardDAO.getAll(); }

    public Card get(long id){ return cardDAO.get(id); }

    public void update(Card card){ cardDAO.update(card); }

    public void delete(long id){ cardDAO.delete(id); }

    public void create(Card card){ cardDAO.create(card); }

    public List<Card> getCardsByAccountId(long accountID){
        return cardDAO.getByAccountID(accountID);
    }

    public long addBalance(long id, long balance) {
        Card card = get(id);
        long resultBalance = card.getBalance() + balance;
        card.setBalance(resultBalance);
        update(card);
        return resultBalance;
    }
}