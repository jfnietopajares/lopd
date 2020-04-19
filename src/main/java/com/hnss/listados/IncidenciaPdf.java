package com.hnss.listados;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.lopd.LopdIncidencia;
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

public class IncidenciaPdf {
	private static final Logger logger = LogManager.getLogger(IncidenciaPdf.class);

	public static final String DEST = Constantes.DIRECTORIOREPORTS + "incidencia"
			+ Long.toString(Utilidades.getHoraNumeroAcual()) + ".pdf";

	private File file;

	private LopdIncidencia incidencia;

	DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("dd/MM/YYYY");

	public IncidenciaPdf(LopdIncidencia inciParamIncidencia) {
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

			pdf = new PdfDocument(new PdfWriter(dest));

			Document document = new Document(pdf, PageSize.A4).setTextAlignment(TextAlignment.JUSTIFIED);

			document.setMargins(75, 36, 75, 36);

			PdfEventoPagina evento = new PdfEventoPagina(document,
					" Incidencia de seguridad de datos  " + incidencia.getTipo().getDescripcion());

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
			paragraph0.add("\n   Registros de incidencia de seguridad.").setFontSize(15);
			paragraph0.setTextAlignment(TextAlignment.CENTER);
			paragraph0.setFont(times);
			document.add(paragraph0);

			Paragraph paragraph = new Paragraph();
			paragraph.add(
					"\n  La Ley 41/2002, de 14 de noviembre, báisca Reguladora de la Autonómía del paciente de de los "
							+ " derechos y obligaciones en materia de información y documentación clínica incluye en su artículo 15.1"
							+ "que la historia clínica incorporará la información que se considere trancendental para el conocimiento"
							+ "y  veraz actualizado estado del paciente  ")
					.setFontSize(12);
			paragraph.setTextAlignment(TextAlignment.JUSTIFIED);
			paragraph.setFont(times);
			document.add(paragraph);

			Paragraph paragraph1 = new Paragraph();
			paragraph1.add(
					"\n   Por otra parte, el Decreto 101/2005, de 22 de diciembre, por el que se regula la historia clínica, "
							+ " cita en su artículo 4.1 que en la historia clínica, deberá quedar constancia de toda la información sobre su"
							+ " proceso asistencial de modo que permita el conocimiento veraz y actualizado de su estado de aslud.")
					.setFontSize(12);
			paragraph1.setTextAlignment(TextAlignment.JUSTIFIED);
			paragraph1.setFont(times);
			document.add(paragraph1);

			Paragraph paragraph2 = new Paragraph();
			paragraph2.add(
					"\n   En este sentido, la Ley Orgánica 3/2018 de 5 de diciembre, de Protección de Datos Personales, "
							+ " en su artículo 4 determina que los datos de carácter personal serán exactos y si fuese necesario deben ser"
							+ " actualizados.")
					.setFontSize(12);
			paragraph2.setTextAlignment(TextAlignment.JUSTIFIED);
			paragraph2.setFont(times);
			document.add(paragraph2);

			Float altura = new Float(30f);
			int sizeFont = 7;

			float[] anchos = { 130f, 400f };
			Table tabla = new Table(anchos);

			// String[] tituloSrings = { "Nhc", "Apellidos y nombre", "Fecha I,", "Motivo",
			// "Fecha Fin ", "Motivo Baja." };
			tabla.setMarginTop(10);
			Cell cell = new Cell();
			cell.add(new Paragraph("Número").setFont(times).setFontSize(11));
			tabla.addCell(cell);
			Cell cell1 = new Cell();
			cell1.add(new Paragraph(Long.toString(incidencia.getId())).setFont(times).setFontSize(11));
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
			cell5.add(new Paragraph(incidencia.getUsuarioRegistra().getDni() + " "
					+ incidencia.getUsuarioRegistra().getApellidosNombre()).setFont(times).setFontSize(11));
			tabla.addCell(cell5);

			Cell cell6 = new Cell();
			cell6.add(new Paragraph("Descripción").setFont(times).setFontSize(11));
			tabla.addCell(cell6);
			Cell cell7 = new Cell();
			cell7.add(new Paragraph(incidencia.getDescripcionError()).setFont(times).setFontSize(11));
			tabla.addCell(cell7);

			Cell cell8 = new Cell();
			cell8.add(new Paragraph("Fecha resolución.").setFont(times).setFontSize(11));
			tabla.addCell(cell8);
			Cell cell9 = new Cell();
			cell9.add(new Paragraph(incidencia.getFechaSolucionString()).setFont(times).setFontSize(11));
			tabla.addCell(cell9);

			Cell cell10 = new Cell();
			cell10.add(new Paragraph("Técnico").setFont(times).setFontSize(11));
			tabla.addCell(cell10);
			Cell cell11 = new Cell();
			cell11.add(new Paragraph(incidencia.getUsuCambio().getApellidosNombre()).setFont(times).setFontSize(11));
			tabla.addCell(cell11);

			Cell cell12 = new Cell();
			cell12.add(new Paragraph("Solución").setFont(times).setFontSize(11));
			tabla.addCell(cell12);
			Cell cell13 = new Cell();
			cell13.add(new Paragraph(incidencia.getDescripcionSolucion()).setFont(times).setFontSize(11));
			tabla.addCell(cell13);

			document.add(tabla);

			float[] anchosf = { 260f, 260f };
			Table tablaf = new Table(anchosf);
			Cell cell14 = new Cell();
			cell14.add(new Paragraph("Solicitante \n\n\n\n").setFont(times).setFontSize(11));
			tablaf.addCell(cell14);

			Cell cell15 = new Cell();
			cell15.add(new Paragraph("Técnico \n\n\n\n").setFont(times).setFontSize(11));
			tablaf.addCell(cell15);

			Cell cell16 = new Cell();
			cell16.add(
					new Paragraph(incidencia.getUsuarioRegistra().getApellidosNombre()).setFont(times).setFontSize(11));
			tablaf.addCell(cell16);

			Cell cell17 = new Cell();
			cell17.add(new Paragraph(incidencia.getUsuCambio().getApellidosNombre()).setFont(times).setFontSize(11));
			tablaf.addCell(cell17);
			document.add(tablaf);

			document.close();

		} catch (FileNotFoundException e) {
			logger.error(Notificaciones.EXCEPTION_FILENOTFOUND, e);
		} catch (IOException e) {
			logger.error(Notificaciones.EXCEPTION_IO, e);
		}
	}
}
