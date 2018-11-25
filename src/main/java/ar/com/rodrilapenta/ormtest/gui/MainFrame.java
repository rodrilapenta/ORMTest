package ar.com.rodrilapenta.ormtest.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import ar.com.rodrilapenta.ormtest.service.MainService;
import ar.com.rodrilapenta.ormtest.util.Util;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 2292455670620751734L;
	private JPanel contentPane;
	
	public MainFrame(final MainService service, Boolean useHibernate) {
		try {
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}

			setIconImage(Util.getImage("logomini.jpg", this));

		if(useHibernate) {
			setTitle("Testeo de ORM - Hibernate");
		}
		else {
			setTitle("Testeo de ORM - MyBatis");
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnArchivo.add(mntmSalir);

		JMenu abmMenu = new JMenu("Query");
		menuBar.add(abmMenu);

		JMenu cargaMasivaMenu = new JMenu("Carga masiva");
		JMenuItem cargarReplicasMenuItem = new JMenuItem("Cargar");
		cargaMasivaMenu.add(cargarReplicasMenuItem);
		cargarReplicasMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LoaderFrame(service.getDao().getGenericDAO()).setVisible(true);
			}
		});

		menuBar.add(cargaMasivaMenu);

		/*JMenuItem farmaciaReportesMenuItem = new JMenuItem("Farmacia y producto");
		abmMenu.add(farmaciaReportesMenuItem);
			farmaciaReportesMenuItem.setEnabled(true);
		farmaciaReportesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*PanelFiltrosReporteFarmaciaProducto pfrf = new PanelFiltrosReporteFarmaciaProducto(service.getDao());
				Object[] options = { "Aceptar", "Cancelar" };
				pfrf.setPreferredSize(new Dimension(390, 350));
				int opcion = -1;
				while (opcion == -1) {
					if (opcion == 1 || opcion == -1) {
						opcion = JOptionPane.showOptionDialog(null, pfrf, "Reporte de ventas (Farmacia)",
								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
						System.out.println("OPCION ELEGIDA: " + opcion);
						System.out.println("VALIDAR: " + pfrf.validar());
						while (!pfrf.validar() && opcion == 0) {
							if (!pfrf.validar()) {
								JOptionPane.showMessageDialog(null, "Faltan datos", "Faltan datos",
										JOptionPane.ERROR_MESSAGE);
								opcion = JOptionPane.showOptionDialog(null, pfrf, "Reporte de ventas (Farmacia)",
										JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
										options[1]);
							}
						}
						if (opcion == 0) {
							Map<String, Object> datos = new HashMap<String, Object>();
							datos.put("farmacia", pfrf.getFarmacia());
							datos.put("producto", pfrf.getProducto());
							datos.put("fechaDesde", pfrf.getFechaDesde());
							datos.put("fechaHasta", pfrf.getFechaHasta());
							datos.put("trimestre", pfrf.getTrimestre());
							new PDFGenerator(1, service.getDao()).generarReporte(datos);
							JOptionPane.showMessageDialog(null,
									"Reporte de ventas (Farmacia) generado en C:\\Reportes\\Reporte Farmacias.pdf");
						}
					}
				}

			}
		});*/

		JMenuItem queryGenericaMenuItem = new JMenuItem("Genèrica");
		queryGenericaMenuItem.setEnabled(true);
		
		queryGenericaMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.getDao().getGenericDAO().genericQuery();
			}
		});

		abmMenu.add(queryGenericaMenuItem);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		//label.setIcon(new ImageIcon(Util.getImage("/gui/simlogo.jpg", this)));
		contentPane.add(label, BorderLayout.CENTER);
	}
}