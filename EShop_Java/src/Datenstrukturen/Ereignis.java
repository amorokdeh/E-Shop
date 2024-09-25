package Datenstrukturen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ereignis {

	private String typ;
	private String benutzerName;
	private int artikelNr;
	private int bestandsAenderung;
	private int benutzerNr;
	private String datum;
	
	 public Ereignis(String typ, int artikelNr, int bestandsAenderung, int benutzerNr, String benutzerName) {
	        this.typ = typ;
	        this.artikelNr = artikelNr;
	        this.bestandsAenderung = bestandsAenderung;
	        this.benutzerNr = benutzerNr;
	        this.datum =  new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(new Date());
	        this.benutzerName = benutzerName;
	    }
	 public Ereignis(String typ, int artikelNr, int bestandsAenderung, int benutzerNr, String benutzerName, String datum) {
	        this.typ = typ;
	        this.artikelNr = artikelNr;
	        this.bestandsAenderung = bestandsAenderung;
	        this.benutzerNr = benutzerNr;
	        this.datum =  datum;
	        this.benutzerName = benutzerName;
	    }
	 
	public String getTyp() {
		return typ;
	}

	public String getBenutzerName() {
		return benutzerName;
	}

	public int getArtikelNr() {
		return artikelNr;
	}

	public int getBestandsAenderung() {
		return bestandsAenderung;
	}

	public int getBenutzerNr() {
		return benutzerNr;
	}

	public String getDatum() {
		 return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

}
