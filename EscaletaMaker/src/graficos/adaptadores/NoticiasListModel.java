package graficos.adaptadores;

import java.util.List;

import javax.swing.AbstractListModel;

import model.Noticia;

@SuppressWarnings("rawtypes")
public class NoticiasListModel extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	private List<Noticia> noticias;

	public NoticiasListModel(List<Noticia> noticias) {
		super();
		this.noticias = noticias;
	}

	@Override
	public int getSize() {
		return noticias.size();
	}

	@Override
	public Object getElementAt(int index) {
		return (index+1)+": "+noticias.get(index).getTitulo();
	}
	
	public void addNoticia(Noticia n) {
		noticias.add(n);
		this.fireIntervalAdded(this, getSize(), getSize()+1);
	}
	
	public void eliminarNoticia(int index) {
		noticias.remove(index);
		this.fireIntervalRemoved(index, getSize(), getSize()+1);
	}
	
	public Noticia getNoticia (int index) {
		return noticias.get(index);
	}

}
