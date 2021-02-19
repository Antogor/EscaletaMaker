package service;

import java.util.List;
import java.util.Map;

import model.Escaleta;
import model.Noticia;

public interface EscaletaService {
	public void grupoNoticias(Noticia n);
	public Map<String, List<Noticia>> getNoticias();
	public boolean guardarEscaleta(Escaleta escaleta, String ruta, String nProgrma);
	public void setNoticias(Map<String, List<Noticia>> noticias);
}
