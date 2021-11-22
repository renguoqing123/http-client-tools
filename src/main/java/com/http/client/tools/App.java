package com.http.client.tools;

import com.http.client.tools.controller.FxmlLoader;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * http-client-tools 
 * @author rengq
 */
public class App extends Application {
	
	private static FxmlLoader loader = new FxmlLoader();
	
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
			Parent root = loader.load("/fxml/home.fxml"); 
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
	        primaryStage.centerOnScreen();// 设置让对话框处于屏幕中间
			primaryStage.show();
			primaryStage.widthProperty().addListener((observable, oldValue, newValue) ->{
        		System.out.println("old: (" + oldValue + "); " 
                		 		 + "new: (" + newValue + ")");
        		System.out.println(observable);
//        		if((double)oldValue>(double)newValue) {
//        			Parent root1 = loader.load("/fxml/home.fxml"); 
//        			Scene scene1 = new Scene(root1);
//        			primaryStage.setScene(scene1);
//        	        primaryStage.centerOnScreen();// 设置让对话框处于屏幕中间
//        			primaryStage.show();
//        			return;
//        		}
        		ObservableList<Node> node = root.getChildrenUnmodifiable();
        		for(Node child:node) {
					if(child instanceof SplitPane) {
						SplitPane s =(SplitPane)child; 
						s.setPrefWidth((double)newValue);
						s.setDividerPositions(0.20);
//						ObservableList<Node> anchorPaneNode = s.getItems();
//						for(Node anchorPaneChild:anchorPaneNode) {
//							if(anchorPaneChild instanceof AnchorPane) {
//								AnchorPane ap =(AnchorPane)anchorPaneChild; 
//								ap.setPrefWidth((double)newValue);
//								ObservableList<Node> textNode = ap.getChildren();
//								for(Node text:textNode) {
//									if(text instanceof Button) {
//										Button b = (Button)text;
//										double lx = b.getLayoutX();
//										double v = handle(newValue, lx);
//										b.setLayoutX(v);
////									}
////									else if(text instanceof MenuButton) {
////										MenuButton b = (MenuButton)text;
////										double lx = b.getLayoutX();
////										double v = handle(newValue, lx);
////										b.setLayoutX(v);
//									}else if(text instanceof TextField) {
//										TextField b = (TextField)text;
//										double lx = b.getPrefWidth();
//										double v = handle(newValue, lx);
//										b.setPrefWidth(v);
//									}else if(text instanceof Pane) {
//										Pane b = (Pane)text;
//										double lx = b.getPrefWidth();
//										double v = handle(newValue, lx);
//										b.setPrefWidth(v);
//									}
//								}
//							}
//						}
					}
				}
//        		splitPane.setPrefWidth((double)newValue);
			});
		    primaryStage.heightProperty().addListener((observable, oldValue, newValue) ->{
	            System.out.println("old: (" + oldValue + "); " 
                		 		 + "new: (" + newValue + ")");
	            ObservableList<Node> node = root.getChildrenUnmodifiable();
        		for(Node child:node) {
					if(child instanceof SplitPane) {
						SplitPane s =(SplitPane)child; 
						s.setPrefHeight((double)newValue);
					}
				}
//	            splitPane.setPrefHeight((double)newValue);
		    });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private double handle(Number newValue, double lx) {
		double v = (double)newValue;
		v = v - lx;
		v = v*0.9;
		v = v + lx;
		System.out.println(v);
		return v;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
