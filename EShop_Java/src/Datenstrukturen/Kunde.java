package Datenstrukturen;
/**
 * Klasse zur Person
 * 
 * @authors Mohamad Alkasem and Amr Okdeh
 */


public class Kunde extends Person {
	
	private String strasse; 
	private String hausNr; 
	private int plz;
	private String ort;
	
	
	public Kunde (String Vorname, String Nachname, int Nummer, String BenutzerName, 
					String Password,String strasse, String hausNr, int plz, String ort) {
		
		super( Vorname, Nachname, Nummer, BenutzerName, Password);
		this.strasse = strasse;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
			
	}
	
	
	public String getStrasse() {
		return strasse;
	}


	public String getHausNr() {
		return hausNr;
	}


	public int getPlz() {
		return plz;
	}


	public String getOrt() {
		return ort;
	}
	
	
	

	public String getAdresse() {
		
		String Adresse = "";
		Adresse +=  getStrasse() + " " + getHausNr() + "\n" + getPlz() + " " + getOrt();
		
		return Adresse;
	}
	
	
	

	public void setAdresse(String strasse, String hausNr, int plz, String ort) {
		
		this.strasse = strasse;
		this.hausNr = hausNr;
		this.plz = plz;
		this.ort = ort;
	}
	
	
	// Getter  Kunde auf Warenkorb 
	
	  Warenkorb warenkorb = new Warenkorb();
	  public Warenkorb getWarenkorb() { return warenkorb; }
	
	
}
	
	




