package model;

public class Noticia {

	private String titulo;
	private String link;
	private String categoria;
	
	public Noticia(String titulo, String link, String categoria) {
		super();
		this.titulo = titulo;
		this.link = link;
		this.categoria = categoria;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
