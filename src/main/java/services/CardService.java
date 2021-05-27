package services;

import model.Card;
import repository.CardRepository;

import java.util.List;

public class CardService {

    CardRepository cardRepository;

    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    public Card get(long id){
        return cardRepository.get(id);
    }

    public List<Card> getAll(){
        return cardRepository.getAll();
    }

    public void create(Card card){
        cardRepository.create(card);
    }

    public List<Card> getByAccountId(long accountId){
        return cardRepository.getCardsByAccountId(accountId);
    }

    public long addBalance(long id, long balance){
        return cardRepository.addBalance(id, balance);
    }
}