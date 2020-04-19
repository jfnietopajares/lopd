package com.hnss.listados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdNotas;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

public class IncidenciaPdfInformeCompleto {
	private static final Logger logger = LogManager.getLogger(IncidenciaPdfInformeCompleto.class);

	public static final String DEST = Constantes.DIRECTORIOREPORTS + "incidencia"
			+ Long.toString(Utilidades.getHoraNumeroAcual()) + ".pdf";

	private File file;

	private LopdIncidencia incidencia;

	DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	public IncidenciaPdfInformeCompleto(LopdIncidencia inciParamIncidencia) {
		incidencia = inciParamIncidencia;
		file = new File(DEST);
		if (file.exists())
			file.delete();

		file.getParentFile().mkdirs();

		this.createPdf(DEST);
	}

	public static String getDest() {
		return DEST;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void createPdf(String dest) {

		PdfDocument pdf;
		try {

			ArrayList<LopdNotas> listaNotas = new LopdIncidenciaDAO().getNostasIncidencia(incidencia);
			pdf = new PdfDocument(new PdfWriter(dest));

			Document document = new Document(pdf, PageSize.A4).setTextAlignment(TextAlignment.JUSTIFIED);

			document.setMargins(75, 36, 75, 36);

			PdfEventoPagina evento = new PdfEventoPagina(document,
					" Informe completo sobre la Incidencia de seguridad " + incidencia.getTipo().getDescripcion());

			pdf.addEventHandler(PdfDocumentEvent.END_PAGE, evento);

			// PdfFont normal = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

			// PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			PdfFont times = null;
			try {
				times = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
			} catch (IOException e) {
				e.printStackTrace();
			}

			Paragraph paragraph0 = new Paragraph();
			paragraph0.add("\n Informe completo sobre la Incidencia de seguridad").setFontSize(15);
			paragraph0.setTextAlignment(TextAlignment.CENTER);
			paragraph0.setFont(times);
			document.add(paragraph0);

			Float altura = new Float(30f);
			int sizeFont = 7;

			float[] anchos = { 90f, 450f };
			Table tabla = new Table(anchos);

			// String[] tituloSrings = { "Nhc", "Apellidos y nombre", "Fecha I,", "Motivo",
			// "Fecha Fin ", "Motivo Baja." };
			tabla.setMarginTop(10);
			Cell cell = new Cell();
			cell.add(new Paragraph("Número").setFont(times).setFontSize(11));
			tabla.addCell(cell);
			Cell cell0 = new Cell();
			cell0.add(new Paragraph(Long.toString(incidencia.getId())).setFont(times).setFontSize(11));
			tabla.addCell(cell0);

			tabla.addCell(new Cell().add(new Paragraph("Tipo")).setFont(times).setFontSize(11));
			Cell cell1 = new Cell();
			cell1.add(new Paragraph(incidencia.getTipoDescripcion()).setFont(times).setFontSize(11));
			tabla.addCell(cell1);

			Cell cell2 = new Cell();
			cell2.add(new Paragraph("Fecha").setFont(times).setFontSize(11));
			tabla.addCell(cell2);
			Cell cell3 = new Cell();
			DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern("dd/mm/YYYY hh:mm");
			cell3.add(new Paragraph(fechaFormato.format(incidencia.getFechaHora())).setFont(times).setFontSize(11));
			tabla.addCell(cell3);

			Cell cell4 = new Cell();
			cell4.add(new Paragraph("Solicitante").setFont(times).setFontSize(11));
			tabla.addCell(cell4);
			Cell cell5 = new Cell();
			cell5.add(
					new Paragraph(incidencia.getUsuarioRegistra().getApellidosNombre()).setFont(times).setFontSize(11));
			tabla.addCell(cell5);

			Cell cell6 = new Cell();
			cell6.add(new Paragraph("Descripción").setFont(times).setFontSize(11));
			tabla.addCell(cell6);
			Cell cell7 = new Cell();
			if (incidencia.getDescripcionError() != null)
				cell7.add(new Paragraph(incidencia.getDescripcionError()).setFont(times).setFontSize(11));
			else
				cell6.add(new Paragraph());

			tabla.addCell(cell7);

			Cell cell8 = new Cell();
			cell8.add(new Paragraph("Fecha resolución.").setFont(times).setFontSize(11));
			tabla.addCell(cell8);
			Cell cell9 = new Cell();
			if (incidencia.getFechaSolucion() != null)
				cell9.add(new Paragraph(incidencia.getFechaSolucionString()).setFont(times).setFontSize(11));
			else
				cell9.add(new Paragraph());

			tabla.addCell(cell9);

			Cell cell10 = new Cell();
			cell10.add(new Paragraph("Técnico").setFont(times).setFontSize(11));
			tabla.addCell(cell10);
			Cell cell11 = new Cell();
			if (incidencia.getUsuCambio() != null)
				cell11.add(
						new Paragraph(incidencia.getUsuCambio().getApellidosNombre()).setFont(times).setFontSize(11));
			else
				cell11.add(new Paragraph());
			tabla.addCell(cell11);

			Cell cell12 = new Cell();
			cell12.add(new Paragraph("Solución").setFont(times).setFontSize(11));
			tabla.addCell(cell12);
			Cell cell13 = new Cell();
			if (incidencia.getDescripcionSolucion() != null)
				cell13.add(new Paragraph(incidencia.getDescripcionSolucion()).setFont(times).setFontSize(11));
			else
				cell13.add(new Paragraph());
			tabla.addCell(cell13);
			document.add(tabla);

			if (listaNotas.size() > 0) {
				float[] anchoTabla = { 50f, 120f, 460f };
				Table tablaNotas = new Table(anchoTabla);
				tablaNotas.addCell(new Cell().add(new Paragraph("Fecha")).setFont(times).setFontSize(9));
				tablaNotas.addCell(new Cell().add(new Paragraph("Usuario")).setFont(times).setFontSize(9));
				tablaNotas.addCell(new Cell().add(new Paragraph("Texto de la nota")).setFont(times).setFontSize(9));

				for (LopdNotas nota : listaNotas) {
					tablaNotas.addCell(
							new Cell().add(new Paragraph(nota.getFechaHoraFormato())).setFont(times).setFontSize(9));
					tablaNotas.addCell(new Cell().add(new Paragraph(nota.getUsucambio().getApellidosNombre()))
							.setFont(times).setFontSize(9));
					tablaNotas.addCell(
							new Cell().add(new Paragraph(nota.getDescripcion())).setFont(times).setFontSize(9));
				}
				document.add(tablaNotas);
			}

			Paragraph paragraph1 = new Paragraph();
			paragraph1.add("\n\n\n fecha impresión:" + fechaFormato.format(LocalDateTime.now())).setFontSize(8);
			paragraph1.setTextAlignment(TextAlignment.LEFT);
			paragraph1.setFont(times);
			document.add(paragraph1);
			document.close();

		} catch (FileNotFoundException e) {
			logger.error(Notificaciones.EXCEPTION_FILENOTFOUND, e);
		} catch (IOException e) {
			logger.error(Notificaciones.EXCEPTION_IO, e);
		}
	}

}
