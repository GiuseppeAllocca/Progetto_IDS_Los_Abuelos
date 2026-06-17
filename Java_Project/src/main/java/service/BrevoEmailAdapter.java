package service;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;

/**
 * Adattatore concreto che implementa l'interfaccia NotificatoreService.
 * Si occupa di inviare e-mail di allerta sfruttando le API REST basate su HTTPS del servizio cloud Brevo.
 */
public class BrevoEmailAdapter implements NotificatoreService {

    // URL ufficiale fornito da Brevo per l'invio delle e-mail transazionali
    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    // Variabili che saranno popolate dal nostro file .properties
    private String apiKey;
    private String mittenteEmail;
    private String destinatarioEmail;

    // Il client HTTP nativo di Java utilizzato per effettuare la richiesta web
    private final HttpClient httpClient;

    /**
     * Il costruttore inizializza il client di rete e carica i dati sensibili
     * dal file di configurazione
     */
    public BrevoEmailAdapter() {
        // HttpClient.newHttpClient() crea un'istanza nativa di java
        // per gestire le connessioni HTTP/2
        this.httpClient = HttpClient.newHttpClient();

        // .getClassLoader().getResourceAsStream cerca il file "config.properties"
        // direttamente nella cartella delle risorse compilate (classpath)
        try (InputStream input = BrevoEmailAdapter.class.getClassLoader().getResourceAsStream("config.properties")) {

            // L'oggetto Properties serve a leggere file
            // strutturati in formato "chiave = valore"
            Properties prop = new Properties();

            // Controllo di sicurezza: se il file non esiste
            // (es. non è stato creato o configurato), evitiamo il crash
            if (input == null) {
                System.err.println("Errore critico: Impossibile trovare il file 'config.properties' in src/main/resources!");
                return;
            }

            // Carica in memoria tutte le coppie
            // chiave-valore presenti nel file .properties
            prop.load(input);

            // Estraiamo i valori dal file tramite
            // le rispettive chiavi testuali e li assegniamo alle nostre variabili
            this.apiKey = prop.getProperty("brevo.api.key");
            this.mittenteEmail = prop.getProperty("brevo.mittente");
            this.destinatarioEmail = prop.getProperty("brevo.destinatario");

        } catch (Exception ex) {
            // Intercettiamo qualsiasi anomalia di Input/Output
            // durante la lettura del file
            System.err.println("Errore durante il caricamento del file di configurazione API:");
            ex.printStackTrace();
        }
    }

    @Override
    public void inviaNotifica(String titolo, String messaggio) {
        // Controllo preventivo: se la chiave API è assente,
        // interrompiamo l'esecuzione per evitare chiamate vuote fallimentari
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Errore: Servizio di notifica non attivo. Chiave API mancante o non configurata.");
            return;
        }

        // L API di brevo utilizza il formato JSON e quindi andremo a creare la mail
        // in modo manuale creando da zero il file JSON
        String jsonPayload = "{"
                + "\"sender\":{\"name\":\"Gestione Magazzino\",\"email\":\"" + mittenteEmail + "\"},"
                + "\"to\":[{\"email\":\"" + destinatarioEmail + "\"}],"
                + "\"subject\":\"" + titolo.replace("\"", "\\\"") + "\","
                + "\"textContent\":\"" + messaggio.replace("\"", "\\\"").replace("\n", "\\n") + "\""
                + "}";

        // Costruiamo la richiesta HTTP mediante
        // il comodo pattern Builder nativo di Java
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BREVO_API_URL)) // Impostiamo l'indirizzo web di destinazione
                .header("accept", "application/json") // Comunichiamo a Brevo che ci aspettiamo una risposta in JSON
                .header("api-key", apiKey) // Iniettiamo la nostra chiave segreta nell'Header per l'autenticazione di sicurezza
                .header("content-type", "application/json") // Specifichiamo che i dati in allegato sono in formato JSON
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload)) // Definiamo il metodo HTTP come POST inserendo il payload testuale
                .build();

        // Usiamo una chiamata asincrona siccome senza quest'ultima l'interfaccia
        // si bloccherebbe aspettando l'arrivo dell email
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                // .thenAccept intercetta la risposta del server quando la chiamata web si conclude con successo
                .thenAccept(response -> {
                    // Lo Status Code "201" nel protocollo HTTP significa
                    // "Created" ed è il codice con cui Brevo conferma l'avvenuto invio
                    if (response.statusCode() == 201) {
                        System.out.println("E-mail di notifica inviata con successo via HTTPS (Porta 443)!");
                    } else {
                        // Se lo status code è diverso (es. 401 o 400),
                        // stampiamo nei log l'errore dettagliato restituito da Brevo
                        System.err.println("Errore API Brevo. Status Code: " + response.statusCode());
                        System.err.println("Dettaglio: " + response.body());
                    }
                })
                // .exceptionally intercetta eventuali anomalie distruttive
                // improvvise prima o durante la connessione (es. assenza totale di internet)
                .exceptionally(ex -> {
                    System.err.println("Errore imprevisto durante l'invio della richiesta web:");
                    ex.printStackTrace();
                    return null;
                });
    }

    @Override
    public void inviaNotifiche(String titolo, List<String> messaggi) {
        // Se la lista è vuota o nulla, è inutile procedere con l'invio della mail
        if (messaggi == null || messaggi.isEmpty()) return;

        // Utilizziamo lo StringBuilder per assemblare le stringhe.
        // È una best practice di performance:
        // evita la continua creazione e distruzione di oggetti String
        // all'interno del ciclo loop
        StringBuilder corpoEmail = new StringBuilder("Rilevate criticità nel magazzino:\\n\\n");

        for (String msg : messaggi) {
            corpoEmail.append("- ").append(msg).append("\\n");
        }
        
        inviaNotifica(titolo, corpoEmail.toString());
    }
}