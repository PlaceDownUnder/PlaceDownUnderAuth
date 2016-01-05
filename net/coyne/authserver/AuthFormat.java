package net.coyne.authserver;

public class AuthFormat {

	/**
	 * The method will search the string specified for the valid additions that
	 * are required for the string to be verified.
	 * 
	 * The required field is authrequest?=PROTOCOL_VERSION_OF_SERVER
	 * 
	 * @param str
	 * @return Can the string be used as an authentication link
	 */
	
	public static boolean isAuthRequest(String str) {
		if (!(str.contains( "authrequest?=" + ProtocolConstants.PROTOCOL ))) {
			return false;
		}
		return true;
	}
}
