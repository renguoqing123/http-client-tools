package com.http.client.tools.controller.add;

import com.http.client.tools.cache.MapCache;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class BodyController {
	
	@FXML
	private TextArea textArea;
	
	@FXML
	public void mouseExited() {
		String text = textArea.getText();
		if(null != text) {
			MapCache.setCache(MapCache.body, text);
		}
	}
	
	@FXML
	public void mouseMoved() {
		System.out.println("mouseMoved");
	}
}
