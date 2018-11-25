package ar.com.rodrilapenta.ormtest.reports;


import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.swing.JOptionPane;

import org.hibernate.jdbc.Work;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ar.com.rodrilapenta.ormtest.db.dao.impl.MainDAO;
import ar.com.rodrilapenta.ormtest.gui.MainFrame;
import ar.com.rodrilapenta.ormtest.util.Util;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class PDFGenerator {
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private MainDAO dao;
	private Map<String, Object> params;
	private static int idReporte;
	private static PdfPTable table;

	@SuppressWarnings("static-access")
	public PDFGenerator(int idReporte, MainDAO dao) {
		this.dao = dao;
		this.idReporte = idReporte;
	}

	public void reporteJasper() {
		dao.sessionFactory.openSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				try {
					JasperReport report = JasperCompileManager
							.compileReport("E:\\OneDrive\\Copy\\Dropbox\\EclipseProjects\\SIM\\src\\reportes\\VentasFarmacia.jrxml");
					JasperPrint print = JasperFillManager.fillReport(report,
							null, connection);
					// Exporta el informe a PDF
					JasperExportManager.exportReportToPdfFile(print,
							"C:\\Reportes\\Ventas por farmacia.pdf");
					// Para visualizar el pdf directamente desde java
					JasperViewer.viewReport(print, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void reporteDinamicoFarmacia() {
		final String QUERY_FINAL = ReportQueries.armarReporteFarmacia(params);
		
		dao.sessionFactory.openSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				JasperReportBuilder report = DynamicReports.report();
				report.addPageHeader(Components.image(Util.getImage("/gui/logomini.jpg", this)));
				report.addPageFooter(Components.currentDate());
				report.columns(
						Columns.column("Farmacia", "NombreFarmacia",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Producto", "DescripcionProductos",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Fecha", "InformeFarmaciaFecha",
								DataTypes.dateType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Trimestre", "InformesFarmaciasTrimestre", DataTypes.integerType()).setHorizontalAlignment(
								HorizontalAlignment.CENTER),
						Columns.column("Cantidad vendida", "CantidadVendida", DataTypes.integerType()).setHorizontalAlignment(
								HorizontalAlignment.CENTER))
						.title(// title of the report
						Components.text("Reporte de ventas por farmacia y producto - SIM")
								.setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.pageFooter(Components.pageXofY())// show page number on
															// the page footer
						.setDataSource(
								QUERY_FINAL,
								connection);

				try {
					// show the report
					report.show(false);
					

					// export the report to a pdf file
					//report.toPdf(new FileOutputStream("D:/report.pdf"));
				}
				catch (DRException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void reporteDinamicoFarmaciaOS() {
		final String QUERY_FINAL = ReportQueries.armarReporteFarmaciaObraSocial(params);
		
		dao.sessionFactory.openSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				JasperReportBuilder report = DynamicReports.report();
				report.addPageHeader(Components.image(Util.getImage("/gui/logomini.jpg", this)));
				report.addPageFooter(Components.currentDate());
				report.columns(
						Columns.column("Farmacia", "NombreFarmacia",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Producto", "DescripcionProductos",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Obra social", "DescripcionObrasSociales",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Plan", "DescripcionPlan",
								DataTypes.stringType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Fecha", "InformesVentasFarmaciasOSFecha",
								DataTypes.dateType()).setHorizontalAlignment(
										HorizontalAlignment.CENTER),
						Columns.column("Trimestre", "InformesVentasFarmaciasOSTrimestre", DataTypes.integerType()).setHorizontalAlignment(
								HorizontalAlignment.CENTER),
						Columns.column("Cantidad vendida", "InformesVentasFarmaciasOSCantVendida", DataTypes.integerType()).setHorizontalAlignment(
								HorizontalAlignment.CENTER))
						.title(// title of the report
						Components.text("Reporte de ventas por farmacia, producto y obra social - SIM")
								.setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.pageFooter(Components.pageXofY())// show page number on
															// the page footer
						.setDataSource(
								QUERY_FINAL,
								connection);

				try {
					// show the report
					report.show(false);
					

					// export the report to a pdf file
					//report.toPdf(new FileOutputStream("D:/report.pdf"));
				}
				catch (DRException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void reporteDinamico(Integer reporte) {
		
		switch(reporte) {
			case 1:
				reporteDinamicoFarmacia();
			
			case 2:
				reporteDinamicoFarmaciaOS();
		}
	}

	public void generarReporte(Map<String, Object> params) {
		this.params = params;

		try {
				// generarReporteFarmacia();
				reporteDinamico(idReporte);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Reporte vac�o", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void generarReporteFarmacia() throws Exception {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(
				"C:\\Reportes\\Reporte Farmacias.pdf"));
		document.open();
		addMetaData(document);
		addTitlePage(document);
		addContent(document);
		document.close();

		java.awt.Desktop.getDesktop().open(
				new java.io.File("C:\\Reportes\\Reporte Farmacias.pdf"));
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		if (idReporte == 1) {
			document.addTitle("Reporte de ventas Farmacia");
		} else if (idReporte == 2) {
			document.addTitle("Reporte de ventas Laboratorio");
		} else if (idReporte == 3) {
			document.addTitle("Reporte de ventas Visitador M�dico");
		}

		document.addSubject("Reporte de ventas Farmacia");
		document.addAuthor("Rodrigo Lapenta");
		document.addCreator("SIM");

	}

	@SuppressWarnings("deprecation")
	private void addTitlePage(Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		Paragraph p = new Paragraph("Reporte de ventas", catFont);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		preface.add(p);
		Image i = Image.getInstance(MainFrame.class
				.getResource("/gui/logomini.jpg"));
		i.setAlignment(Image.ALIGN_CENTER);
		preface.add(i);
		addEmptyLine(preface, 1);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Reporte generado por: ");
		stringBuilder.append("Rodrigo");
		stringBuilder.append(" ");
		stringBuilder.append("Lapenta");
		stringBuilder.append(", ");
		stringBuilder.append(new Date().toLocaleString());
		// Will create: Report generated by: _name, _date
		Paragraph p2 = new Paragraph(stringBuilder.toString(), smallBold);
		p2.setAlignment(Paragraph.ALIGN_CENTER);
		preface.add(p2);
		addEmptyLine(preface, 3);
		// preface.add(new
		// Paragraph("This document describes something which is very important ",
		// smallBold));

		// addEmptyLine(preface, 8);

		// preface.add(new
		// Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
		// redFont));
		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private void addContent(Document document) throws Exception {
		if (idReporte == 1) {

			// Second parameter is the number of the chapter

			// add a table
			Paragraph catPart = new Paragraph();
			createTable(catPart, document);

			// Next section
			/*
			 * anchor = new Anchor("Second Chapter", catFont);
			 * anchor.setName("Second Chapter");
			 * 
			 * // Second parameter is the number of the chapter
			 */

			/*
			 * subPara = new Paragraph("Subcategory", subFont); subCatPart =
			 * catPart.addSection(subPara); subCatPart.add(new
			 * Paragraph("This is a very important message"));
			 * 
			 * // now add all this to the document document.add(catPart);
			 */
		}
	}

	private void createTable(Paragraph p, Document d) throws Exception {
		if (idReporte == 1) {
			table = new PdfPTable(5);
			p.setAlignment(Element.ALIGN_CENTER);

			// t.setBorderColor(BaseColor.GRAY);
			// t.setPadding(4);
			// t.setSpacing(4);
			// t.setBorderWidth(1);

			PdfPCell c1 = new PdfPCell(new Phrase("Farmacia"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Producto"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Fecha"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Trimestre"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("Cantidad vendida"));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(c1);

			table.setHeaderRows(1);

			//preparar(d, table);
		}
	}

	/*@SuppressWarnings({ "rawtypes", "deprecation" })
	private void preparar(Document d, PdfPTable table) throws Exception {
		ArrayList<ArrayList<Object>> result = dao.getInformesFarmaciasDAO()
				.getInformesFarmaciasConFiltros((FarmaciasDTO) params.get("farmacia"),
						(ProductosDTO) params.get("producto"), (Date) new Date((Long) params.get("fechaDesde")),
						new Date(params.get("fechaHasta")), (Integer) params.get("trimestre"));
		if (result.isEmpty()) {
			throw new Exception("No hay datos");
		}
		Iterator itr = result.iterator();
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/YYYY");
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			table.addCell(obj[0].toString());
			table.addCell(obj[1].toString());
			obj[2] = obj[2].toString().replace('-', '/');
			table.addCell(s.format(new Date(obj[2].toString())));
			table.addCell(obj[3].toString());
			table.addCell(obj[4].toString());
		}

		d.add(table);
	}
*/
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}