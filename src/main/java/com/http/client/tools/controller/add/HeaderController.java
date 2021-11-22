package com.http.client.tools.controller.add;

import com.http.client.tools.cache.MapCache;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class HeaderController extends ParameterController{

	@FXML
	private AnchorPane anchorPaneHeader;
	
	@FXML
	private HBox hboxHeader;
	
	public void initialize() {
		System.out.println("HeaderController initialize");
		setAnchorPaneParameter(this.anchorPaneHeader);
		setHboxParameter(this.hboxHeader);
		setCache(MapCache.header);
	}

	@FXML
	public void mouseExited() {
		initialize();
		super.mouseExited();
	}

	@FXML
	public void addText() {
		initialize();
		super.addText();
	}

	@FXML
	public void delText() {
		initialize();
		super.delText();
	}

	
	
}
