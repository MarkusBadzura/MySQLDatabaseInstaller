package databaseinstaller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Databaseinstaller - Errorlog-Datei
 * @author Markus Badzura
 * @version 1.0.000
 */
public class Databaseinstaller_errorlog 
{
  //////////////////////////////////////////////////////////////////
  //                                                              //
  // Variablendeklaration                                         //
  //                                                              //
  //////////////////////////////////////////////////////////////////       
  private String file;

  /**
   * Erzeugen des Dateinamen für den Errorlog
   * Konvention: Aktuelles Datum
   * Alle Errorlogeinträge eines Monats werden in einer Datei 
   * gespeichert
   * @author Markus Badzura
   * @since 0.0.001
   */
  public Databaseinstaller_errorlog()
  {
    LocalDate d = LocalDate.now();
    file = "Databasinstaller_"+d.getYear()+"_"+d.getMonth() +"_errlog.txt";
  }
  /**
   * Error-Log eintrag schreiben
   * @param inhalt Fehlerbeschreibung/Information
   * @param art Klasse und Methode
   * @author Markus Badzura
   * since 0.0.001
   */
  public void schreibe(String inhalt, String art)
  {
    try 
    {
      FileWriter writer = new FileWriter(file,true);
      LocalDateTime t = LocalDateTime.now();
      String zeit = t.getDayOfMonth()+"-"+t.getHour()+":"+t
              .getMinute();
      writer.write(zeit+"\t"+art+"\t"+inhalt+"\r\n");
      writer.close();
    } 
    catch (IOException e) 
    {
    }        
  }    
}
