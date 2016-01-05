package net.coyne.authserver;

public class Authenticator {

	/**
	 * This method will check to see if the inputed parameters can be validated
	 * with our end's information.
	 * @return true if validated.
	 */
	
	protected static int authenticate(String token, String information) {
		if (token.equalsIgnoreCase("Tim")) {
			if (information.equals("password")) {
				return 1;
			}
		} else if (token.equalsIgnoreCase("Greg")) {
			if (information.equals("password")) {
				return 1;
			}
		} else if (token.equalsIgnoreCase("Payton")) {
			if (information.equals("password")) {
				return 1;
			}
		} else if (token.equalsIgnoreCase("Josh")) {
			if (information.equals("password")) {
				return 1;
			}
		} else if (token.equalsIgnoreCase("admin")) {
			if (information.equals("password")) {
				return 1;
			}
		} else {
			return 0;
		}
		return 0;
	}
	
}
