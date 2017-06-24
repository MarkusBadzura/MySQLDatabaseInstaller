package databaseinstaller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Grafische Benutzeroberfläche des Datenbankinstallers
 * @author Markus Badzura
 * @version 1.0.000
 */
public class Databaseinstaller_gui extends JFrame
        implements ActionListener
{
  ////////////////////////////////////////////////////////////////////
  //                                                                //
  // Deklaration ImageIcon - Objekt und Bildschirmgröße             //
  //                                                                //
  ////////////////////////////////////////////////////////////////////
  private static final URL URLICON = Databaseinstaller_gui.class
          .getResource("Icon.gif");
  private static final ImageIcon ICON = new ImageIcon(URLICON);
  private Databaseinstaller_errorlog err = 
          new Databaseinstaller_errorlog();
  private String hostname, port, user, password,dateipfad;
  private Databaseinstaller_db database;
  private Databaseinstaller_datei dbi_dat;
  ////////////////////////////////////////////////////////////////////
  //                                                                //
  // Deklaration Frame und Bedienelemente                           //
  //                                                                //
  ////////////////////////////////////////////////////////////////////    
  private JPanel jp_left;
  private JScrollPane jsp_right;
  private JTextPane jtp_right;
  private JButton jbt_starten, jbt_beenden;
  private JFileChooser fc;
  private ArrayList daten;
  public void Databaseinstaller_gui()
  {
    try 
    {
        UIManager.setLookAndFeel(UIManager
                .getSystemLookAndFeelClassName());
    } 
    catch (ClassNotFoundException | IllegalAccessException | 
            InstantiationException | UnsupportedLookAndFeelException e) 
    {
        err.schreibe(e.toString(), "Databaseinstaller_gui");
    }
    this.setTitle("Databaseinstaller für MySQL-Server");
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    this.setLayout(null);
    this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e)
        {
            exit();
        }
    });          
    this.setSize(800,600);
    this.setResizable(false);
    this.setIconImage(ICON.getImage());
    this.setMaximumSize(new Dimension(800,600));
    this.setMinimumSize(new Dimension(800,600));
    setUserInterface();
    this.setVisible(true);
  }
  /**
   * Setzen der Benutzeroberfläche
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void setUserInterface()
  {
    jp_left = new JPanel();
    jp_left.setLayout(null);
    jp_left.setSize(this.getWidth()/2,this.getHeight());
    jp_left.setLocation(0,0);
    jbt_starten = new JButton("Starten");
    jbt_starten.addActionListener(this);
    jbt_starten.setBounds(10,10,250,25);
    jbt_beenden = new JButton("Beenden");
    jbt_beenden.addActionListener(this);
    jbt_beenden.setBounds(10,550,250,25);
    jp_left.add(jbt_starten);
    jtp_right = new JTextPane();
    jtp_right.setEditable(false);
    jsp_right = new JScrollPane();
    jsp_right.setBounds((this.getWidth()/2)-100,0,this.getWidth()/2+80,
            this.getHeight()-30);
    jsp_right.setViewportView(jtp_right);
    this.add(jsp_right);
    this.add(jp_left);
  }
   /**
   * Abfragedialog beim Beenden des Programmes, incl. des Schließens
   * über ALT + F4 und dem Schließbutton über die Titelleiste.
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void exit()
  {
    int result = JOptionPane.showConfirmDialog(null, "Möchten Sie "
            + "wirklich beenden?","Programm beenden", 
            JOptionPane.YES_NO_OPTION);
    switch (result)
    {
        case JOptionPane.YES_OPTION:
            System.exit(0);
    }
  }   
  /**
   * Eingabe der für die Datenbankverbindungen notwendigen Parameter
   * hostname, port, user und password. Bei Abbrechen wird Programm
   * versucht zu beenden. Prüfung bei Port, ob Zahleneingabe. Wenn
   * alle Parameter eingegeben wurden, erfolgt eine Prüfung, ob die
   * Datenbankverbindung hergestellt werden kann.
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void inputDatabaseParameters()
  {
    do
    {
      hostname = JOptionPane.showInputDialog(this,"Geben Sie bitte den"
              + " Hostnamen des MySQL-Servers ein.","Hostname",
              JOptionPane.QUESTION_MESSAGE);
      if (hostname == null)
      {
        exit();
      }
    }
    while("".equals(hostname) || hostname == null);
    do
    {
      port = JOptionPane.showInputDialog(this,"Geben Sie bitte den"
              + " Port des MySQL-Servers ein.","Port",
              JOptionPane.QUESTION_MESSAGE);
      if (!port.matches("[1-9][0-9]{1,4}"))
      {
        JOptionPane.showMessageDialog(this, "Der Port eines MySQL-"
                + "Servers besteht aus Ziffern. Standardport wäre die "
                + "3306.","Fehlerhafte Eingabe", JOptionPane.OK_OPTION);
        port = "";
      }
      if (port == null)
      {
        exit();
      }
    }
    while("".equals(port) || port == null);
    do
    {
      user = JOptionPane.showInputDialog(this,"Geben Sie bitte den"
              + " Benutzername für den MySQL-Server ein.","Username",
              JOptionPane.QUESTION_MESSAGE);
      if (user == null)
      {
        exit();
      }      
    }
    while("".equals(user) || user == null);
    do
    {
      password = JOptionPane.showInputDialog(this,"Geben Sie bitte das"
              + " Benutzerpasswort des Datenbankbenutzers an."
              ,"Hostname",JOptionPane.QUESTION_MESSAGE);
      if (password == null)
      {
        exit();
      }      
    }
    while ("".equals(password) || password == null);
    jbt_beenden.setEnabled(false);
    database = new Databaseinstaller_db(hostname, port, user, password);
    boolean temp = database.tryOpenDB();
    if (temp)
    {
      selectFile();
    }
    else
    {
      JOptionPane.showMessageDialog(this, "Mit den von Ihnen "
              + "eingegebenen Daten kann keine Datenbankverbindung "
              + "hergestellt werden.\n\nHostname: "+hostname+"\nPort: "
              +port+"\nBenutzername: "+user+"\nPasswort: "+password,
              "Datenbankverbindung kann nicht hergestellt werden.",
              JOptionPane.OK_OPTION);     
    }
  }
  /**
   * Öffnet den JFileChooser mit der Voreinstellung SQL-Datei
   * Wenn eine SQL-Datei ausgewählt wurde, wird das Einlesen der
   * Datei veranlasst.
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void selectFile()
  {
    fc = new JFileChooser();
    FileNameExtensionFilter filter = 
            new FileNameExtensionFilter("SQL-Datei", "txt", "sql");
    fc.setFileFilter(filter); 
    int auswahl = fc.showOpenDialog(null);
    if (auswahl == JFileChooser.APPROVE_OPTION)
    {
      dateipfad = fc.getSelectedFile().toString();
      dbi_dat = new Databaseinstaller_datei(dateipfad);
      executeSQL();
    }
    fc.approveSelection();    
  }
  /**
   * Der Inhalt der SQL-Datei wird angefordert und als ArrayList
   * übergeben. Wenn es eine Kommentarzeile ist, wird diese direkt
   * an die optische Ausgabe übergeben. Wenn es eine SQL-Anweisung
   * ist, wird diese ausgeführt. Wenn die Ausführung in Ordnung war,
   * wird dies in der optischen Ausgabe mitgeteilt.
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void executeSQL()
  {
    daten = dbi_dat.getDatei();
    String text = "";
    for (int i = 0; i<daten.size();i++)
    {
      String query = (String) daten.get(i);
      text = text + query + "\n";
      if (!query.startsWith("#"))
      {
        database.writeSql(query);
        if(database.isDone())
        {
          text = text + "\n*** SQL-Anweisung ausgeführt ***\n\n";
        }
        else
        {
          text = text + "\n*** SQL-Anweisung fehlerhaft ***\n\n";
        }
      }
      jtp_right.setText(text);
      jsp_right.repaint();
      jbt_beenden.setEnabled(true);
    }    
  }
  /** 
   * Action-Listener
   * @param e ActionEvent Auslösentes Event
   * @author Markus Badzura
   * @since 0.0.001
   */
  @Override
  public void actionPerformed(ActionEvent e) 
  {
    // Button Starten
    if (e.getSource() == jbt_starten)
    {
      inputDatabaseParameters();
    }
    // Button Beenden
    if (e.getSource() == jbt_beenden)
    {
      exit();
    }
  }
}
