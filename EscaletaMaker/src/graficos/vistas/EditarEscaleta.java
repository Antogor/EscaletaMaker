package graficos.vistas;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import graficos.adaptadores.CategoriaListModel;
import graficos.adaptadores.NoticiasListModel;
import model.Escaleta;
import model.Noticia;
import service.EditarService;
import service.EditarServiceImpl;
import service.EscaletaService;
import service.Utilities;

public class EditarEscaleta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTitulo;
	private JTextField textLink;
	private String categoria;
	private int selection;
	private NoticiasListModel nL;
	private JTextField textPos;
	private EditarService es;
	private List<String> categoriasTotales;


	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked"})
	public EditarEscaleta(Escaleta escaleta, EscaletaService service, String path, String nArchivo) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 593, 562);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setIconImage(new ImageIcon(EscaletaApp.class.getResource("/src/iconos/LOGO.PNG")).getImage());
		this.setTitle("Edición");
		es = new EditarServiceImpl();
		categoriasTotales = new ArrayList<String>();
		categoriasTotales.add("mundoGeek");
		categoriasTotales.add("videojuegos");
		categoriasTotales.add("entretenimiento");
		categoriasTotales.add("comics");
		categoriasTotales.add("juegosMesa");
		categoriasTotales.add("topEngorile");
		
		textTitulo = new JTextField();
		textTitulo.setBounds(363, 32, 204, 20);
		contentPane.add(textTitulo);
		textTitulo.setColumns(10);
		
		textLink = new JTextField();
		textLink.setBounds(363, 93, 204, 20);
		contentPane.add(textLink);
		textLink.setColumns(10);
		
		textPos = new JTextField();
		textPos.setBounds(444, 197, 48, 20);
		contentPane.add(textPos);
		textPos.setColumns(10);

		JList<String> categorias = new JList<>();
		categorias.setBounds(10, 11, 149, 120);
		contentPane.add(categorias);
		//Creamos el listModel con las categorias, en este caso si queremos ver las categorias disponibles
		//categorias.setModel(new CategoriaListModel(categoriasTotales));
		//En este si queremos ver las categorias que existen
		categorias.setModel(new CategoriaListModel(new ArrayList<String>(escaleta.getNoticias().keySet())));
		
		JList<Noticia> noticias = new JList<>();
		noticias.setBounds(10, 158, 328, 354);
		//contentPane.add(noticias);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 158, 328, 355);
		contentPane.add(scrollPane);
		scrollPane.add(noticias);
		/*
		 * Al seleccionar la categoria se imprime la lista de noticias
		 */
		categorias.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				textTitulo.setText(null);
				textLink.setText(null);
				//Recogemos la categoria seleccionada
				categoria = categorias.getSelectedValue();
				//Si la categoria no existe la metemos dentro de la escaleta, vacia para añadirla en revisar
				//if (escaleta.getNoticias().get(categoria) == null)
				//	escaleta.getNoticias().put(categoria, new ArrayList<Noticia>());
				//Recogemos el listModel correspondiente a las noticias y se muestra con setModel
				nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
				noticias.setModel(nL);
			}
		});
		/*
		 * Al seleccionar la noticia se muestra sus campos
		 */
		noticias.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selection = noticias.getSelectedIndex();
				if (selection != -1) {
					Noticia n = nL.getNoticia(selection);
					textTitulo.setText(n.getTitulo());
					textLink.setText(n.getLink());
				}
			}
		});
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Si no hay noticia o categoria seleccionada
				if (selection == -1 || escaleta.getNoticias().get(categoria) == null) {
					JOptionPane.showMessageDialog(null, "No has seleccionado noticia");
				}
				else {
					Noticia n = new Noticia(textTitulo.getText(), textLink.getText().isEmpty()?"#":textLink.getText(), categoria);
					List<Noticia> ns = escaleta.getNoticias().get(categoria);
					ns.set(selection, n);
					escaleta.getNoticias().put(categoria, ns);
					//Actualizamos la listModel con la modificacion
					NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
					noticias.setModel(nL);
				}
			}
		});
		btnModificar.setBounds(363, 136, 105, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Si no hay noticia o categoria seleccionada
				if (selection == -1 || escaleta.getNoticias().get(categoria) == null) {
					JOptionPane.showMessageDialog(null, "No has seleccionado noticia");
				}
				else {
					List<Noticia> ns = escaleta.getNoticias().get(categoria);
					ns.remove(selection);
					escaleta.getNoticias().put(categoria, ns);
					//Actualizamos la listModel con la modificacion
					NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
					noticias.setModel(nL);
				}
			}
		});
		btnEliminar.setBounds(478, 136, 89, 23);
		contentPane.add(btnEliminar);
		
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditarEscaleta.this.dispose();
			}
		});
		btnCerrar.setBounds(460, 469, 89, 23);
		contentPane.add(btnCerrar);
		
		JButton btnAnadir = new JButton("A\u00F1adir");
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos que hya categoria seleccionada
				if (escaleta.getNoticias().get(categoria) == null) {
					JOptionPane.showMessageDialog(null, "No has seleccionado categoria");
				}
				//Comprobamos que la nueva noticia tenga al menos un titulo
				else if (textTitulo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Necesitas al menos un titulo");
				}
				else {
					//Creamos la nueva noticia
					Noticia n = new Noticia(textTitulo.getText(), textLink.getText().isEmpty()?"#":textLink.getText(), categoria);
					//recogemos la lista actual correspondienta a la categoria
					List<Noticia> ns = escaleta.getNoticias().get(categoria);
					//Si no hay posicion se añade al final
					if (textPos.getText().isEmpty()) {					
						ns.add(n);
						//Sobreescribimos la categoria actual con la lista actualizada
						escaleta.getNoticias().put(categoria, ns);
						NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
						noticias.setModel(nL);
					}
					//Si hay poscion, se añade al final y con el metodo cambiar se coloca en el deseado
					else {
						ns.add(n);
						//comprobamos que la posicion este dentro del tamaño de la lista y que sea un numero
						if (!Utilities.isNumeric(textPos.getText()) || 
							Integer.parseInt(textPos.getText()) >=1 && Integer.parseInt(textPos.getText()) <= ns.size()) {
								//Sobreescribimos la categoria actual con la lista actualizada
								escaleta.getNoticias().put(categoria, es.intercambiarNoticia(ns, 
										Integer.parseInt(textPos.getText()), n));
								NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
								noticias.setModel(nL);						
						}
						else {
							JOptionPane.showMessageDialog(null, "Esa posicion no existe");
						}
						/*escaleta.getNoticias().put(categoria, ns);
						NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
						noticias.setModel(nL);*/
					}
				}
			}
		});
		btnAnadir.setBounds(427, 281, 89, 23);
		contentPane.add(btnAnadir);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(444, 12, 48, 14);
		contentPane.add(lblTitulo);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(444, 68, 48, 14);
		contentPane.add(lblLink);	
		
		
		JButton btnCambio = new JButton("Cambiar posicion");
		btnCambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos que la posicion no este vacia, este dentro del tamaño de la lista
				//y sea numerico
				if(!Utilities.isNumeric(textPos.getText()) || textPos.getText().isEmpty() ||
					Integer.parseInt(textPos.getText()) > escaleta.getNoticias().get(categoria).size() ||
					Integer.parseInt(textPos.getText()) <= 0) {
					JOptionPane.showMessageDialog(null, "Esa posicion no existe");
				}
				//Comprobamos que haya sido seleccionada la notcia
				else if (selection == -1) {
					JOptionPane.showMessageDialog(null, "No has seleccionado noticia");
				}
				else {
					//Hacemos el cambio y actualizamos
					List<Noticia> ns = es.intercambiarNoticia(escaleta.getNoticias().get(categoria), 
							Integer.parseInt(textPos.getText()),
							nL.getNoticia(selection));
					escaleta.getNoticias().put(categoria, ns);
					NoticiasListModel nL = new NoticiasListModel(escaleta.getNoticias().get(categoria));
					noticias.setModel(nL);
				}
			}
		});
		btnCambio.setBounds(393, 228, 156, 23);
		contentPane.add(btnCambio);
		
		JLabel lblPos = new JLabel("Posicion");
		lblPos.setBounds(444, 172, 72, 14);
		contentPane.add(lblPos);
		
		JButton btnGuardar = new JButton("Guardar cambios");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.guardarEscaleta(escaleta, path, nArchivo);
				//JOptionPane.showMessageDialog(null, "CAMBIOS GUARDADOS");
			}
		});
		btnGuardar.setBounds(393, 423, 156, 23);
		contentPane.add(btnGuardar);
	}
}
