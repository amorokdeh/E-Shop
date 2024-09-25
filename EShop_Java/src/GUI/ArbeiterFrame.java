package GUI;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import Datenstrukturen.Artikel;
import Datenstrukturen.Ereignis;
import Datenstrukturen.Mitarbeiter;
import Domain.EShop;
import GUI.AddArtikelPanel.AddArtikelListener;
import GUI.EditArtikelTablePanel.UpdateBestandListener;
import GUI.SearchArtikelPanel.SearchResultListener;
import Server.EShopInterface;

public class ArbeiterFrame extends JFrame implements SearchResultListener, AddArtikelListener, UpdateBestandListener {

    private JPanel contentPane;
    private SearchArtikelPanel searchPanel;
    private AddArtikelPanel addPanel;
    private EditArtikelTablePanel artikelPanel;
    private EShopInterface eShop;
    private JTabbedPane tabbedPane;
    private EreignissTablePanel ereignisPanel;
    private MitarbeiterTablePanel mitarbeiterPanel;

    /**
     * Create the frame.
     */
    public ArbeiterFrame(EShopInterface eShop2) {
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

        //north
        searchPanel = new SearchArtikelPanel();
        searchPanel.initialize(eShop2, this);

        //west
        addPanel = new AddArtikelPanel();
        addPanel.initialize(eShop2, this);

        //Center
        java.util.List<Artikel> artikel = eShop2.gibAlleArtikel();
        java.util.List<Ereignis> ereignis = eShop2.gibEreignisListe();
        java.util.List<Mitarbeiter> mitarbeiter = eShop2.gibAlleMitarbeiter();

        //Zusammenbau in BorderLayout des Frames
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(addPanel, BorderLayout.WEST);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        // (wahlweise Anzeige als Liste oder Tabelle)
        //booksPanel = new BooksListPanel(artikel);
        artikelPanel = new EditArtikelTablePanel(artikel);
        JScrollPane scrollPane = new JScrollPane(artikelPanel);
        tabbedPane.addTab("Artikel", null, scrollPane, null);

        ereignisPanel = new EreignissTablePanel(ereignis);
        JScrollPane scrollPane_1 = new JScrollPane(ereignisPanel);
        tabbedPane.addTab("Ereignis", null, scrollPane_1, null);
        
        mitarbeiterPanel = new MitarbeiterTablePanel(mitarbeiter);
        JScrollPane scrollPane_2 = new JScrollPane(mitarbeiterPanel);
        tabbedPane.addTab("Mitarbeiter", null, scrollPane_2, null);

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
     * Listener, der Benachrichtungen erh�lt, wenn im AddBookPanel ein Buch eingef�gt wurde.
     * (Als Reaktion soll die B�cherliste aktualisiert werden.)
     * @see bib.local.ui.gui.panels.AddBookPanel.AddBookListener#onBookAdded(bib.local.valueobjects.Buch)
     */
    @Override
    public void onArtikelAdded(Artikel artikel) {
        //artikel neu laden und anzeigen
        java.util.List<Artikel> artikelListe = eShop.gibAlleArtikel();
        java.util.List<Ereignis> ereignis = eShop.gibEreignisListe();
        java.util.List<Mitarbeiter> mitarbeiter = eShop.gibAlleMitarbeiter();
        artikelPanel.updateArtikelList(artikelListe);
        ereignisPanel.updateEreignisList(ereignis);
        mitarbeiterPanel.updateEreignisList(mitarbeiter);
    }
    
    public void update() {
        //artikel neu laden und anzeigen
        java.util.List<Artikel> artikelListe = eShop.gibAlleArtikel();
        java.util.List<Ereignis> ereignis = eShop.gibEreignisListe();
        java.util.List<Mitarbeiter> mitarbeiter = eShop.gibAlleMitarbeiter();
        artikelPanel.updateArtikelList(artikelListe);
        ereignisPanel.updateEreignisList(ereignis);
        mitarbeiterPanel.updateEreignisList(mitarbeiter);
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
        java.util.List<Ereignis> ereignis = eShop.gibEreignisListe();
        java.util.List<Mitarbeiter> mitarbeiter = eShop.gibAlleMitarbeiter();
        ereignisPanel.updateEreignisList(ereignis);
        mitarbeiterPanel.updateEreignisList(mitarbeiter);
    }

    public void onEnterListArbeiter(int nummer, int menge) {
    	eShop.aendereBestand(nummer, menge);
        JOptionPane.showMessageDialog(this, "Bestand " + nummer + " um " + menge + "Stueck local geaendert");
    }

}
