package entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Prodotto {

    /*
    Ciascun prodotto sarà identificato da
    un codice numerico univoco auto-generato
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private int quantita;
    private int soglia;
    private boolean sottoScorta;

    @Enumerated(EnumType.STRING)
    private Collocazione posizione;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    /*
    Tra Prodotto e MovimentoProdotto sussite un'associazione 1 a Molti.
    Tramite quest'annotazione stiamo implementando l'associazione 1 a Molti, lato 1
    */
    @OneToMany(mappedBy = "prodotto")
    private List<MovimentoProdotto> operazioni = new ArrayList<>();

    public Prodotto() {
	    // Costruttore vuoto richiesto da Hibernate
    }

    public Prodotto(String nome, String descrizione, int quantita, int soglia,
	Categoria categoria, Collocazione posizione) {
	this.nome = nome;
	this.descrizione = descrizione;
	this.quantita = quantita;
	this.soglia = soglia;
	if (quantita <= soglia) {
	    this.sottoScorta = true;
	}
	this.categoria = categoria;
	this.posizione = posizione;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getDescrizione() {
	return descrizione;
    }

    public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
    }

    public int getQuantita() {
	return quantita;
    }

    public int getSoglia() {
        return soglia;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /**
    Ogni qualvolta viene aggiornata la soglia relativa
    ad un prodotto, avviene un controllo per assicurarsi
    che lo stato (sotto scorta o meno) del prodotto stesso
    sia consistente dopo l'aggiornamento/modifica
    **/
    public void setSoglia(int soglia) {
        this.soglia = soglia;
        if (this.quantita > this.soglia) {
            this.sottoScorta = false;
        }
        else{
            this.sottoScorta = true;
        }
    }

    public boolean getSottoScorta() {
	return this.sottoScorta;
    }

    public void setSottoScorta(boolean esito) {
	this.sottoScorta = esito;
    }

    public Categoria getCategoria() {
	return categoria;
    }

    public void setCategoria(Categoria categoria) {
	this.categoria = categoria;
    }

    public Collocazione getPosizione() {
	return posizione;
    }

    public void setPosizione(Collocazione posizione) {
	this.posizione = posizione;
    }

    public List<MovimentoProdotto> getOperazioni() {
	return operazioni;
    }

    public void setOperazioni(List<MovimentoProdotto> operazioni) {
	this.operazioni = operazioni;
    }

    @Override
    public String toString() {
	return id + "-" + nome;
    }

}
