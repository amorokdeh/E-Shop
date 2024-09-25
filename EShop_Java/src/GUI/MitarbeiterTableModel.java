package GUI;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Datenstrukturen.Ereignis;
import Datenstrukturen.Mitarbeiter;

public class MitarbeiterTableModel extends AbstractTableModel{
	private List<Mitarbeiter> mitarbeiter;
    private String[] spaltenNamen = { "Benutzername","Vorname", "Nachname", "Mitarbeiternummer" };


    public MitarbeiterTableModel(List<Mitarbeiter> mitarbeitern) {
        super();
        // Ich erstelle eine Kopie der B�cherliste,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        mitarbeiter = new Vector<Mitarbeiter>();
        mitarbeiter.addAll(mitarbeitern);
    }

    public void setBooks(List<Mitarbeiter> mitarbeitern){
    	mitarbeiter.clear();
    	mitarbeiter.addAll(mitarbeitern);
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
        if(mitarbeiter != null) {
            return mitarbeiter.size();
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
        Mitarbeiter gewaehltesMit = mitarbeiter.get(row);
        switch (col) {
            case 0:
                return gewaehltesMit.getBenutzerName();
            case 1:
                return gewaehltesMit.getVorname();
            case 2:
                return gewaehltesMit.getNachname();
            case 3:
                return gewaehltesMit.getNummer();
            default:
                return null;
        }
    }
}
