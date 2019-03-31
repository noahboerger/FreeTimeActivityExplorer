package org.dhbw.mosbach.ai.freetimeactivityexplorer.general;

public class APINoResultException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4622705401853692066L;

	public APINoResultException() {
		super("WebService not able to connect");
	}
}
