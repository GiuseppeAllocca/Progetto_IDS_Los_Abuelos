# Gestione Magazzino

Sistema desktop per la gestione di un magazzino, sviluppato in Java con interfaccia grafica Swing, persistenza JPA/Hibernate e database MySQL.

-----

## Indice

- [Requisiti di sistema](#-requisiti-di-sistema)
- [Tecnologie utilizzate](#-tecnologie-utilizzate)
- [Struttura del progetto](#-struttura-del-progetto)
- [Installazione e configurazione](#-installazione-e-configurazione)
- [Primo avvio: inizializzazione del database](#-primo-avvio-inizializzazione-del-database)
- [Avvio dell’applicazione](#-avvio-dellapplicazione)
- [Credenziali di accesso](#-credenziali-di-accesso)
- [Funzionalità per ruolo](#-funzionalità-per-ruolo)
- [Configurazione notifiche email (opzionale)](#-configurazione-notifiche-email-opzionale)

-----

## Requisiti di sistema

|Requisito      |Versione minima           |
|---------------|--------------------------|
|Java JDK       |21                        |
|Apache Maven   |3.8+                      |
|MySQL Server   |8.0+                      |
|MySQL Workbench|qualsiasi versione recente|
|IDE consigliato|IntelliJ IDEA             |

-----

## Struttura del progetto

```
Java_Project/
├── pom.xml
└── src/main/java/
    ├── it/unina/
    │   └── Main.java                        # Entry point principale
    ├── setup/
    │   ├── MainInizializzaDatabase.java      # Entry point inizializzazione DB
    │   ├── DatiTestUtenti.java               # Utenti di esempio
    │   └── DatiTestProdotti.java             # Prodotti di esempio
    ├── boundary/
    │   ├── MainFrame.java                    # Finestra principale (CardLayout)
    │   ├── LoginPanel.java                   # Schermata di login
    │   ├── UseCasesResponsabilePanel.java    # Dashboard Responsabile
    │   ├── UseCasesOperatorePanel.java       # Dashboard Operatore
    │   ├── CreaProdottoPanel.java            # Creazione prodotto
    │   ├── ModificaProdottoPanel.java        # Modifica prodotto
    │   ├── MovimentazioneProdottiPanel.java  # Carico/scarico prodotti
    │   ├── RicercaProdottiPerFiltroPanel.java# Ricerca con filtri
    │   └── VisualizzaSottoScorta.java        # Prodotti sotto soglia
    ├── controller/
    │   └── ControllerGestione.java           # Logica di controllo
    ├── entity/
    │   ├── Utente.java                       # Entità utente
    │   ├── Prodotto.java                     # Entità prodotto
    │   ├── MovimentoProdotto.java            # Entità movimento
    │   ├── GestoreUtenti.java                # Entità information-expert
    │   ├── GestoreProdotti.java              # Entità information-expert
    │   ├── GestoreMovimenti.java             # Entità information-expert
    │   ├── Categoria.java                    # Enum categorie prodotto
    │   ├── Collocazione.java                 # Enum scaffali
    │   ├── TipoOperatore.java                # Enum ruoli utente
    │   └── TipoMovimentoProdotto.java        # Enum tipo movimento
    ├── database/
    │   ├── JpaUtil.java                      # Singleton EntityManagerFactory
    │   └── GestorePersistenza.java           # Operazioni CRUD su DB
    └── service/
        ├── NotificatoreService.java          # Interfaccia notifiche
        └── BrevoEmailAdapter.java            # Invio email via Brevo API
```

-----

## Installazione e configurazione

### 1. Clona il repository

```bash
git clone <url-del-repository>
cd Java_Project
```

### 2. Crea lo schema del database su MySQL

Apri **MySQL Workbench**, connettiti al tuo server MySQL locale ed esegui il seguente comando SQL per creare lo schema:

```sql
CREATE SCHEMA magazzino_db;
```

> Il nome dello schema deve essere esattamente `magazzino_db`, poiché è quello configurato nel file `persistence.xml`.

### 3. Configura la connessione al database

Apri il file:

```
src/main/resources/META-INF/persistence.xml
```

Individua la proprietà relativa alla password e sostituisci il valore con la password del tuo utente MySQL (di default l’utente è `root`):

```xml
<!-- Modifica solo il campo password -->
<property name="jakarta.persistence.jdbc.user"     value="root"/>
<property name="jakarta.persistence.jdbc.password" value="LA_TUA_PASSWORD"/>
```

Se utilizzi un utente MySQL diverso da `root`, aggiorna anche il campo `user` di conseguenza.

> Il database è configurato per girare in locale sulla porta standard `3306`. Se la tua installazione MySQL usa una porta diversa, aggiorna anche l’URL:
> 
> ```xml
> <property name="jakarta.persistence.jdbc.url"
>           value="jdbc:mysql://127.0.0.1:PORTA/magazzino_db"/>
> ```

### 4. Scarica le dipendenze Maven

```bash
mvn clean install
```

-----

## Primo avvio: inizializzazione del database

> **Questo passaggio va eseguito una sola volta**, prima del primo avvio dell’applicazione. Popola il database con gli utenti e i prodotti di esempio necessari al funzionamento del software.

Esegui la classe:

```
src/main/java/setup/MainInizializzaDatabase.java
```

**Da IDE (IntelliJ / Eclipse):** tasto destro sul file → *Run as Java Application*.

**Da terminale con Maven:**

```bash
mvn exec:java -Dexec.mainClass="setup.MainInizializzaDatabase"
```

Al termine, nella console apparirà il messaggio:

```
Database inizializzato con successo!
```

Questo passaggio crea automaticamente le tabelle su MySQL (grazie a `hibernate.hbm2ddl.auto = update`) e inserisce:

- **2 utenti** di test (1 Responsabile, 1 operatore)
- **3 prodotti** di esempio in magazzino

-----

## Avvio dell’applicazione

Esegui la classe principale:

```
src/main/java/it/unina/Main.java
```

**Da IDE:** tasto destro → *Run as Java Application*.

**Da terminale:**

```bash
mvn exec:java -Dexec.mainClass="it.unina.Main"
```

Si aprirà la finestra dell’applicazione con la schermata di **Login**.

-----

## Credenziali di accesso

Al primo avvio sono disponibili i seguenti account precaricati dal `MainInizializzaDatabase`:

### Responsabile

|Username            |Password      |
|--------------------|--------------|
|`KvaraMagic77`      |`admin`       |


### Operatore

|Username       |Password     |
|---------------|-------------|
|`Marekiaro17`  |`fns`        |


> Per gli operatori, selezionare il ruolo **Operatore** nella schermata di login prima di cliccare *Accedi*.

-----

## Funzionalità per ruolo

### Responsabile

Dalla propria dashboard, il Responsabile può accedere a:

- **Ricerca prodotti per filtro** — cerca prodotti filtrando per nome, categoria, collocazione e stato scorte
- **Creazione prodotto** — registra un nuovo prodotto in magazzino specificando nome, descrizione, quantità iniziale, soglia minima, categoria e scaffale
- **Modifica prodotto** — aggiorna i dati di un prodotto esistente (nome, descrizione, soglia, categoria, posizione)
- **Visualizza prodotti sotto scorta** — lista tutti i prodotti la cui quantità è scesa sotto la soglia minima impostata

### Operatore

Dalla propria dashboard, l’Operatore può accedere a:

- **Ricerca prodotti per filtro** — stessa funzione del responsabile, in sola consultazione
- **Movimentazione prodotti** — registra operazioni di **carico** (ingresso merce) o **scarico** (uscita merce) per un prodotto, specificando quantità e data

-----

## Dati di riferimento

### Categorie prodotto disponibili

|Valore               |Descrizione           |
|---------------------|----------------------|
|`VESTIARIO_SPORTIVO` |Abbigliamento sportivo|
|`DISPOSITIVI_APPLE`  |Prodotti Apple        |
|`DISPOSITIVI_SAMSUNG`|Prodotti Samsung      |
|`CIBO`               |Prodotti alimentari   |
|`NAPOLI`             |Prodotto a tema SSC-Na|

### Collocazioni (scaffali) disponibili

`SCAFFALE_1` · `SCAFFALE_2` · `SCAFFALE_3` · `SCAFFALE_4` · `SCAFFALE_5`

-----

## Configurazione notifiche email (opzionale)

Il sistema supporta l’invio di notifiche email tramite il servizio cloud **[Brevo](https://www.brevo.com/)** quando un prodotto scende sotto la soglia di scorta minima.

Per abilitarlo bisogna modificare il file `config_example.properties` nella cartella delle risorse (`src/main/resources/`), il file contiene 3 variabili:

```properties
brevo.api.key=TUA_API_KEY_BREVO
brevo.mittente.email=tuo@indirizzo.com
brevo.destinatario.email=destinatario@indirizzo.com
```
>Se il file non è presente o non è configurato, l’applicazione funziona normalmente senza invio di notifiche.

>[!NOTE]
Le credenziali e i parametri necessari per l’attivazione del servizio di notifiche email verranno forniti durante la seduta d’esame, al fine di consentire una verifica completa delle funzionalità del software.

