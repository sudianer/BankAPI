package httpServer.utils;

import httpServer.handlers.ROUTING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandlerUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(HandlerUtils.class);

    public static Map<String, String> queryToMap(String query) {
        // query is null if not provided (e.g. localhost/path )
        // query is empty if '?' is supplied (e.g. localhost/path? )
        if (query == null || query.isEmpty()) return Collections.emptyMap();

        return Stream.of(query.split("&"))
                .filter(s -> !s.isEmpty())
                .map(kv -> kv.split("=", 2))
                .collect(Collectors.toMap(x -> x[0].toLowerCase(), x-> x[1]));
    }

    public static Map<ROUTING, String> pathToMap(String path){

        LOGGER.info("Started mapping path");
        if (path == null || path.isEmpty()) return Collections.emptyMap();
        List<String> splitPath = Stream.of(path.split("/"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        LOGGER.info("path size: " + splitPath.size());

        Map<ROUTING, String> pathMap = new HashMap<>();

        if(!isCorrectURI(splitPath)){
            return Collections.emptyMap();
        }

        for(int i = 0 ;i < splitPath.size(); i++)
        {
           if(i == ROUTING.USERS.ordinal())
               pathMap.put(ROUTING.USERS, splitPath.get(ROUTING.USERS.ordinal()));

           if(i == ROUTING.USER_ID.ordinal())
               pathMap.put(ROUTING.USER_ID, splitPath.get(ROUTING.USER_ID.ordinal()));

           if(i == ROUTING.ACCOUNTS.ordinal())
               pathMap.put(ROUTING.ACCOUNTS, splitPath.get(ROUTING.ACCOUNTS.ordinal()));

           if(i == ROUTING.ACCOUNT_ID.ordinal())
               pathMap.put(ROUTING.ACCOUNT_ID, splitPath.get(ROUTING.ACCOUNT_ID.ordinal()));

           if(i == ROUTING.CARDS.ordinal())
               pathMap.put(ROUTING.CARDS, splitPath.get(ROUTING.CARDS.ordinal()));

           if(i == ROUTING.CARD_ID.ordinal())
               pathMap.put(ROUTING.CARD_ID, splitPath.get(ROUTING.CARD_ID.ordinal()));
        }

        return pathMap;
    }

    private static boolean isCorrectURI(List<String> splitUri){
        for(int i = 0; i < splitUri.size(); i++) {
            if (i == ROUTING.USERS.ordinal() &&
                    !splitUri.get(i).equalsIgnoreCase(ROUTING.USERS.toString())) {
                LOGGER.info("Users fail");
                return false;
            }
            if (i == ROUTING.USER_ID.ordinal()
                    && (
                        isInt(splitUri.get(i))
                      ^ !splitUri.get(ROUTING.USER_ID.ordinal()).equalsIgnoreCase(ROUTING.NEWUSER.toString())
                    )
            ) {
                LOGGER.info("User_ID / newuser fail, i=" + i);
                return false;
            }
            if (i == ROUTING.ACCOUNTS.ordinal()
                    && !splitUri.get(i).equalsIgnoreCase(ROUTING.ACCOUNTS.toString())) {
                LOGGER.info("Accounts fail");
                return false;
            }
            if (i == ROUTING.ACCOUNT_ID.ordinal()
                    && (
                        isInt(splitUri.get(ROUTING.ACCOUNT_ID.ordinal()))
                        ^ !splitUri.get(ROUTING.ACCOUNT_ID.ordinal()).equalsIgnoreCase(ROUTING.NEWACCOUNT.toString())
                    )
            ) {
                LOGGER.info("Account_ID / newAccount fail");
                return false;
            }
            if (i == ROUTING.CARDS.ordinal()
                    && !splitUri.get(ROUTING.CARDS.ordinal()).equalsIgnoreCase(ROUTING.CARDS.toString())) {
                LOGGER.info("Cards fail");
                return false;
            }
            if (i == ROUTING.CARD_ID.ordinal()
                    && (
                        isInt(splitUri.get(ROUTING.CARD_ID.ordinal()))
                      ^ !splitUri.get(ROUTING.CARD_ID.ordinal()).equalsIgnoreCase(ROUTING.NEWCARD.toString())
                    )
            ) {
                LOGGER.info("Card_id / newCard fail");
                return false;
            }
        }
        return true;
    }

    public static boolean isInt(String string){
        try {
            Integer.parseInt(string);
        }catch (NumberFormatException e){
            LOGGER.info("Cannot parse to int: " + string);
            return false;
        }
        return true;
    }

    public static int parseInt(String string){
        int number = 0;
        try {
            number = Integer.parseInt(string);
        }catch (NumberFormatException e){
            LOGGER.info("Cannot parse to int: " + string, e);
        }
        return number;
    }
}
