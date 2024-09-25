package GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Datenstrukturen.Artikel;
import Datenstrukturen.MassengutArtikel;

public class ArtikelTableModel extends AbstractTableModel/*DefaultTableModel*/  {

    private List<Artikel> artikel;
    private String[] spaltenNamen = { "Nummer","Titel", "Preis", "Bestand" };


    public ArtikelTableModel(List<Artikel> aktuelleArtikel) {
        super();
        // Ich erstelle eine Kopie der ArtikelListe,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        artikel = new Vector<Artikel>();
        artikel.addAll(aktuelleArtikel);
    }

    public void setArtikel(List<Artikel> aktuelleArtikel){
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        fireTableDataChanged();
    }

    /*
     * Ab hier �berschriebene Methoden mit Informationen,
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */

    @Override
    public int getRowCount() {
        if(artikel != null) {
            return artikel.size();
        }else {
            return 0;
        }
    }


    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Artikel gewaehltesArtikel = artikel.get(row);
        switch (col) {
            case 0:
                return gewaehltesArtikel.getNummer();
            case 1:
                return gewaehltesArtikel.getName();
            case 2:
                return gewaehltesArtikel.getPreis();
            case 3:
                return gewaehltesArtikel.getBestand();
            case 4:
                
            default:
                return null;
        }
    }
    @Override 
    public void setValueAt(Object value, int row, int col) {
        Artikel gewaehltesArtikel = artikel.get(row);

        switch (col) {
            case 0:
                gewaehltesArtikel.setNummer(Integer.parseInt(value.toString()));
            case 1:
                gewaehltesArtikel.setName(value.toString());
            case 2:
                gewaehltesArtikel.setPreis(Integer.parseInt(value.toString()));
            case 3:
                gewaehltesArtikel.setBestand(Integer.parseInt(value.toString()));
            case 4:
                //           	gewaehltesArtikel.setMassengut(Integer.parseInt(value.toString()));
            default:

        }
    }
}
