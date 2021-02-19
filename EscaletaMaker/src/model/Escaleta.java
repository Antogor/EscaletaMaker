package model;

import java.util.List;
import java.util.Map;

public class Escaleta {
	private String tituloPrograma;
	private Map<String, List<Noticia>> noticias;
	
	public Escaleta() {
		super();
	}
	
	public Escaleta(String tituloPrograma, Map<String, List<Noticia>> noticias) {
		super();
		this.tituloPrograma = tituloPrograma;
		this.noticias = noticias;
	}
	public String getTituloPrograma() {
		return tituloPrograma;
	}
	public void setTituloPrograma(String tituloPrograma) {
		this.tituloPrograma = tituloPrograma;
	}
	public Map<String, List<Noticia>> getNoticias() {
		return noticias;
	}
	public void setNoticias(Map<String, List<Noticia>> noticias) {
		this.noticias = noticias;
	}
	
	
}
