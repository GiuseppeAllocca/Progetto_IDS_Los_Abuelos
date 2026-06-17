package setup;

import database.JpaUtil;
import entity.GestoreUtenti;
import entity.GestoreProdotti;

public class MainInizializzaDatabase {

    public static void main(String[] args) {

        // 1. Avvia Hibernate (leggendo il tuo persistence.xml)
        JpaUtil.getInstance();

        // 2. Popola gli utenti ed i prodotti di test
        GestoreUtenti g = new GestoreUtenti();
        DatiTestUtenti.popolaUtenti(g);
        GestoreProdotti r = new GestoreProdotti();
        DatiTestProdotti.popolaProdotti(r);

        // 3. Chiudiamo tutto correttamente
        JpaUtil.getInstance().chiudi();

        System.out.println("Database inizializzato con successo!");
        System.exit(0); // Termina l'applicazione
    }
}