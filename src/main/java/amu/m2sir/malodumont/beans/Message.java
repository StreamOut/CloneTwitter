package amu.m2sir.malodumont.beans;

public class Message {
	
	private Long id;
	private String contenu;
	private String date;
	private String auteur;
	
	public Message() {
		
	}
	
	public Message (String contenu, String date, String auteur){
		this.contenu = contenu;
		this.date = date;
		this.auteur = auteur;
	}
	
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	
}
