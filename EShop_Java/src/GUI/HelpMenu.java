package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HelpMenu extends JMenu implements ActionListener {
    public HelpMenu() {
        super("Help");

        // Nur zu Testzwecken: Men� mit Untermen�
        JMenuItem miP = new JMenuItem("Programmers");
        miP.addActionListener(this);
        add(miP);

        // Geht in AWT auch (alternativ zu mi.addActionListener(this)-Aufrufen oben):
//		m.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand()== "Programmers") {
            JOptionPane.showMessageDialog(null, "Dieses Programm wurde Programmiert von aaa","",1);

        }


        System.out.println("Klick auf Menue '" + e.getActionCommand() + "'.");
    }
}
