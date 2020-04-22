package com.hnss.ui.lopd;

import java.time.LocalDate;
import java.util.ArrayList;

import com.hnss.dao.LopTiposDAO;
import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.ObjetosComunes;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class LopdIncidenciaGestionar extends HorizontalLayout {

	private HorizontalLayout formulario = new HorizontalLayout();
	private VerticalLayout contenedorGrid = new VerticalLayout();
	private HorizontalLayout filaFiltro = new HorizontalLayout();
	private DateField desde;
	private DateField hasta;
	private ComboBox<LopdTipos> comboTipos = new ComboBox<LopdTipos>("Tipo");
	private ComboBox<String> resuelta;
	private Button buscar = new Button();

	private Grid<LopdIncidencia> grid = new Grid<LopdIncidencia>();

	public LopdIncidenciaGestionar() {
		this.setMargin(false);
		this.setSpacing(true);
		this.addComponents(formulario, contenedorGrid);
		this.setCaption("Resolución de incidencias de seguridad relacionadas con la LOPD.");
		formulario.setMargin(false);
		formulario.setSpacing(false);
		formulario.addComponent(new LopdIncidenciaRespuesta(new LopdIncidencia()));
		contenedorGrid.setMargin(true);
		contenedorGrid.setSpacing(true);
		contenedorGrid.addComponents(filaFiltro, grid);

		desde = new ObjetosComunes().getFecha("Desde", " desde");
		desde.addValueChangeListener(e -> clickBuscar());
		// desde.setValue(LocalDate.now());
		hasta = new ObjetosComunes().getFecha("Hasta", " desde");
		hasta.addValueChangeListener(e -> clickBuscar());
		// hasta.setValue(LocalDate.now());
		comboTipos.setItems(new LopTiposDAO().getListaIncidenciaTipos(LopdSujeto.SUJETO_PACIENTE, null));
		comboTipos.setWidth("200px");
		comboTipos.setItemCaptionGenerator(LopdTipos::getDescripcion);
		comboTipos.addSelectionListener(e -> clickBuscar());

		resuelta = new ObjetosComunes().getComoSiNo("Pendientes", "", "Si");
		resuelta.addSelectionListener(e -> clickBuscar());

		buscar.setIcon(VaadinIcons.SEARCH);
		buscar.addClickListener(e -> clickBuscar());
		filaFiltro.addComponents(resuelta, desde, hasta);

		grid.addColumn(LopdIncidencia::getId).setCaption("Nº");
//		grid.addColumn(LopdIncidencia::getResueltaString).setCaption("Esta.");
		grid.addColumn(LopdIncidencia::getFechaHoraFormato).setCaption("Fecha");
		grid.addColumn(LopdIncidencia::getDescripcionError).setCaption("Problema");
		grid.addSelectionListener(e -> selecciona());
		// grid.setStyleGenerator(lopdIncidencia -> lopdIncidencia.getResuelta() ? "red"
		// : "yellow");
		grid.setStyleGenerator(e -> getEstilo(e));
		// grid.setSelectionMode(SelectionMode.SINGLE);

		clickBuscar();

	}

	private String getEstilo(LopdIncidencia e) {
		if (e.getResuelta() == true) {
			return "yellow1";
		} else
			return "green";

	}

	public void selecciona() {
		if (grid.getSelectedItems().size() > 0) {
			LopdIncidencia incidencia = grid.getSelectedItems().iterator().next();
			formulario.removeAllComponents();
			formulario.addComponent(new LopdIncidenciaRespuesta(incidencia));
		} else {
			new Notificaciones("Sin dato");
		}
	}

	public void clickBuscar() {
		LopdTipos tipo = null;
		String resueltapara = null;

		if (comboTipos.getSelectedItem().isPresent()) {
			tipo = comboTipos.getSelectedItem().get();
		}
		LocalDate desdeDate = null, hastaDate = null;
		if (desde.getValue() != null)
			desdeDate = desde.getValue();

		if (hasta.getValue() != null)
			hastaDate = hasta.getValue();

		if (resuelta.getSelectedItem().isPresent()) {
			if (resuelta.getSelectedItem().get().equals("Si"))
				resueltapara = "S";
			else if (resuelta.getSelectedItem().get().equals("Ni"))
				resueltapara = "N";
		}

		ArrayList<LopdIncidencia> lista = new LopdIncidenciaDAO().getListaInicidencias(desdeDate, hastaDate, tipo, null,
				resueltapara);
		grid.setItems(lista);

	}
}
