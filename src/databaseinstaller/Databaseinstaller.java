package databaseinstaller;

/**
 * Datenbank erstellen für MySQL-Server
 * Startklasse
 * @author Markus Badzura
 * @version 1.0.000
 */
public class Databaseinstaller 
{
  /**
   * Einstiegspunkt des Databaseinstallers
   * Objektaufruf der Benutzeroberfläche
   * @param args String[] Keine Parameter übergeben
   * @author Markus Badzura
   * @since 0.0.001
   */
  public static void main(String[] args) 
  {
    Databaseinstaller_gui dbig = new Databaseinstaller_gui();
    dbig.Databaseinstaller_gui();
  }
}
