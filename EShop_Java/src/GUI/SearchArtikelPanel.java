package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Datenstrukturen.Artikel;
import Domain.EShop;
import Server.EShopInterface;

//Wichtig: Das AddBookPanel _ist ein_ Panel und damit auch eine Component;
//es kann daher in das Layout eines anderen Containers
//(in unserer Anwendung des Frames) eingefuegt werden.
public class SearchArtikelPanel extends JPanel {

  // Ueber dieses Interface uebermittelt das SearchBooksPanel
  // Suchergebnisse an einen Empfaenger.
  // In unserem Fall ist der Empfaenger die BibGuiMitKomponenten,
  // die dieses Interface implementiert und auf ein neues
  // Suchergebnis reagiert, indem sie die Buecherliste aktualisiert.
  public interface SearchResultListener {
      public void onSearchResult(List<Artikel> artikelList);
  }


  private EShopInterface eShop = null;
  private SearchResultListener searchListener = null;

  private JTextField searchTextField;
  private JButton searchButton = null;

  public SearchArtikelPanel() {
      setupUI();

      setupEvents();
  }

  public void initialize(EShopInterface eShop2, SearchResultListener listener) {
      this.eShop = eShop2;
      this.searchListener = listener;
  }

  private void setupUI() {
      // GridBagLayout
      // (Hinweis: Das ist schon ein komplexerer LayoutManager, der mehr kann als hier gezeigt.
      //  Hervorzuheben ist hier die Idee, explizit Constraints (also Nebenbedindungen) f�r
      //  die Positionierung / Ausrichtung / Groesse von GUI-Komponenten anzugeben.)
      GridBagLayout gridBagLayout = new GridBagLayout();
      this.setLayout(gridBagLayout);
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridy = 0;	// Zeile 0

      JLabel searchLabel = new JLabel("Suchbegriff:");
      c.gridx = 0;	// Spalte 0
      c.weightx = 0.2;	// 20% der gesamten Breite
      c.anchor = GridBagConstraints.EAST;
      gridBagLayout.setConstraints(searchLabel, c);
      this.add(searchLabel);

      searchTextField = new JTextField();
      searchTextField.setToolTipText("Suchbegriff eingeben.");
      c.gridx = 1;	// Spalte 1
      c.weightx = 0.6;	// 60% der gesamten Breite
      gridBagLayout.setConstraints(searchTextField, c);
      this.add(searchTextField);

      searchButton = new JButton("Such!");
      c.gridx = 2;	// Spalte 2
      c.weightx = 0.2;	// 20% der gesamten Breite
      gridBagLayout.setConstraints(searchButton, c);
      this.add(searchButton);

      // Rahmen definieren
      setBorder(BorderFactory.createTitledBorder("Suche"));
  }

  private void setupEvents() {
      searchButton.addActionListener(new SuchListener());
  }

  // Lokale Klasse fuer Reaktion auf Such-Klick
  class SuchListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent ae) {
          if (ae.getSource().equals(searchButton)) {
              String suchbegriff = searchTextField.getText();
              java.util.List<Artikel> suchErgebnis;
              if (suchbegriff.isEmpty()) {
                  suchErgebnis = eShop.gibAlleArtikel();
              } else {
                  suchErgebnis = eShop.sucheNachArtikelName(suchbegriff);
              }

              // Listener benachrichtigen, damit er die Ausgabe aktualisieren kann
              searchListener.onSearchResult(suchErgebnis);
          }
      }
  }
}