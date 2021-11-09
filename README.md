# kurzgeschichten_kreisel

## Datenbankkonfiguration
* Benutzer		kk_client
* Kennwort		p&f_2021
* Datenbank		kurzgeschichten_kreisel
* Der Benutzer  kk_client braucht die "log in" Berechtigung. Unbedingt einstellen.

## HTTP Requests
* Erstellen	    POST	    /shortstories
*               erwartet eine JSON mit folgenden Angaben: title, author, genre, creationDate, text 
*               erstellt einen entsprechenden Eintrag in der Datenbank inkl. id
*               returned JSON mit folgenden Angaben: id, title, author, genre, creationDate, text 

* Bearbeiten	PUT	        /shortstories/$ID
*               erwartet eine JSON mit folgenden Angaben: title, author, genre, creationDate, text 
*               aktualisiert auf der Datenbank: genre, title, text (Datum und Autor etc. würde ich unverändert lassen. Wäre aber schnell zu machen, falls du die Funktionalität möchtest)
*               returned JSON mit folgenden Angaben: id, title, author, genre, creationDate, text 

* Löschen	    DELETE	    /shortstories/$ID
*               löscht den Eintrag mit der übergebenen id aus der Datenbank

* Lesen	        GET	        /shortstories/
*               returned ALLE in der Datenbank gespeicherten Einträgen

* Lesen	        GET	        /shortstories/$ID
*               returned Eintrag mit der übergebenen id als JSON

* Lesen	        GET	        /shortstories/?title=$title
*                           returned Einträge die in der Datenbank den angegebenen Titel hinterlegt haben (Titel nicht einzigartig)

* Lesen	        GET	        /shortstories/?genre=$genre
*                           returned Einträge die in der Datenbank das angegebene Genre hinterlegt haben

* Lesen	        GET	        /shortstories/?author=name
*                           returned Einträge die in der Datenbank den angegebenen Autor hinterlegt haben
