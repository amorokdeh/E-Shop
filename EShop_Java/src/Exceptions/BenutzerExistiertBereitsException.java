package Exceptions;

public class BenutzerExistiertBereitsException extends EShopException{
	  /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public BenutzerExistiertBereitsException() {
	        super("Dieser Benutzer ist bereit existiert");
	    }

}
