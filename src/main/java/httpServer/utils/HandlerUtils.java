package httpServer.utils;

import httpServer.handlers.ROUTING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<String> notEmptySplitPath = Stream.of(path.split("/"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        LOGGER.info("path size: " + notEmptySplitPath.size());

        Map<ROUTING, String> pathMap = new HashMap<>();

        for(int i = 0; i < notEmptySplitPath.size(); i++){

            if(i == 0 && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.USERS.toString())) {
                pathMap.put(ROUTING.USERS, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as Users: " + ROUTING.USERS);
            }

            if(i == 1
                    && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.NEWUSER.toString())
                    && notEmptySplitPath.size() == i+1){
                pathMap.put(ROUTING.NEWUSER, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as NEW_USER: " + pathMap.get(ROUTING.NEWUSER));
                break;
            }

            if(i == 1 && isInt(notEmptySplitPath.get(i))) {
                pathMap.put(ROUTING.USER_ID, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as User_ID: " + pathMap.get(ROUTING.USER_ID));
            }

            if(i == 2 && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.ACCOUNTS.toString())) {
                pathMap.put(ROUTING.ACCOUNTS, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as Accounts: " + ROUTING.ACCOUNTS);
            }

            if(i == 3
                    && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.NEWACCOUNT.toString())
                    && notEmptySplitPath.size() == i+1) {
                pathMap.put(ROUTING.NEWACCOUNT, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as NEW_ACCOUNT: " + pathMap.get(ROUTING.NEWACCOUNT));
                break;
            }
            if(i == 3 && isInt(notEmptySplitPath.get(i))) {
                pathMap.put(ROUTING.ACCOUNT_ID, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as Account_ID: " + pathMap.get(ROUTING.ACCOUNT_ID));
            }

            if(i == 4 && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.CARDS.toString())) {
                pathMap.put(ROUTING.CARDS, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as Cards: " + ROUTING.CARDS);
            }

            if(i == 5
                    && notEmptySplitPath.get(i).equalsIgnoreCase(ROUTING.NEWCARD.toString())
                    && notEmptySplitPath.size() == i+1) {
                pathMap.put(ROUTING.NEWCARD, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as NEW_CARD: " + pathMap.get(ROUTING.NEWCARD));
                break;
            }

            if(i == 5 && isInt(notEmptySplitPath.get(i))) {
                pathMap.put(ROUTING.CARD_ID, notEmptySplitPath.get(i));
                LOGGER.info("Mapped as Cards_ID: " + pathMap.get(ROUTING.CARD_ID));
            }
        }
        return pathMap;
    }

    private static boolean isInt(String string){
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
