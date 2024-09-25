package Datenstrukturen;

public class MassengutArtikel extends Artikel{

	private int massengut;
	
	public MassengutArtikel(String name, int nummer, int bestand, float preis, int massengut) {
		super(name, nummer, bestand, preis);
		this.massengut = massengut;
	}

	public int getMassengut() {
        return this.massengut;
    }
}
