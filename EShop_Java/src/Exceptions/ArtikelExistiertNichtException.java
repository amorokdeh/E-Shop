package Exceptions;

import Datenstrukturen.Artikel;

public class ArtikelExistiertNichtException extends EShopException{
	
public ArtikelExistiertNichtException(Artikel artikel) {
		
        super("Artikel mit Name " + artikel.getName() + " und Nummer " + artikel.getNummer() 
                + " existiert nicht" );

	}
}
