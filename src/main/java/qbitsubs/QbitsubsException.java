package qbitsubs;

import java.io.IOException;

public class QbitsubsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QbitsubsException(String errorMessage, Throwable err) {
		super(errorMessage, err);
		try {
			Runtime.getRuntime()
					.exec("cmd.exe /c start cmd.exe /k \"echo " + errorMessage + " \n " + err.getMessage() + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
