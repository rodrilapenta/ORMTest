package ar.com.rodrilapenta.ormtest.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.com.rodrilapenta.ormtest.buffer.Buffer;
import ar.com.rodrilapenta.ormtest.buffer.HiloSacar;
import ar.com.rodrilapenta.ormtest.db.dao.impl.GenericDAOImpl;
import ar.com.rodrilapenta.ormtest.util.Util;
import ar.com.rodrilapenta.ormtest.util.WatchDir;


public class LoaderFrame extends JFrame {

	private static final long serialVersionUID = -7678392829726758297L;
	private JPanel contentPane;
	private JTextField textField;
	private Path path;
	private TrayIcon trayIcon;
	private SystemTray tray;
	private Buffer b = new Buffer(30);
	private Thread t;
	private HiloSacar h;
	private MenuItem runItem;
	private MenuItem stopItem;
	private JButton btnArrancar, btnParar, btnExaminar;
	private JCheckBox recursivo;
	private JProgressBar progressBar;
	private final JSeparator separator = new JSeparator();
	private JList<Object> listaEmails;
	private JCheckBox enviarEmail;
	private JButton btnAgregarEmail, btnBorrarEmail;
	private List<Object> emails = new ArrayList<Object>();
	private JScrollPane scrollPaneEmails;

