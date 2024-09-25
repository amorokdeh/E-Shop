package GUI;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import Datenstrukturen.Ereignis;
import Datenstrukturen.Mitarbeiter;

public class MitarbeiterTablePanel extends JTable{
	public interface UpdateBestandListener{
        public void onEnterListArbeiter(int nummer, int menge);
    }

    UpdateBestandListener updateListener = null;

    public MitarbeiterTablePanel(List<Mitarbeiter> mitarbeiter) {
        super();


        //TableModel erzeugen
        MitarbeiterTableModel tableModel = new MitarbeiterTableModel (mitarbeiter);
        //bei JTable "anmelden" und..
        setModel(tableModel);
        //Daten an Model uebergeben
        updateEreignisList(mitarbeiter);

    }

    public void updateEreignisList(java.util.List<Mitarbeiter> mitarbeiter) {
        // Sortierung (mit Lambda-Expression)
        //Collections.sort(buecher, (b1, b2) -> b1.getTitel().compareTo(b2.getTitel()));	// Sortierung nach Titel
        Collections.sort(mitarbeiter, (b1, b2) -> b1.getNummer() - b2.getNummer());	// Sortierung nach Nummer

        // TableModel von JTable holen und ...
        MitarbeiterTableModel tableModel = (MitarbeiterTableModel) getModel();
        // ... Inhalt aktualisieren
        tableModel.setBooks(mitarbeiter);
    }


    public void initialize(UpdateBestandListener listener) {
        updateListener = listener;
    }
}
