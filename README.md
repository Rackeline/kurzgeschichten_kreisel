# kurzgeschichten_kreisel

## Datenbankkonfiguration
- Benutzer kk_client
- Kennwort p&f_2021
- Datenbank kurzgeschichten_kreisel
- Der Benutzer kk_client braucht die "log in" Berechtigung. Unbedingt einstellen.

## HTTP Requests
- Erstellen POST /shortstories
-               erwartet eine JSON mit folgenden Angaben: title, genre, creationDate, text
-               erstellt einen entsprechenden Eintrag in der Datenbank inkl. id
-               returned JSON mit folgenden Angaben: id, title, author, genre, creationDate, text

- Bearbeiten PUT /shortstories/\$ID
-               erwartet eine JSON mit folgenden Angaben: title, genre, text
-               aktualisiert auf der Datenbank: genre, title, text (Datum und Autor etc. würde ich unverändert lassen. Wäre aber schnell zu machen, falls du die Funktionalität möchtest)
-               returned JSON mit folgenden Angaben: id, title, author, genre, creationDate, text

- Löschen DELETE /shortstories/\$ID
-               löscht den Eintrag mit der übergebenen id aus der Datenbank

- Lesen GET /shortstories/
-               returned ALLE in der Datenbank gespeicherten Einträgen

- Lesen GET /shortstories/\$ID
-               returned Eintrag mit der übergebenen id als JSON

- Lesen GET /shortstories/?title=\$title
-               returned Einträge die in der Datenbank den angegebenen Titel hinterlegt haben (Titel nicht einzigartig)

- Lesen GET /shortstories/?genre=\$genre
-               returned Einträge die in der Datenbank das angegebene Genre hinterlegt haben

- Lesen GET /shortstories/?author=name
-               returned Einträge die in der Datenbank den angegebenen Autor hinterlegt haben

- Limit Optional an allen Endpunkten möglich. Erwartet Ganzzahl (int). Beispiel /shortstories/?genre=\$genre&limit=3

## Security
### Registrierung    /user/register
- Client schickt:
-   {
-   username: String,
-   role: String,
-   password: String,
-   email: String
-   }

- Server antwortet:
-   {
-   message: String //(hat geklappt/hat nicht geklappt)
-   }

### Login            /user/login
- Client schickt:
-   {
-   username: String,
-   password: String
-   }

- Server antwortet:
-   {
-   token: String,
-   username: String,
-   email: String,
-   roles: String[]
-   }

### Token (JsonWebToken): 
- Token muss nach Login mit jedem Folgerequest als http-Header im Authorization-Feld als Bearer-Token: (Bsp: Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...) mitgeschickt werden.
- "Bearer" muss vom Client dem Token vor Verwendung vorangestellt werden (s. Beispiel oben)
- Bei Abmeldung (logout) muss das Token beim Client gelöscht werden --> wird nicht mehr mitgeschickt. Keine Serverseitige Abmeldung notwendig.

## 10.11.2021: nach Refactoring: DB Table Namen angepasst --> unbedingt umbenennen:
ALTER TABLE kurzgeschichte RENAME TO shortstory;
ALTER TABLE benutzer RENAME TO "user";

## 14.11.2121: 
- Erstellen POST: Endpunkt benötigt keinen Autor mehr --> übernimmt den eingeloggten Usernamen als Autor
- Bearbeiten PUT: Endpunkt benötigt nur noch: title, genre, text