	public LoaderFrame(final GenericDAOImpl dao) {
		setResizable(false);
		setTitle("Carga de r\u00E9plicas - SIM");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setToolTipText("Directorio en el que se estar\u00E1n buscando nuevos archivos .txt");
		textField.setBounds(75, 68, 446, 25);
		contentPane.add(textField);
		textField.setEditable(false);
		textField.setColumns(10);

		btnExaminar = new JButton("Examinar");
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
				if(path != null) {
					textField.setText(path.toString());
				}
			}
		});

		btnExaminar.setBounds(521, 68, 89, 25);
		contentPane.add(btnExaminar);

		recursivo = new JCheckBox("Recursivo");
		recursivo.setToolTipText("Si se activa esta opci\u00F3n, tambi\u00E9n se buscar\u00E1n archivos en los subdirectorios del directorio seleccionado");
		recursivo.setBounds(297, 134, 97, 23);
		contentPane.add(recursivo);

		ActionListener runListener = new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				try {
					t = new Thread(new WatchDir(path, recursivo.isSelected(), b));
					h = new HiloSacar(b, 2000, dao, (List<String>)(List<?>) emails);
					t.start();
					h.start();
					trayIcon.setToolTip("Carga de r\u00E9plicas - SIM - Activado - ("	+ path.toString() + ")");
					stopItem.setEnabled(true);
					runItem.setEnabled(false);
					btnArrancar.setEnabled(false);
					btnParar.setEnabled(true);
					btnExaminar.setEnabled(false);
					recursivo.setEnabled(false);
					progressBar.setVisible(true);
					progressBar.setIndeterminate(true);
					scrollPaneEmails.setEnabled(false);
		        	btnAgregarEmail.setEnabled(false);
		        	btnBorrarEmail.setEnabled(false);
		        	enviarEmail.setEnabled(false);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};

		ActionListener stopListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.interrupt();
				h.interrupt();
				trayIcon.setToolTip("Carga de r\u00E9plicas - SIM - Desactivado");
				stopItem.setEnabled(false);
				runItem.setEnabled(true);
				btnArrancar.setEnabled(true);
				btnParar.setEnabled(false);
				btnExaminar.setEnabled(true);
				recursivo.setEnabled(false);
				progressBar.setVisible(false);
				progressBar.setIndeterminate(false);
				scrollPaneEmails.setEnabled(true);
	        	btnAgregarEmail.setEnabled(true);
	        	btnBorrarEmail.setEnabled(true);
	        	enviarEmail.setEnabled(true);
			}
		};

		btnArrancar = new JButton("Arrancar");
		btnArrancar.addActionListener(runListener);
		btnArrancar.setEnabled(false);
		btnArrancar.setBounds(585, 213, 89, 23);
		contentPane.add(btnArrancar);

		btnParar = new JButton("Parar");
		btnParar.addActionListener(stopListener);
		btnParar.setBounds(486, 213, 89, 23);
		btnParar.setEnabled(false);
		contentPane.add(btnParar);

		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(8, 271, 680, 14);
		progressBar.setVisible(false);
		contentPane.add(progressBar);
		separator.setBounds(0, 263, 694, 2);
		contentPane.add(separator);
		
		enviarEmail = new JCheckBox("Enviar email con fallidos");
		enviarEmail.setBounds(6, 213, 139, 23);
		enviarEmail.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(enviarEmail.isSelected()) {
					scrollPaneEmails.setVisible(true);
		        	btnAgregarEmail.setVisible(true);
		        	btnBorrarEmail.setVisible(true);
		        }
				else {
					scrollPaneEmails.setVisible(false);
		        	btnAgregarEmail.setVisible(false);
		        	btnBorrarEmail.setVisible(false);
		        	emails = new ArrayList<Object>();
		        	
		        	DefaultListModel<?> listModel = (DefaultListModel<?>) listaEmails.getModel();
		        	listModel.removeAllElements();
				}
			}
		});
		contentPane.add(enviarEmail);
		
		listaEmails = new JList<Object>();
		listaEmails.setModel(new DefaultListModel<Object>());
		scrollPaneEmails = new JScrollPane();
		scrollPaneEmails.setBounds(151, 200, 161, 50);
		scrollPaneEmails.setVisible(false);
		scrollPaneEmails.setViewportView(listaEmails);
		contentPane.add(scrollPaneEmails);
		
		btnAgregarEmail = new JButton("+ Agregar");
		btnAgregarEmail.setBounds(322, 200, 89, 23);
		btnAgregarEmail.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnAgregarEmail.setVisible(false);
		contentPane.add(btnAgregarEmail);
		
		btnBorrarEmail = new JButton("\u2014 Borrar");
		btnBorrarEmail.setBounds(322, 227, 89, 23);
		btnBorrarEmail.setVisible(false);
		btnBorrarEmail.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!listaEmails.isSelectionEmpty()) {
					String email = listaEmails.getSelectedValue().toString();
					int pos = existeEmail(email);
					emails.remove(pos);
					listaEmails.setListData(emails.toArray());
				}
			}
		});
		contentPane.add(btnBorrarEmail);

		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();

			Image image = Util.getImage("hibernatelogo.jpg", this);

			PopupMenu popup = new PopupMenu();

			MenuItem defaultItem;

			runItem = new MenuItem("Arrancar");
			runItem.addActionListener(runListener);
			runItem.setEnabled(false);
			popup.add(runItem);

			stopItem = new MenuItem("Parar");
			stopItem.addActionListener(stopListener);
			stopItem.setEnabled(false);
			popup.add(stopItem);

			popup.addSeparator();

			defaultItem = new MenuItem("Abrir");
			defaultItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setExtendedState(JFrame.NORMAL);
				}
			});
			popup.add(defaultItem);

			trayIcon = new TrayIcon(image, "Carga de r\u00E9plicas - SIM - Desactivado", popup);
			trayIcon.setImageAutoSize(true);
		} 
		else {
		}
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == ICONIFIED) {
					try {
						tray.add(trayIcon);
						setVisible(false);
					} 
					catch (AWTException ex) {
					}
				}
				if (e.getNewState() == 7) {
					try {
						tray.add(trayIcon);
						setVisible(false);
					} 
					catch (AWTException ex) {
					}
				}
				if (e.getNewState() == MAXIMIZED_BOTH) {
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
				if (e.getNewState() == NORMAL) {
					tray.remove(trayIcon);
					setVisible(true);
					System.out.println("Tray icon removed");
				}
			}

		});
		setIconImage(Util.getImage("logomini.jpg" , this));

		setVisible(true);
	}

	private void abrirArchivo() {
		// String aux="";
		// String texto="";
		try {
			/** llamamos el metodo que permite cargar la ventana */
			JFileChooser file = new JFileChooser();
			file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			file.setMultiSelectionEnabled(false);
			int option = file.showOpenDialog(this);
			/** abrimos el archivo seleccionado */
			if(option != JFileChooser.CANCEL_OPTION) {
				btnArrancar.setEnabled(true);
				runItem.setEnabled(true);
				path = file.getSelectedFile().toPath();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex + "" + "\nNo se ha encontrado el archivo", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int existeEmail(String email) {
		int pos = -1;
		for(int i = 0; i < emails.size(); i++) {
			if(emails.get(i).toString().equals(email)) {
				pos = i;
			}
		}
		return pos;
	}
}
