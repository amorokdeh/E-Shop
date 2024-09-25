package Datenstrukturen;
/**
 * Klasse zur Person
 * 
 * @authors Mohamad Alkasem and Amr Okdeh
 */

public abstract class Person {
	
	// Eigenschaften der Klasse Person 
	
	private String vorname;
	private String nachname;
	private int nummer; 
	private String benutzerName;
	private String password;
	
	// Constructor Person
	
	public Person( String Vorname, String Nachname, int Nummer, String BenutzerName, String Password) {
		this.vorname = Vorname;
		this.nachname = Nachname;
		this.nummer = Nummer;
		this.benutzerName = BenutzerName;
		this.password = Password;
		
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public int getNummer() {
		return nummer;
	}

	
	  public void setNummer(int nummer) {
	 
		this.nummer = nummer;
	} 
	
	public String getBenutzerName() {
		return benutzerName;
	}

	public void setBenutzerName(String benutzerName) {
		this.benutzerName = benutzerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
