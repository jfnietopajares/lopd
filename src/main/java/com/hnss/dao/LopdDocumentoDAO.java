package com.hnss.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.lopd.LopdDocumento;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class LopdDocumentoDAO extends ConexionDAO {
	private static final Logger logger = LogManager.getLogger(LopdDocumentoDAO.class);

	public LopdDocumentoDAO() {
		super();
	}

	public boolean grabaDatos(LopdDocumento documento) {
		boolean actualizado = false;

		if (documento.getId().equals(new Long(0))) {
			actualizado = this.insertaDatos(documento);
		} else {
			actualizado = this.actualizaDatos(documento);
		}
		return actualizado;
	}

	public boolean insertaDatos(LopdDocumento documento) {
		Connection connection = null;
		boolean insertado = false;
		FileInputStream is = null;
		try {
			connection = super.getConexionBBDD();
			documento.setId(new UtilidadesDAO().getSiguienteId("lopd_documentos"));
			is = new FileInputStream(documento.getFicheroAdjunto());

			sql = " INSERT INTO lopd_documentos (id,idincidencia,fichero,fechacambio,hora,usucambio,estado)"
					+ " VALUES (?,?,?,?,?,?,?)  ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, documento.getId());
			statement.setLong(2, documento.getIdIncidenica().getId());
			statement.setBlob(3, is);
			statement.setLong(4, Utilidades.getFechayyymmdd(documento.getFecha()));
			statement.setInt(5, Utilidades.getHora(documento.getHora()));
			statement.setLong(6, documento.getUsuCambio().getId());
			statement.setInt(7, documento.getEstado());
			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return insertado;
	}

	public boolean actualizaDatos(LopdDocumento documento) {
		Connection connection = null;
		boolean actualizado = false;
		FileInputStream is = null;
		try {
			connection = super.getConexionBBDD();
			documento.setId(new UtilidadesDAO().getSiguienteId("documentos"));
			is = new FileInputStream(documento.getFicheroAdjunto());
			sql = " UPDATE  lopd_documentos (file =? ,fechacmabio=?,hora=?,usucambio=?) WHERE id=? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBlob(1, is);
			statement.setLong(2, Utilidades.getFechayyymmdd(documento.getFecha()));
			statement.setInt(3, Utilidades.getHora(documento.getHora()));
			statement.setLong(4, documento.getUsuCambio().getId());
			statement.setLong(5, documento.getId());
			actualizado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return actualizado;
	}

	public ArrayList<LopdDocumento> getDocumentos(LopdIncidencia incidencia) {
		Connection connection = null;
		ArrayList<LopdDocumento> lista = new ArrayList<>();
		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM  lopd_documentos  WHERE estado= " + Constantes.BBDD_ACTIVOSI + " AND  idincidencia= "
					+ incidencia.getId();
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				LopdDocumento documento = getDocumentoResulset(resulSet, incidencia);
				lista.add(documento);
			}
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return lista;
	}

	public LopdDocumento getDocumentoResulset(ResultSet resulSet, LopdIncidencia incidencia) {
		LopdDocumento documento = new LopdDocumento();
		try {
			documento.setId(resulSet.getLong("id"));
			documento.setFecha(Utilidades.getFechaLocalDate(resulSet.getLong("fecha")));
			documento.setHora(Utilidades.getHoraHH_MM(resulSet.getLong("hora")));
			if (incidencia != null)
				documento.setIdIncidenica(incidencia);
			else
				documento.setIdIncidenica(
						new LopdIncidenciaDAO().getIncidenciaId(resulSet.getLong("idinciencia"), null, null));

			// documento.setFicheroAdjunto(resulSet.getBlob("fichero"));

		} catch (SQLException e) {
			logger.error(Notificaciones.SQLERRORRESULSET);
		}

		return documento;
	}

	public File getFilePdf(LopdDocumento documento) {
		File file = null;
		try {
			FileOutputStream outpu = null;
			String pathname = documento.getPathFilePdf();
			file = new File(pathname);
			outpu = new FileOutputStream(file);
			Blob archivo = new LopdDocumentoDAO().getBlobPdfId(documento.getId());
			InputStream inStream = archivo.getBinaryStream();
			int size = (int) archivo.length();
			byte[] buffer = new byte[size];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				outpu.write(buffer, 0, length);
			}
			outpu.close();
		} catch (Exception ioe) {
			logger.error(ioe);
		}
		return file;
	}

	public StreamResource getStreamPDF(LopdDocumento documento) {
		StreamSource resource = null;
		try {
			Blob archivo = new LopdDocumentoDAO().getBlobPdfId(documento.getId());
			resource = new StreamResource.StreamSource() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -1341398535003627835L;

				@Override
				public InputStream getStream() {
					try {
						return new ByteArrayInputStream(archivo.getBytes(new Long(1), (int) archivo.length()));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
			};
		} catch (Exception ioe) {
			logger.error(ioe);
		}
		return new StreamResource(resource, "fichero.pdf");
	}

	public java.sql.Blob getBlobPdfId(Long id) {
		Connection connection = null;
		java.sql.Blob pdfBlob = null;
		try {
			connection = super.getConexionBBDD();
			sql = "SELECT fichero  FROM lopd_documentos  WHERE id=? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resulSet = statement.executeQuery();
			if (resulSet.next()) {
				pdfBlob = resulSet.getBlob("fichero");
			}
			statement.close();
			logger.debug("SELECT docubin  FROM informes  WHERE id= " + id);
		} catch (SQLException e) {
			logger.error("SELECT docubin  FROM informes  WHERE id= " + id);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return pdfBlob;
	}
}
