package GUI;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import Datenstrukturen.Ereignis;

public class EreignissTablePanel extends JTable {
	
    public interface UpdateBestandListener{
        public void onEnterListArbeiter(int nummer, int menge);
    }
    
    UpdateBestandListener updateListener = null;
    
    public EreignissTablePanel(List<Ereignis> ereignis) {
        super();
        
        
        //TableModel erzeugen
        EreignisTableModel tableModel = new EreignisTableModel (ereignis);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        //Daten an Model uebergeben
        updateEreignisList(ereignis);
        
    }
    
    public void updateEreignisList(java.util.List<Ereignis> ereignis) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
        Collections.sort(ereignis, (b1, b2) -> b1.getBenutzerNr() - b2.getBenutzerNr());	// Sortierung nach Nummer
        
        // TableModel von JTable holen und ...
        EreignisTableModel tableModel = (EreignisTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setBooks(ereignis);
    }
    
    
    public void initialize(UpdateBestandListener listener) {
        updateListener = listener;
    }
}
