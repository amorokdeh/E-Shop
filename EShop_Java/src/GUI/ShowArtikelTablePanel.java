package GUI;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import Datenstrukturen.Artikel;
import Datenstrukturen.Mitarbeiter;

public class ShowArtikelTablePanel extends JTable {

    public ShowArtikelTablePanel(List<Artikel> artikel, List<Mitarbeiter> mitarbeiter) {
        super();

        //TableModel erzeugen
        ArtikelTableModel tableModel = new ArtikelTableModel (artikel);
        MitarbeiterTableModel tableModel2 = new MitarbeiterTableModel(mitarbeiter);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        setModel(tableModel2);
        //Daten an Model uebergeben
        updatelList(artikel, mitarbeiter);
    }
    
    public ShowArtikelTablePanel(List<Artikel> artikel) {
        super();

        //TableModel erzeugen
        ArtikelTableModel tableModel = new ArtikelTableModel (artikel);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        //Daten an Model uebergeben
        updateArtikelList(artikel);
    }
 
    public void updatelList(java.util.List<Artikel> artikel, java.util.List<Mitarbeiter> mitarbeiter) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
        Collections.sort(artikel, (b1, b2) -> b1.getNummer() - b2.getNummer());	// Sortierung nach Nummer
        Collections.sort(mitarbeiter, (b1, b2) -> b1.getNummer() - b2.getNummer());	// Sortierung nach Nummer
        // TableModel von JTable holen und ...
        ArtikelTableModel tableModel = (ArtikelTableModel) getModel();
        MitarbeiterTableModel tableModel2 = (MitarbeiterTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setArtikel(artikel);
        tableModel2.setBooks(mitarbeiter);
    }
    
    public void updateArtikelList(java.util.List<Artikel> artikel) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
        Collections.sort(artikel, (b1, b2) -> b1.getNummer() - b2.getNummer());	// Sortierung nach Nummer
        
        // TableModel von JTable holen und ...
        ArtikelTableModel tableModel = (ArtikelTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setArtikel(artikel);
    }
}

