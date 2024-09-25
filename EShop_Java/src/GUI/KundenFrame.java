package GUI;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import Datenstrukturen.Artikel;
import Datenstrukturen.Mitarbeiter;
import Domain.EShop;
import Exceptions.ArtikelExistiertNichtException;
import GUI.AddArtikelInWarenkorbPanel.AddArtikelInWarenkorbListener;
import GUI.DeleteArtikelInWarenkorb.DeleteArtikelInWarenkorbListener;
import GUI.EditWarenkorbPanel.EditWarenkorbListener;
import GUI.SearchArtikelPanel.SearchResultListener;
import Server.EShopInterface;

public class KundenFrame extends JFrame implements SearchResultListener, AddArtikelInWarenkorbListener, EditWarenkorbListener, DeleteArtikelInWarenkorbListener {

    private JPanel contentPane;
    private EShopInterface eShop;
    private SearchArtikelPanel searchPanel;

    private JTabbedPane tabbedPane;

    // Panel Bestand
    private JPanel panelBestand;
    private AddArtikelInWarenkorbPanel addArtikelInWarenkorbPanel;
    private ShowArtikelTablePanel artikelPanel;

    // Panel Warenkorb
    private JPanel panelWarenkorb;
    private EditWarenkorbPanel editWarenkorbPanel;
    private WarenkorbTablePanel warenkorbPanel;
    private JTabbedPane tabbedPane_1;
    private DeleteArtikelInWarenkorb deleteWarenkorbPanel;

    /**
     * Create the frame.
     */
    public KundenFrame(EShopInterface eShop2) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        this.eShop = eShop2;

        //Menu definieren
        setupMenu();


        //Klick auf Kreu / roten Kreis (Fenster schlie�en)
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Layout des Frames : BorderLayout
        getContentPane().setLayout(new BorderLayout());

        //Center
        java.util.List<Artikel> artikel = eShop2.gibAlleArtikel();
        // TODO: Warenkorb des Kunden
        java.util.List<Artikel> warenkorb = eShop2.warenkorbAnzeigen();
        //java.util.List<Mitarbeiter> mitarbeiter = eShop2.gibAlleMitarbeiter();

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        panelBestand = new JPanel();
        tabbedPane.addTab("Bestand", null, panelBestand, null);

        //west
        addArtikelInWarenkorbPanel = new AddArtikelInWarenkorbPanel();
        addArtikelInWarenkorbPanel.setBounds(0, 53, 98, 329);
        addArtikelInWarenkorbPanel.initialize(eShop2, this);
        panelBestand.setLayout(null);

        //north
        searchPanel = new SearchArtikelPanel();
        searchPanel.setBounds(0, 0, 609, 53);
        panelBestand.add(searchPanel);
        searchPanel.initialize(eShop2, this);
        panelBestand.add(addArtikelInWarenkorbPanel);
        // (wahlweise Anzeige als Liste oder Tabelle)
        //booksPanel = new BooksListPanel(artikel);
        artikelPanel = new ShowArtikelTablePanel(artikel);
        artikelPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        JScrollPane scrollPaneBestand = new JScrollPane(artikelPanel);
        scrollPaneBestand.setBounds(98, 53, 511, 329);
        panelBestand.add(scrollPaneBestand);


        panelWarenkorb = new JPanel();
        tabbedPane.addTab("Warenkorb", null, panelWarenkorb, null);
        panelWarenkorb.setLayout(new BorderLayout(0, 0));
        warenkorbPanel = new WarenkorbTablePanel(warenkorb);
        JScrollPane scrollPaneWarenkorb = new JScrollPane(warenkorbPanel);
        scrollPaneWarenkorb.setBounds(98, 53, 511, 329);
        //panelBestand.add(scrollPaneWarenkorb);
        panelWarenkorb.add(scrollPaneWarenkorb, BorderLayout.CENTER);

        tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        panelWarenkorb.add(tabbedPane_1, BorderLayout.WEST);

        editWarenkorbPanel = new EditWarenkorbPanel();
        tabbedPane_1.addTab("Bearbeiten", null, editWarenkorbPanel, null);
        editWarenkorbPanel.initialize(eShop2, this);

        deleteWarenkorbPanel = new DeleteArtikelInWarenkorb();
        tabbedPane_1.addTab("Loeschen", null, deleteWarenkorbPanel, null);
        deleteWarenkorbPanel.initialize(eShop2, this);


        this.setSize(640, 480);
        this.setVisible(true);
    }

    private void setupMenu() {
        // Men�leiste anlegen ...
        JMenuBar mBar = new JMenuBar();

        JMenu fileMenu = new FileMenu(this, eShop);
        mBar.add(fileMenu);

        JMenu helpMenu = new HelpMenu();
        mBar.add(helpMenu);

        // ... und beim Fenster anmelden
        this.setJMenuBar(mBar);
    }

    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erh�lt, wenn im Warnkorb ein Buch eingef�gt wurde.
     */
    @Override
    public void onArtikelAdded(Artikel artikel) {
        // TODO: Warenkorb zeugs
        warenkorbPanel.updateArtikelList(eShop.warenkorbAnzeigen());
    }


    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = artikelPanel.getSelectedRow();
        String artikelNumber = artikelPanel.getValueAt(selectedRow, 0).toString();
        addArtikelInWarenkorbPanel.setNumberTextFieldText(artikelNumber);
    }


    /*
     * (non-Javadoc)
     *
     * Listener, der Benachrichtungen erh�lt, wenn das SearchBooksPanel ein Suchergebnis bereitstellen m�chte.
     * (Als Reaktion soll die B�cherliste aktualisiert werden.)
     * @see bib.local.ui.gui.swing.panels.SearchBooksPanel.SearchResultListener#onSearchResult(java.util.List)
     */
    @Override
    public void onSearchResult(List<Artikel> artikelList) {
        artikelPanel.updateArtikelList(artikelList);
    }

    @Override
    public void onArtikelRemoved() {
        warenkorbPanel.updateArtikelList(eShop.warenkorbAnzeigen());
    }

    @Override
    public void onWarenkorbEdited() {
        // TODO Auto-generated method stub
        warenkorbPanel.updateArtikelList(eShop.warenkorbAnzeigen());
    }

    @Override
    public void onCheckOut() {
			warenkorbPanel.updateArtikelList(eShop.warenkorbAnzeigen());
	        artikelPanel.updateArtikelList(eShop.gibAlleArtikel());
    }
}
