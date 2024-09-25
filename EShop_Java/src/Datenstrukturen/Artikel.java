package Datenstrukturen;

/**
 * Klasse zur Artikeln
 * 
 * @authors Mohamad Alkasem and Amr Okdeh
 */

public class Artikel {
	
	// Eigenschaften der Artiekln 
	
	private String artikelName;
	private int artikelNummer;
	private int artikelBestand;
	//private MassengutArtikel massengut;
	private float artikelPreis;
	private boolean verfeugbar;
	
	// Constructor Artikel
	
	public Artikel(String name, int nummer, int bestand, float preis) {
		this.setName(name);
		this.setNummer(nummer);
		this.setPreis(preis);
		this.setBestand(bestand);
		//this.massengut = new MassengutArtikel(name, nummer, bestand, preis, 1);
		if (bestand > 0 ) {this.verfeugbar = true;}
	}
	
	// Sortieren der Artikeln 
	
	public String toString() {
		
		String Verfeugbarkeit = verfeugbar ? "Artikel ist auf Lager" : "Artiekel ist nicht auf Lager"; // überpruft Verfügbarkeit Artikel 
		
		return (
				"Name: " + artikelName + " | Nummer: " + artikelNummer + " | Preis: " + artikelPreis + " | Bestand: " + artikelBestand 
				+ " | Verfügbarkeit: " + Verfeugbarkeit);
	}
	
	
	/**
     * Standard-Methode von Object überschrieben.
     * Methode dient Vergleich von zwei Artikeln-Objekten anhand ihrer Werte,
     * d.h. artikelNummer und artikelName.
     * 
     * @see java.lang.Object#toString()
     */
	
	public boolean equals(Object andereArtikel) {
        if (andereArtikel instanceof Artikel) 
            return ((this.artikelNummer == ((Artikel) andereArtikel).artikelNummer) 
                    && (this.artikelName.equals(((Artikel) andereArtikel).artikelName)));
        else

            return false;
          }
	
	
	//getter
	public String getName() {
		return this.artikelName;
	}
	public int getNummer() {
		return this.artikelNummer;
	}
	public int getBestand() {
		return this.artikelBestand;
	}
	public float getPreis() {
		return this.artikelPreis;
	}
	
	
	public boolean getVerfuegbar() {
		return this.verfeugbar;
	}
	
	//setter
	public void setName(String name) {
		this.artikelName = name;
	}
	public void setNummer(int num) {
		this.artikelNummer = num;
	}
	public void setBestand(int bestand) {
		this.artikelBestand = bestand;
	}
	public void setPreis(float preis) {
		this.artikelPreis = preis;
	}
	
	
	public void setVerfeugbar(boolean verfeugbar) {
		this.verfeugbar = verfeugbar;
	}

	
}
