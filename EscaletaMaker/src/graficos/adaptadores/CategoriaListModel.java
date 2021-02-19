package graficos.adaptadores;

import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("rawtypes")
public class CategoriaListModel extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	private List<String> categorias;

	public CategoriaListModel(List<String> categorias) {
		super();
		this.categorias = categorias;
	}

	@Override
	public int getSize() {
		return categorias.size();
	}

	@Override
	public Object getElementAt(int index) {
		return categorias.get(index);
	}

}
