package graficos.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import graficos.adaptadores.FondoSwing;
import model.Noticia;
import service.EscaletaService;

public class AnadirNoticia extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private String categoria;
	private EscaletaService es;
	private Noticia n;
	private JTextField titulo;
	private JTextField link;
	private JButton btnCancelar;

	/**
	 * Create the frame.
	 */
	public AnadirNoticia(String categoria, EscaletaService escaletaService, ImageIcon icono) {
		this.categoria = categoria;
		es = escaletaService;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(600, 100, 437, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//setImagenFondo();
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(EscaletaApp.class.getResource("/src/iconos/LOGO.PNG")).getImage());
		this.setTitle(categoria);
		
		titulo = new JTextField();
		
		titulo.setBounds(77, 113, 251, 28);
		contentPane.add(titulo);
		titulo.setColumns(10);
		
		link = new JTextField();
		link.setBounds(77, 178, 251, 28);
		contentPane.add(link);
		link.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(109, 232, 89, 23);
		contentPane.add(btnGuardar);
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Comprobamos que al menos haya un titulo para la noticia
				if(titulo.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Necesitas rellenar al menos el titulo");
				else {
					//creamos la notica y la guardamos en su lista correspondiente y la asociamos a su codigo de categoria en el map
					//indicamos si el link esta vacio o no para generar un html u otro
					n = new Noticia(titulo.getText(), link.getText().isEmpty() ? "#": link.getText(), categoria);
					es.grupoNoticias(n);
					AnadirNoticia.this.dispose();
				}
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AnadirNoticia.this.dispose();
			}
		});
		btnCancelar.setBounds(215, 232, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(192, 78, 48, 14);
		contentPane.add(lblTitulo);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(192, 152, 48, 14);
		contentPane.add(lblLink);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(-76, -24, 370, 146);		
		contentPane.add(lblLogo);
		lblLogo.setIcon(icono);
		
		JLabel lblCategoria = new JLabel(categoria.toUpperCase());
		lblCategoria.setBounds(116, 41, 147, 14);
		contentPane.add(lblCategoria);
	}
	
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
