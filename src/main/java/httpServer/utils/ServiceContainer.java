package httpServer.utils;

import services.AccountService;
import services.CardService;
import services.UserService;

public class ServiceContainer {

	static UserService userService;
	static AccountService accountService;
	static CardService cardService;

	public static UserService getUserService() {
		return userService;
	}

	public static AccountService getAccountService() {
		return accountService;
	}

	public static CardService getCardService() {
		return cardService;
	}

	public static void setUserService(UserService userService) {
		ServiceContainer.userService = userService;
	}

	public static void setAccountService(AccountService accountService) {
		ServiceContainer.accountService = accountService;
	}

	public static void setCardService(CardService cardService) {
		ServiceContainer.cardService = cardService;
	}
}