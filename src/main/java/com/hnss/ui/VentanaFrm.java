package com.hnss.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class VentanaFrm extends Window {

    /**
     *
     */
    private static final long serialVersionUID = 3553450551393827863L;

    public VentanaFrm() {
    }

    public VentanaFrm(String caption) {
        super(caption);
    }

    public VentanaFrm(UI ui, Layout vt, String captacion) {
        super("", vt);
        // this.setWidth("440px");
        // this.setClosable(false);
        this.setStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
        this.setWidth(vt.getWidth(), vt.getWidthUnits());
        this.setHeightUndefined();
        // this.setHeight(vt.getHeight(), vt.getHeightUnits());
        this.center();
        this.setModal(true);
        this.isResizeLazy();
        this.isResizable();
        this.setCaption(captacion);
        this.setCaptionAsHtml(true);
        ui.addWindow(this);
    }

    public VentanaFrm(UI ui, FormLayout vt, String captacion) {
        super("", vt);
        // this.setWidth("440px");
        // this.setClosable(false);
        this.setStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
        this.setWidth(vt.getWidth(), vt.getWidthUnits());
        this.setHeightUndefined();
        // this.setHeight(vt.getHeight(), vt.getHeightUnits());
        this.center();
        this.setModal(true);
        this.isResizeLazy();
        this.isResizable();
        this.setCaption(captacion);
        this.setCaptionAsHtml(true);
        ui.addWindow(this);
    }

    public VentanaFrm(UI ui, HorizontalLayout ht, String captacion) {
        super("", ht);
        // this.setWidth("440px");
        // this.setClosable(false);
        this.setStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
        this.setWidth(ht.getWidth(), ht.getWidthUnits());
        this.setHeightUndefined();
        // this.setHeight(vt.getHeight(), vt.getHeightUnits());
        this.center();
        this.setModal(true);
        this.isResizeLazy();
        this.isResizable();
        this.setCaption(captacion);
        this.setCaptionAsHtml(true);
        ui.addWindow(this);
    }

    public VentanaFrm(String caption, Component content) {
        super(caption, content);
    }

}
