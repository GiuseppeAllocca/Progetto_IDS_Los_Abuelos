package entity;

import database.GestorePersistenza;
import java.util.Map;

public class GestoreUtenti {
    private GestorePersistenza gestorePersistenza;

    public GestoreUtenti() {
        this.gestorePersistenza = new GestorePersistenza();
    }

    /**
     * Il seguente metodo si occupa di restituire un oggetto
     * di tipo Utente al controller cercandolo nel database mediante il metodo
     * fornito dal GestorePersistenza usando l'username dell'utente come
     * elemento di ricerca (id)
     */

    public Utente cercaUtentePerUsername(String username) {
        return gestorePersistenza.trovaPerId(Utente.class, username);
    }

    /**
     * Il seguente metodo restituisce true se le credenziali fornite sono
     * corrette , false in caso ci siano errori
     */

    public boolean verificaCredenziali(Utente utente, String password) {
        return (utente.getPassword().equals(password));
    }

    /**
     * Il seguente metodo si occupa di verificare che il ruolo selezionato in fase di login
     * sia coerente con il ruolo effettivo dell'utente per cui si sta cercando di effettuare
     * il login , ritorna true in caso affermativo false in caso ci sia un errore
     */

    public boolean verificaRuolo(Utente utente, boolean isResp){
        return ((isResp && utente.getTipo() == TipoOperatore.RESPONSABILE) ||
                (!isResp && utente.getTipo() == TipoOperatore.OPERATORE));
    }

    /**
     * Istanzia un nuovo oggetto Utnete con i dati forniti e lo persiste
     * tramite GestorePersistenza.
     *
     * @return true se il salvataggio JPA è andato a buon fine, false altrimenti
     */

    public boolean registraUtente(String username, String nome, String cognome,
                                  String email, String password, TipoOperatore tipo) {

        if (cercaUtentePerUsername(username) != null) {
            return false;
        }

        Utente nuovoUtente = new Utente(username, nome, cognome, email, password, tipo);
        return gestorePersistenza.salva(nuovoUtente);
    }

}