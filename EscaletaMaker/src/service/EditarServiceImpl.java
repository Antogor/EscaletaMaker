package service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Escaleta;
import model.Noticia;

public class EditarServiceImpl implements EditarService {

	@Override
	public List<Noticia> intercambiarNoticia(List<Noticia> ns, int index, Noticia noticia) {
		List<Noticia> nLista = ns;
		nLista.remove(nLista.indexOf(noticia));
		nLista.add(index-1, noticia);
		return nLista;
	}

	@Override
	public Escaleta abrirEscaleta(String path) {
		try {
			if(!path.contains(".html"))
				return new Escaleta();
			String raw = Files.lines(Paths.get(path), Charset.forName("UTF-8"))
						.filter(l->l.contains("var escaleta"))
						.findFirst().orElse("");
			String js = raw.substring(44, raw.length()-3);
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(js, Escaleta.class);
		} catch (IOException | java.lang.StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			return new Escaleta();
		}
	}
}
