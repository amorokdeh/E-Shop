package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Domain.EShop;
import Server.EShopInterface;

public class FileMenu extends JMenu implements ActionListener {

    private JFrame frame;
    private EShopInterface eShop;

    public FileMenu(JFrame frame, EShopInterface eShop2) {
        super("File");

        this.frame = frame;
        this.eShop = eShop2;

        JMenuItem mi = new JMenuItem("Save");
        //mi.setEnabled(false);
        mi.addActionListener(this);
        this.add(mi);

        this.addSeparator();

        mi = new JMenuItem("Quit");
        mi.addActionListener(this);
        this.add(mi);

        this.addSeparator();

        mi = new JMenuItem("Logout");
        mi.addActionListener(this);
        this.add(mi);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        System.out.println(command);

        if (command.equals("Save")) {
            try {
            	eShop.schreibeArtikel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("Quit")) {
            frame.setVisible(false);
            frame.dispose();

            System.exit(0);
        } else if (command.equals("Logout")) {
            frame.setVisible(false);

            frame.dispose();
            EShopGUI shop = new EShopGUI("Eshop");

        }
    }
}
