package Exceptions;

import Datenstrukturen.Artikel;

public class ArtikelNichtVerfuegbarException extends EShopException{
	
public ArtikelNichtVerfuegbarException(Artikel artikel, String zusatzMsg) {
		
        super("Artikel mit Name " + artikel.getName() + " und Nummer " + artikel.getNummer() 
                + " existiert nicht verfuegbar" + zusatzMsg);

	}

}
