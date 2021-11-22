package com.http.client.tools.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FxmlLoader {

	public Parent load(String url) {
        try {
    		URL sourseUrl = this.getClass().getResource(url);
            FXMLLoader loader = new FXMLLoader(sourseUrl);
			return loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
}
