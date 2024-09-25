package Exceptions;

import Datenstrukturen.Artikel;

public class ArtikelExistiertBereitsException extends EShopException{
	
	
 public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		
        super("Artikel mit Name " + artikel.getName() + " und Nummer " + artikel.getNummer() 
                + " existiert bereits" + zusatzMsg);
 }
 
 public ArtikelExistiertBereitsException(String zusatzMsg) {
		
     super("Artikel ist existiert bereits" + zusatzMsg);
}
}