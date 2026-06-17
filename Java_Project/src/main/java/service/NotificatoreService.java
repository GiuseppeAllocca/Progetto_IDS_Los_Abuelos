package service;

import java.util.List;
// Uso un interfaccia cosi da poter supportare più implementazioni per il servizio notifiche
// cosi se in futuro si vorrà cambiare si può fare molto facilmente
public interface NotificatoreService {

    //Invia una singola notifica push sul dispositivo.
    void inviaNotifica(String titolo, String messaggio);

    //Invia una lista di notifiche.
    void inviaNotifiche(String titolo, List<String> messaggi);

}
