package databaseinstaller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Einlesen der SQL-Datei
 * @author Markus Badzura
 * @version 1.0.000
 */
public class Databaseinstaller_datei 
{
  ////////////////////////////////////////////////////////////////////
  //                                                                //
  // Deklaration Objekte und Datenarraylist                         //
  //                                                                //
  ////////////////////////////////////////////////////////////////////  
  private static BufferedReader br;
  static Databaseinstaller_errorlog err = new Databaseinstaller_errorlog(); 
  private ArrayList datei = new ArrayList();
  private String inhalt = "";
  private boolean query = false;
  private String anweisung;
  /**
   * Datenbankleseobjekt Konstruktor
   * @param speicherort String Speicherort der SQL-Datei
   * @author Markus Badzura
   * @since 0.0.001
   */
  Databaseinstaller_datei(String speicherort)
  {
    readFile(speicherort);
  }
  /**
   * SQL-Datei auslesen
   * Kommentare werden Zeilenweise der ArrayList hinzugefügt
   * SQL-Anweisungen werden so lange in die Variable anweisungen
   * angefügt, bis ein Semicolon am Zeilenende kommt. Hier erfolgt
   * dann das Hinzufügen zur ArrayList.
   * @param speicherort String Speicherort der SQL-Datei
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void readFile(String speicherort)
  {
    try
    {
      br = new BufferedReader(new InputStreamReader(new 
        FileInputStream(speicherort), StandardCharsets.UTF_8));
      while ((inhalt=br.readLine())!= null)
      {
        if (!"".equals(inhalt))
        {
          if ("#".equals(inhalt.substring(0,1)))
          {
            datei.add(inhalt);
            query = false;
            anweisung = "";
          }
          else
          {
            if(!query)
            {
              anweisung = inhalt;
              query = true;
              if (";".equals(inhalt.trim().substring(inhalt
                      .length()-1)))
              {
                datei.add(anweisung);
                query = false;
              }
            }
            else
            {
              anweisung = anweisung + inhalt;
              if (";".equals(inhalt.trim().substring(inhalt
                      .length()-1)))
              {
                datei.add(anweisung);
                query = false;
              }
            }
          }
        }
      }
    }
    catch(IOException e)
    {
        err.schreibe(e.toString(),"readFile");
    }        
  }
  /**
   * ArrayList mit den Inhalten der SQL-Datei übergeben
   * @return datei ArrayList mit den SQL-Anweisungen und Kommentaren
   * @author Markus Badzura
   * @since 0.0.001
   */
  public ArrayList getDatei()
  {
    return datei;
  }
}