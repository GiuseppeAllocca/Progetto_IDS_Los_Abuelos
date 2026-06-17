package entity;

import jakarta.persistence.*;

@Entity
public class Utente {

	/*
    Ciascun utente sarà identificato da
    un username(Stringa) univoco scelto in fase di registrazione
    */

	@Id
	private String username;

	private String nome;
	private String cognome;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private TipoOperatore tipo;

	public Utente(){
		// Costruttore vuoto richiesto da Hibernate
	}

	public Utente(String username,String nome, String cognome, String email, String password, TipoOperatore tipo) {
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.tipo = tipo;
	}

	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public String getCognome() {return cognome;}
	public void setCognome(String cognome) {this.cognome = cognome;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public TipoOperatore getTipo() { return tipo; }
	public void setTipo(TipoOperatore tipo) { this.tipo = tipo; }

	@Override
	public String toString(){
		return "Utente{" + "username=" + username;
	}

}
