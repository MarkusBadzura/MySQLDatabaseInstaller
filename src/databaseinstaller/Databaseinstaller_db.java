package databaseinstaller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Aufbau der Datenbankverbindung, um Datenbank zu erstellen
 * @author Markus Badzura
 * @version 1.0.000
 */
public class Databaseinstaller_db 
{
  ////////////////////////////////////////////////////////////////////
  //                                                                //
  // Deklaration Variablen und Objekte                              //
  //                                                                //
  ////////////////////////////////////////////////////////////////////    
  private String hostname,user, port, password, url;
  private String dbname = "";
  private Connection connection;
  private Statement statement;
  private Databaseinstaller_errorlog err = 
          new Databaseinstaller_errorlog();
  private boolean ok;
  /** 
   * Konstruktor zum Erstellen des Databaseinstaller_db - Objektes
   * @param hostname String Hostname MySQL-Server
   * @param port String Portnummer des MySQL-Servers
   * @param user String Username für Connection
   * @param password String Passwort für die Connection
   */
  Databaseinstaller_db(String hostname, String port,
          String user, String password)
  {
    this.hostname = hostname;
    this.port = port;
    this.user = user;
    this.password = password;
  }
   /**
   * Prüfung, ob Datenbankverbindung hergestellt werden kann,
   * nachdem die Parameter vom Benutzer eingegeben wurden.
   * @author Markus Badzura
   * @return boolean true oder false
   * @since 0.0.001
   */
  public boolean tryOpenDB()
  {
    boolean connection_ok = false;
    try
    {
      url = "jdbc:mysql://"+hostname+":"+port; 
        connection = DriverManager.getConnection(url, user, password);
      connection_ok = true;
    }
    catch(SQLException e)
    {
      err.schreibe(e.toString(), "tryOpenDB");
    }
    finally
    {
      if (connection_ok)
      {
        try
        {
            connection.close();
        }
        catch(SQLException e)
        {
          err.schreibe(e.toString(), "tryOpenDB");
        }
      }
    }
    return connection_ok;
  }  
  /**
   * Öffnen der Datenbankverbindung
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void openDB()
  {
    try
    {
      if ("".equals(dbname))
      {
        url = "jdbc:mysql://"+hostname+":"+port; 
      }
      else
      {
        url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname;  
      }
        connection = DriverManager.getConnection(url, user, password);
        ok = true;
    }
    catch(SQLException e)
    {
      err.schreibe(e.toString(), "openDB");
    }
  }  
  /**
   * DDL und DML-Anweisungen durchführen
   * @param query DDL oder DML -Anweisung
   * @author Markus Badzura
   * @version 1.0.001
   */
  private void updateDB(String query)
  {
    try
    {
      statement = connection.createStatement();
      statement.executeUpdate(query);
    }
    catch(SQLException e)
    {
      err.schreibe(e.toString(), "Anweisung: "+query);
      ok = false;
    }
  }      
  /**
   * Datenbankverbindung schließen
   * @author Markus Badzura
   * @since 0.0.001
   */
  private void closeDB()
  {
    try
    {
      statement.close();
      connection.close();
    }
    catch(SQLException e)
    {
      err.schreibe(e.toString(), "closeDB");
    }
  }   
  /**
   * Liefert einen Boolen-Wert zurück.
   * true - Wenn die SQL-Anweisung fehlerfrei war
   * false - Wenn die SQL-Anweisung nicht fehlerfrei war
   * @return ok Boolean
   * @author Markus Badzura
   * @since 0.0.001
   */
  public boolean isDone()
  {
    return ok;
  }
  /**
   * Führt die mit der query übergebene SQL-Anweisung durch
   * @param query String mit SQL-Anweisung
   * @author Markus Badzura
   * @since 0.0.001
   */
  public void writeSql(String query)
  {
    if (query.startsWith("USE"))
    {
      dbname = query.substring(4,query.length()-1);
    }
    this.openDB();
    this.updateDB(query);
    this.closeDB();
  }
}
