package service;

import java.util.List;

import model.Escaleta;
import model.Noticia;

public interface EditarService {

	public List<Noticia> intercambiarNoticia(List<Noticia> ns, int index, Noticia noticia);
	
	public Escaleta abrirEscaleta(String path);
}
