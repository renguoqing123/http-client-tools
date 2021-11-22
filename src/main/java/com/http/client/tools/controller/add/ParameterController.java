package com.http.client.tools.controller.add;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.http.client.tools.cache.MapCache;
import com.http.client.tools.controller.abs.AbstractController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ParameterController extends AbstractController{
	
	@FXML
	private AnchorPane anchorPaneParameter;
	
	@FXML
	private HBox hboxParameter;
	
	private String cache = MapCache.parameter;

	@FXML
	public void addText() {
		int lastIndex = getAnchorPaneParameter().getChildren().size();
		Node node = getAnchorPaneParameter().getChildren().get(lastIndex-1);
		HBox h = (HBox)node;
		double newLayoutY = h.getLayoutY();
		System.out.println(newLayoutY);
		double newWidth = getHboxParameter().getPrefWidth();
        double newHeight = getHboxParameter().getPrefHeight();
        HBox box = new HBox();
        System.out.println(getHboxParameter().idProperty().get());
        box.setPrefWidth(newWidth);
        box.setPrefHeight(newHeight);
    	newLayoutY = newLayoutY + 22.0;
    	box.setLayoutY(newLayoutY);
    	
    	addHBox(box,null,null);
    	
    	getAnchorPaneParameter().getChildren().add(box);
	}

	public void addHBox(HBox box, Map.Entry map, AnchorPane anchorPane) {
		String k=null,v=null;
		if(null!=map) {
			k = map.getKey().toString();
			v = map.getValue().toString();
		}
		Label lb = new Label();//layoutX="36.0" layoutY="18.0" prefHeight="24.0" prefWidth="43.0"
    	lb.setId("key");
    	lb.setText("  key：");
    	lb.setLayoutX(36.0);
    	lb.setLayoutY(18.0);
    	lb.setPrefHeight(24.0);
    	lb.setPrefWidth(43.0);
    	box.getChildren().add(lb);
    	TextField tf = new TextField();
    	tf.setLayoutX(68.0);
    	tf.setLayoutY(15.0);
    	if(null!=k && !"".equals(k)) {tf.setText(k);}
    	box.getChildren().add(tf);
    	
    	Label vlb = new Label();//layoutX="255.0" layoutY="18.0" prefHeight="32.0" prefWidth="50.0"
    	vlb.setId("value");
    	vlb.setText(" value：");
    	vlb.setLayoutX(255.0);
    	vlb.setLayoutY(18.0);
    	vlb.setPrefHeight(32.0);
    	vlb.setPrefWidth(50.0);
    	box.getChildren().add(vlb);
    	TextField vtf = new TextField();
    	vtf.setLayoutX(68.0);
    	vtf.setLayoutY(15.0);
    	if(null!=v && !"".equals(v)) {vtf.setText(v);}
    	box.getChildren().add(vtf);
    	
    	Button add = new Button();//layoutX="491.0" layoutY="14.0" mnemonicParsing="false" onAction="#addText" text="+"
    	add.setText("+");
    	add.setLayoutX(491.0);
    	add.setLayoutY(14.0);
    	add.setMnemonicParsing(false);
    	add.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("+++++++++++++");
				if(null!=anchorPane) {
					setAnchorPaneParameter(anchorPane);
					setHboxParameter(box);
					addText();
					event.consume();
					return;
				}
				Button button = (Button)event.getSource();
				setHboxParameter((HBox)button.getParent());
				addText();
				event.consume();
			}
		});
    	box.getChildren().add(add);
    	
    	Button del = new Button();//layoutX="532.0" layoutY="14.0" mnemonicParsing="false" onAction="#delText" text="-" 
    	del.setText("-");
    	del.setLayoutX(532.0);
    	del.setLayoutY(14.0);
    	del.setMnemonicParsing(false);
    	del.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("-------------");
				if(null!=anchorPane) {
					setAnchorPaneParameter(anchorPane);
					setHboxParameter(box);
					delText();
					event.consume();
					return;
				}
				Button button = (Button)event.getSource();
				setHboxParameter((HBox)button.getParent());
				delText();
				event.consume();
			}
		});
    	box.getChildren().add(del);
	}
	
	@FXML
	public void delText() {
		getAnchorPaneParameter().getChildren().remove(getHboxParameter());
		sort(null);
		
	}

	public void sort(AnchorPane anchorPane) {
		if(null!=anchorPane) {setAnchorPaneParameter(anchorPane);}
		ObservableList<Node> observablelist = getAnchorPaneParameter().getChildren();
//		double index = hbox.getLayoutY();
		double i = 0;
		for (Node node : observablelist) {
			HBox hBox = (HBox)node;
			if(i==0) {
				hBox.setLayoutY(i);
				i++;
				continue;
			}
			i = i + 22.0;
			hBox.setLayoutY(i);
			i++;
		}
	}
	
	public void save() {
		ObservableList<Node> nodeList  = getAnchorPaneParameter().getChildren();
		List<Map> list = new ArrayList();
		Map data = new HashMap();
		for(Node node:nodeList) {
			HBox hBox = (HBox)node;
			ObservableList<Node> boxList= hBox.getChildren();
			String k = null;
			String v = null;
			for(Node value:boxList) {
				if(value instanceof TextField) {
					String id = value.getId();
					String val = ((TextField) value).getText();
					if(null == k) {//只能一行两列
						k = val;
					}else {
						v = val;
					}
				}
			}
			if(null == k || null == v) {
				continue;
			}
			data.put(k, v);
			list.add(data);
		}
		MapCache.setCache(getCache(), data);
	}
	
	@FXML
	public void mouseExited() {
		System.out.println("鼠标离开事件");
		save();
	}

	public HBox getHboxParameter() {
		return hboxParameter;
	}

	public void setHboxParameter(HBox hboxParameter) {
		this.hboxParameter = hboxParameter;
	}

	public AnchorPane getAnchorPaneParameter() {
		return anchorPaneParameter;
	}

	public void setAnchorPaneParameter(AnchorPane anchorPaneParameter) {
		this.anchorPaneParameter = anchorPaneParameter;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}
}
