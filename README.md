# MySQLDatabaseInstaller
Template zum Erstellen einer MySQL-Datenbank

Warum dieser Datenbankersteller?

Ich stehe jedesmal bei Beginn eines neuen Projektes vor dem Problem, wie ich die Datenbankstruktur anlegen kann. Um mir dieses Problem vom Hals zu schaffen, erstelle ich mir einen Datenbank-Installer. 

Voraussetzungen werden sein, dass der MySQL - Server eingerichtet ist und ein Benutzer mit den nötigen Rechten eingerichtet worden ist.

Der Database-Installer erstellt die Datenbankstruktur und befüllt die ersten Tabellen mit Daten (je nachdem, ob es nötig ist oder nicht).
Die notwendigen Informationen, d. h. die notwendigen SQL-Anweisungen werden in einer Textdatei eingegeben, welche vom Datenbankinstaller eingelesen und ausgeführt werden.
Sollte die SQL-Datei fehlerhaft sein, so erfolgt eine Mitteilung und die Datenbank wird wieder gelöscht.
Die ausgeführten SQL-Anweisungen werden im Databaseinstaller mit angezeigt.
