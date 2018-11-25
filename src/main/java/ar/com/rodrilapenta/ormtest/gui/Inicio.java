package ar.com.rodrilapenta.ormtest.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import ar.com.rodrilapenta.ormtest.service.MainService;
import ar.com.rodrilapenta.ormtest.util.Util;

public class Inicio extends JFrame {
	private static final long serialVersionUID = -8098471520984281922L;
	private MainService service;
	//private File config;

	/**
	 * M�todo que arranca la aplicaci�n.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio inicio = new Inicio();
					inicio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor de la clase. Crea el JFrame inicial para loguearse
	 */
	public Inicio() {
		// Se inicializa la visibilidad de la contrase�a en 'invisible'

		// Se pone el logo a la aplicaci�n
		setIconImage(Util.getImage("logomini.jpg", this));

		// Se elige el look and feel de la aplicaci�n
		lookAndFeel("Windows");

		// Configuraci�n e inicializaci�n del frame de inicio
		setResizable(false);
		setTitle("Testeo de ORM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 353);

		// Creaci�n del panel principal
		JPanel panelInicio = new JPanel();
		panelInicio.setLayout(null);
		setContentPane(panelInicio);

		// Opcional para utilizar con MySQL
		final JCheckBox chckbxConexionLocal = new JCheckBox("Conexi\u00F3n local");
		chckbxConexionLocal.setHorizontalAlignment(SwingConstants.RIGHT);
		chckbxConexionLocal.setBounds(336, 285, 130, 23);
		chckbxConexionLocal.setVisible(false);
		panelInicio.add(chckbxConexionLocal);

		JButton buttonHibernate = new JButton("Hibernate");
		buttonHibernate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login(true);
			}
		});
		buttonHibernate.setBounds(106, 131, 89, 23);
		panelInicio.add(buttonHibernate);

		/*final JButton btnCargarArchivoDe = new JButton("Cargar archivo de configuraci\u00F3n");
		btnCargarArchivoDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(btnCargarArchivoDe);

				if (result == JFileChooser.APPROVE_OPTION) {
					config = fileChooser.getSelectedFile();
				}
			}
		});*/
		
		JButton btnMyBatis = new JButton("MyBatis");
		btnMyBatis.setBounds(287, 131, 89, 23);
		panelInicio.add(btnMyBatis);
		btnMyBatis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login(false);
			}
		});
		
		//btnCargarArchivoDe.setBounds(10, 291, 185, 23);
		
		//panelInicio.add(btnCargarArchivoDe);
	}

	private void lookAndFeel(String laf) {
		try {
			if (laf.equals("Windows"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else if (laf.equals("WindowsClassic"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			else if (laf.equals("Nimbus"))
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			else if (laf.equals("Metal"))
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			else if (laf.equals("CDE"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}

		catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private void login(Boolean useHibernate) {
		dispose();
		service = new MainService(useHibernate);
		new MainFrame(service, useHibernate).setVisible(true);
	}
}