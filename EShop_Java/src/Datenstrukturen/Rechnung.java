package Datenstrukturen;

import java.util.ArrayList;
import java.util.Date;

public class Rechnung {
	
		private Kunde kunde;
	    private Warenkorb warenkorb;
	    private ArrayList<Artikel> artikels;
	    private String date;
	    private Date dt = new Date();

	    public Rechnung(Kunde kunde, Warenkorb warenkorb) {
	        this.kunde = kunde;
	        this.warenkorb = warenkorb;
	       
	    }

	    public String rechnungAusgeben() {
	        String rechnung = "";
	        rechnung += this.kunde.getVorname() + " " +  this.kunde.getNachname() + "\n" 
	        		    + this.kunde.getAdresse() + "\n"
	        		    + "Sie haben folgende Artikel gekauft: ";
	        for (Artikel artikel : warenkorb.getList()) {
	            rechnung += " \n Artikel: " + artikel.getName()
	                         + "\t Artikelnummer: " + artikel.getNummer()
	                         + "\t Anzahl: " + warenkorb.countArtikel(artikel);
	        }
	        return rechnung;
	       
	    }
	 

}
