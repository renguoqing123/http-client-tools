package com.http.client.tools.controller.abs;

import com.http.client.tools.controller.FxmlLoader;

import javafx.scene.layout.AnchorPane;

public abstract class AbstractController {
	
	public static FxmlLoader loader = new FxmlLoader();

	public abstract void mouseExited();
	
	public abstract void addText();
	
	public abstract void delText();
	
	public abstract void sort(AnchorPane anchorPane);
	
	
}
