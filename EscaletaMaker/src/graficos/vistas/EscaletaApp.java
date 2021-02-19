package graficos.vistas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import graficos.adaptadores.FondoSwing;
import model.Escaleta;
import model.Noticia;
import service.EditarService;
import service.EditarServiceImpl;
import service.EscaletaService;
import service.EscaletaServiceImpl;

public class EscaletaApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField path;
	private EscaletaService es;
	private JTextField textnombrePrograma;
	private Escaleta escaleta;
	//Creamos el objeto Logger con el nombre de la clase y paquete en el que esta y lo guardamos en una variable constante
	//con getLogger y el getName del .class recogemos el nombre del paquete y la clase para que genere el Logger en esta clase
	private final static Logger LOG = Logger.getLogger(EscaletaApp.class.getName());
	
	//Rutas de las imagenes, con getResource recogemos el path del paquete donde estan guardadas
	private ImageIcon logo = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/LOGO.PNG"));
	private ImageIcon IMundoGeek = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/mando.png"));
	private ImageIcon IVideojuegos = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/mando.png"));
	private ImageIcon IEntretenimiento = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/claqueta.png"));
	private ImageIcon IComics = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/superman.png"));
	private ImageIcon IJuegosMesa = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/superman.png"));
	private ImageIcon ITop = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/superman.png"));
	private ImageIcon fondoApp = new ImageIcon(EscaletaApp.class.getResource("/src/iconos/Background_pequeno.jpg"));
	private JTextField textnArchivo;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EscaletaApp frame = new EscaletaApp();
					frame.setVisible(true);	
				} catch (Exception e) {
					//Creamos un archivo txt para guardar el mensaje de error
					FileHandler fileTxt;
					try {
						fileTxt = new FileHandler("LogError.txt");
						SimpleFormatter formatterTxt = new SimpleFormatter();
						//le aplicamos un formato simple para convertirlo a texto plano
						fileTxt.setFormatter(formatterTxt);
						//Llamamos al metodo addHandler para pasarle la variable con el formato de la salida
						LOG.addHandler(fileTxt);
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//Creamos un stringWriter y printWriter para pasarselo al Stacktrace para que guarde el mensaje
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					//transformamos la traza a string
					String sStackTrace = sw.toString();
					//la mandamos al archivo txt
					LOG.log(Level.SEVERE, sStackTrace);
				}
			}
		});
	}

	public EscaletaApp(){
		es = new EscaletaServiceImpl();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 705, 485);	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//setImagenFondo();
		this.setLocationRelativeTo(null);
		this.setTitle("EscaletaMaker v3.1.0");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(EscaletaApp.class.getResource("/src/iconos/LOGO.PNG")));
		
		
		
		/*
		 * Textos
		 */
		path = new JTextField();
		path.setBounds(8, 181, 194, 29);
		contentPane.add(path);
		path.setColumns(10);
		
		textnombrePrograma = new JTextField();
		textnombrePrograma.setBounds(8, 116, 272, 29);
		contentPane.add(textnombrePrograma);
		textnombrePrograma.setColumns(10);
		
		textnArchivo = new JTextField();
		textnArchivo.setBounds(8, 42, 270, 29);
		contentPane.add(textnArchivo);
		textnArchivo.setColumns(10);

		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		final JFileChooser abrirArchivo = new JFileChooser();
		abrirArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		/*
		 * Labels
		 */
		JLabel lblNombreArchivo = new JLabel("Nombre  del archivo");
		lblNombreArchivo.setBounds(20, 17, 260, 14);
		contentPane.add(lblNombreArchivo);
		
		JLabel lblNombre = new JLabel("Titulo del programa");
		lblNombre.setBounds(20, 82, 192, 23);
		//lblNombre.setFont(getFont().deriveFont(16f));
		contentPane.add(lblNombre);
		
		JLabel lblRuta = new JLabel("Ruta del html");
		lblRuta.setBounds(20, 156, 289, 14);
		//lblRuta.setFont(getFont().deriveFont(16f));
		contentPane.add(lblRuta);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(0, 179, 317, 221);
		
		contentPane.add(lblLogo);
		lblLogo.setIcon(logo);
			
		/*
		 * Boton para seleccionar la ruta donde crear el archivo
		 * se encargará de crear el objeto Escaleta a partir de los datos enviados
		 */
		JButton buscarPath = new JButton("Buscar");
		//buscarPath.setFont(getFont().deriveFont(18f));
		buscarPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					path.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		buscarPath.setBounds(220, 184, 89, 23);
		contentPane.add(buscarPath);
		
		/*
		 * Botones de categorias
		 * cada una de ellas abre el mismo menu 
		 * que recibe un EscaletaService para guardar 
		 * un objeto Noticia en la lista correspondiente a su categoria
		 */
		
		JButton btnMundoGeek = new JButton("Mundo Geek");
		//btnMundoGeek.setFont(getFont().deriveFont(18f));
		btnMundoGeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("mundoGeek", es, IMundoGeek);
				noticia.setVisible(true);
			}
		});
		btnMundoGeek.setBounds(335, 40, 150, 72);
		contentPane.add(btnMundoGeek);
		
		JButton btnVideojuegos = new JButton("Videojuegos");
		btnVideojuegos.setBorder(null);
		btnVideojuegos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("videojuegos", es, IVideojuegos);
				noticia.setVisible(true);
			}
		});
		btnVideojuegos.setBounds(503, 40, 150, 72);
		contentPane.add(btnVideojuegos);
		btnVideojuegos.setIcon(IVideojuegos);
		btnVideojuegos.setContentAreaFilled(false);
		btnVideojuegos.setOpaque(false);
		//Evento para resaltar el boton al pasar encima con el raton
		btnVideojuegos.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseEntered(MouseEvent arg0) {
				btnVideojuegos.setOpaque(true);
				btnVideojuegos.setBackground(new Color(213,48,48));
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnVideojuegos.setOpaque(false);
            }
		});
		
		JButton btnEntretenimiento = new JButton("Entretenimiento");
		btnEntretenimiento.setBorder(null);
		btnEntretenimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("entretenimiento", es, IEntretenimiento);
				noticia.setVisible(true);
			}
		});
		btnEntretenimiento.setBounds(335, 128, 150, 71);
		contentPane.add(btnEntretenimiento);
		btnEntretenimiento.setIcon(IEntretenimiento);
		btnEntretenimiento.setContentAreaFilled(false);
		btnEntretenimiento.setOpaque(false);
		//Evento para resaltar el boton al pasar encima con el raton
		btnEntretenimiento.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseEntered(MouseEvent arg0) {
				btnEntretenimiento.setOpaque(true);
				btnEntretenimiento.setBackground(new Color(213,48,48));
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnEntretenimiento.setOpaque(false);
            }
		});
		
		JButton btnComics = new JButton("Comics");
		btnComics.setBorder(null);
		btnComics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("comics", es, IComics);
				noticia.setVisible(true);
			}
		});
		btnComics.setBounds(503, 128, 150, 71);
		contentPane.add(btnComics);
		btnComics.setIcon(IComics);
		btnComics.setContentAreaFilled(false);
		btnComics.setOpaque(false);
		//Evento para resaltar el boton al pasar encima con el raton
		btnComics.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseEntered(MouseEvent arg0) {
				btnComics.setOpaque(true);
				btnComics.setBackground(new Color(213,48,48));
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	btnComics.setOpaque(false);
            }
		});
		
		JButton btnJuegos = new JButton("Juegos Mesa");
		//btnJuegos.setFont(getFont().deriveFont(16f));
		btnJuegos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("juegosMesa", es, IJuegosMesa);
				noticia.setVisible(true);
			}
		});
		btnJuegos.setBounds(335, 210, 150, 71);
		contentPane.add(btnJuegos);
		
		JButton btnTopEngorile = new JButton("Top Engorile");
		//btnTopEngorile.setFont(getFont().deriveFont(18f));
		btnTopEngorile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia noticia = new AnadirNoticia("topEngorile", es, ITop);
				noticia.setVisible(true);
			}
		});
		btnTopEngorile.setBounds(503, 210, 150, 71);
		contentPane.add(btnTopEngorile);

		/*
		 * Botones para crear o revisar la escaleta
		 */
		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(371, 306, 78, 47);
		contentPane.add(btnCrear);
		//btnGuardar.setFont(getFont().deriveFont(18f));
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (path.getText().isEmpty() || textnArchivo.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Necesitas rellenar lo de arriba");
				else {
					//Comprobamos si la escaleta ya ha sido creada
					if(escaleta == null) {
						//Si no hay escaleta comprobamos si existen noticias para crear la escaleta sin ellas y poder meterlas luego
						//Si hay noticias, se crea la escaleta completa
						escaleta = es.getNoticias().isEmpty() ? 
								new Escaleta(textnombrePrograma.getText(), new HashMap<String, List<Noticia>>()) :
								new Escaleta(textnombrePrograma.getText(), es.getNoticias());
						es.guardarEscaleta(escaleta, path.getText(), textnArchivo.getText());
						JOptionPane.showMessageDialog(null, "ESCALETA CREADA");
					}
					else {
						//Si la escaleta ya existe porque se ha creado antes en el Boton Revisar, 
						//se actualiza con el grupo de noticias creado y se guarda
						escaleta.setNoticias(es.getNoticias());
						es.guardarEscaleta(escaleta, path.getText(), textnArchivo.getText());
						JOptionPane.showMessageDialog(null, "ESCALETA CREADA");
					}
				}
				
			}
		});
		
		/*
		 * Boton que crea el objeto EditarService y abre la ventana de edicion
		 */
		JButton btnRevisar = new JButton("Revisar");
		btnRevisar.setBounds(511, 305, 105, 49);
		contentPane.add(btnRevisar);
		btnRevisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (path.getText().isEmpty() || textnArchivo.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Necesitas rellenar lo de arriba");
				else {
					//Comprobamos si la escaleta ya ha sido creada
					if(escaleta == null) {
						//Si no hay escaleta comprobamos si existen noticias para crear la escaleta sin ellas y poder meterlas luego
						//Si hay noticias, se crea la escaleta completa
						escaleta = es.getNoticias().isEmpty() ? 
								new Escaleta(textnombrePrograma.getText(), new HashMap<String, List<Noticia>>()) :
								new Escaleta(textnombrePrograma.getText(), es.getNoticias());
						//Nos vamos a la pestaña editar
						EditarEscaleta edicion = new EditarEscaleta(escaleta, es, path.getText(), textnArchivo.getText());
						edicion.setVisible(true);
					}
					else {
						//Si la escaleta ya existe porque se ha creado antes en el Boton Crear,
						//se actualiza con el grupo de noticias creado y vamos a revisar
						escaleta.setNoticias(es.getNoticias());
						EditarEscaleta edicion = new EditarEscaleta(escaleta, es, path.getText(), textnArchivo.getText());
						edicion.setVisible(true);
					}
				}
			}
		});
		
			
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(564, 379, 89, 23);
		contentPane.add(btnSalir);
		
		JLabel fondo = new JLabel("");
		fondo.setIcon(fondoApp);
		fondo.setBounds(0, 0, 689, 425);
		contentPane.add(fondo);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EscaletaApp.this.dispose();
			}
		});
		
		/*
		 * Barra de menu
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmNuevo = new JMenuItem("Nuevo");
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(EscaletaApp.this, "¿Crear nueva escaleta?", "", JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					path.setText(null);
					textnombrePrograma.setText(null);
					textnArchivo.setText(null);
					es = new EscaletaServiceImpl();
					escaleta = new Escaleta();
				}
			}
		});
		mnArchivo.add(mntmNuevo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos si hay informacion ya creada y preguntamos si estamos seguro de abrir uno nuevo
				//ya que perderiamos todo lo no guardado
				if(escaleta != null || !es.getNoticias().isEmpty() || 
						!path.getText().isEmpty() || !textnArchivo.getText().isEmpty()) {
					int res = JOptionPane.showConfirmDialog(EscaletaApp.this, "¿Seguro que quieres abrir archivo?", "Se perdera la informacion no guardada", JOptionPane.YES_NO_OPTION);
					//Si elegimos si, se da la opcion de abrir el archivo
					if(res == JOptionPane.YES_OPTION) {
						EditarService editar = new EditarServiceImpl();
						if (abrirArchivo.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							//Recogemos solo el nombre del archivo
							String nombre = abrirArchivo.getSelectedFile().getName();
							//Recogemos solo la ruta, sin el nombre del archivo
							String ruta = abrirArchivo.getSelectedFile().getParent();
							//Mandamos la combinacion de ambos al metodo que va abrir el archivo
							escaleta = editar.abrirEscaleta(ruta+"/"+nombre);
							if (escaleta.getNoticias() == null)
								JOptionPane.showConfirmDialog(EscaletaApp.this, "Archivo no valido", "", JOptionPane.YES_NO_OPTION);
							else {
								//Colocamos en la textBox la ruta para la sobreescritura
								path.setText(ruta);
								//En la textBox del nombre colocamos el nombre sin .html para poder sobrescribirlo
								textnArchivo.setText(nombre.substring(0, nombre.length()-5));
								textnombrePrograma.setText(escaleta.getTituloPrograma());
								//Reiniciamos la EscaletaService con los elementos que hemos abierto
								es = new EscaletaServiceImpl();
								es.setNoticias(escaleta.getNoticias());
							}
							
						}
					}
				}
				//Si no tenemos nada se abre el archivo sin problemas, aqui se hace los mismo que mas arriba
				else {
					EditarService editar = new EditarServiceImpl();
					if (abrirArchivo.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						String nombre = abrirArchivo.getSelectedFile().getName();
						String ruta = abrirArchivo.getSelectedFile().getParent();
						escaleta = editar.abrirEscaleta(ruta+"/"+nombre);
						if (escaleta.getNoticias() == null)
							JOptionPane.showMessageDialog(null, "Este archivo no es una escaleta");
						else {
							//Colocamos en la textBox la ruta para la sobreescritura
							path.setText(ruta);
							//En la textBox del nombre colocamos el nombre sin .html para poder sobrescribirlo
							textnArchivo.setText(nombre.substring(0, nombre.length()-5));
							textnombrePrograma.setText(escaleta.getTituloPrograma());
							//Reiniciamos la EscaletaService con los elementos que hemos abierto
							es = new EscaletaServiceImpl();
							es.setNoticias(escaleta.getNoticias());
						}
					}
				}
			}
		});
		mnArchivo.add(mntmAbrir);
	}
	
	/*@Override
	public Font getFont() {
	       try {
	           File font_file = new File(EscaletaApp.class.getResource("/fuentes/ComickBook_Simple.ttf").getPath()); 
	           Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
	           return font;
	       } catch (FontFormatException | IOException ex) {
	    	   JOptionPane.showMessageDialog(this, ex.getMessage(), "Tipografia incorrecta", JOptionPane.ERROR_MESSAGE);
	           return null;
	       }
	}*/
	
	/*
	 * Metodo para poner una imagen como fondo de la app
	 * necesita de una clase externa (FondoSwing) 
	 * donde se configura la imagen
	 */
	@SuppressWarnings("unused")
	private void setImagenFondo() {
		try {
            FondoSwing fondo = new FondoSwing(ImageIO.read(new File(EscaletaApp.class.getResource("/src/iconos/Background.PNG").getPath())));
            JPanel panel = (JPanel) this.getContentPane();
            panel.setBorder(fondo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Imagen Background", JOptionPane.ERROR_MESSAGE);
        }
	}
}
