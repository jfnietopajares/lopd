package com.hnss.controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class Uploaderr implements Receiver, SucceededListener, FailedListener, ProgressListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6580957106625007843L;
	private static final String parentDirectory = "/tmp/";
	private File file;

	public Uploaderr() {

	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		System.out.println(filename + " " + mimeType);

		File childDir = new File(pathToChildDir(filename));
		childDir.mkdir();
		file = new File(pathToFile(filename));
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}

	public File getFile() {
		return file;
	}

	private String pathToChildDir(String filename) {
		return parentDirectory + File.separator + getFileNameWithoutExtension(filename);
	}

	private String pathToFile(String filename) {
		return pathToChildDir(filename) + File.separator + filename;
	}

	private String getFileNameWithoutExtension(String file) {
		String[] s = file.split("\\.");
		return s[0];
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {

		System.out.println(event.getFilename() + " " + event.getLength() + " bytes");

	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {

		System.out.println("Status: " + readBytes + "/" + contentLength);

	}

	@Override
	public void uploadFailed(FailedEvent event) {
		System.out.println("Failed");
	}

}
