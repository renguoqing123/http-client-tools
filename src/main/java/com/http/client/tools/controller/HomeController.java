package com.http.client.tools.controller;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.http.client.tools.cache.MapCache;
import com.http.client.tools.controller.add.ParameterController;
import com.http.client.tools.util.HttpUtils;
import com.http.client.tools.util.JsonValidator;
import com.http.client.tools.util.MethodType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class HomeController extends ParameterController implements Initializable {
	
	@FXML private Pane homePane;
	
	@FXML private Pane homeChilderPane;
	
	@FXML private SplitPane splitPane;
	
	@FXML private AnchorPane anchorPane;
	
	@FXML private MenuItem nenuItemGet;
	@FXML private MenuItem nenuItemHead;
	@FXML private MenuItem nenuItemPost;
	@FXML private MenuItem nenuItemPut;
	@FXML private MenuItem nenuItemPatch;
	@FXML private MenuItem nenuItemDelete;
	@FXML private MenuItem nenuItemTrace;
	
	@FXML private MenuButton menuButtonUrl;
	
	@FXML private MenuItem nenuItemAddBody;
	@FXML private MenuItem nenuItemAddParameter;
	@FXML private MenuItem nenuItemAddHeader;
	@FXML private MenuItem nenuItemAddContentType;
	
	@FXML private MenuButton menuButtonParam;
	
	@FXML private TextField urlText;
	
	@FXML private TextArea resultText;
	
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(location);
		System.out.println(resources);
	}
	
	@FXML
	public void addBody() {
		String text = nenuItemAddBody.getText();
		double newWidth = homeChilderPane.getPrefWidth();
		double newHeight = homeChilderPane.getPrefHeight();
		System.out.println(newWidth+":"+newHeight);
        AnchorPane pane = new AnchorPane();//面板
    	pane.setPrefWidth(newWidth);
    	pane.setPrefHeight(newHeight);
    	
    	Parent parent = loader.load("/fxml/addBody.fxml");
    	Object cacheBodyText = MapCache.getCache(MapCache.body);
    	if(null != cacheBodyText) {
        	Pane paneText = (Pane)parent;
        	TextArea textArea = (TextArea)paneText.getChildren().get(0);
        	textArea.setText(String.valueOf(cacheBodyText));
    	}
    	
    	menuButtonParam.setText(text);
    	pane.getChildren().setAll(parent);
    	homeChilderPane.getChildren().setAll(pane);
	}

	@FXML
	public void addParameter() {
		String text = nenuItemAddParameter.getText();
		double newWidth = homeChilderPane.getPrefWidth();
		double newHeight = homeChilderPane.getPrefHeight();
		System.out.println(newWidth+":"+newHeight);
        AnchorPane pane = new AnchorPane();//面板
    	pane.setPrefWidth(newWidth);
    	pane.setPrefHeight(newHeight);
    	Parent parent = loader.load("/fxml/addParameter.fxml");
    	Object cacheText = MapCache.getCache(MapCache.parameter);
    	handle(newWidth, newHeight, parent, cacheText);
    	menuButtonParam.setText(text);
    	pane.getChildren().setAll(parent);
    	homeChilderPane.getChildren().setAll(pane);
	}

	private void handle(double newWidth, double newHeight, Parent parent, Object cacheText) {
		if(null != cacheText) {
			Map dataMap= (Map)cacheText;
    		Pane paneText = (Pane)parent;
    		ScrollPane scrollPane = (ScrollPane)paneText.getChildren().get(0);
    		Node content = scrollPane.getContent();
    		AnchorPane anchorPane = (AnchorPane)content;
    		HBox h = (HBox)anchorPane.getChildren().get(0);
    		
    		double newLayoutY = 0;
    		Iterator it = dataMap.entrySet().iterator();
    		while (it.hasNext()) {
    			Map.Entry map = (Map.Entry)it.next();
    			
				HBox box = new HBox();
		        box.setPrefWidth(h.getPrefWidth());
		        box.setPrefHeight(h.getPrefHeight());
		    	newLayoutY = newLayoutY + 22.0;
		    	box.setLayoutY(newLayoutY);
		    	
		    	addHBox(box,map,anchorPane);
		    	
				System.out.println(map);
				
				newLayoutY++;

	    		anchorPane.getChildren().add(box);
			}
    		anchorPane.getChildren().remove(0);//删除头部空行
    		sort(anchorPane);//删完索引排序
    	}
	}
	

	@FXML
	public void addHeader() {
		String text = nenuItemAddHeader.getText();
		double newWidth = homeChilderPane.getPrefWidth();
		double newHeight = homeChilderPane.getPrefHeight();
		System.out.println(newWidth+":"+newHeight);
        AnchorPane pane = new AnchorPane();//面板
    	pane.setPrefWidth(newWidth);
    	pane.setPrefHeight(newHeight);
    	Parent parent = loader.load("/fxml/addHeader.fxml");
    	Object cacheText = MapCache.getCache(MapCache.header);
    	handle(newWidth, newHeight, parent, cacheText);
    	menuButtonParam.setText(text);
    	pane.getChildren().setAll(parent);
    	homeChilderPane.getChildren().setAll(pane);
	}

	@FXML
	public void addContentType() {
		String text = nenuItemAddContentType.getText();
		double newWidth = homeChilderPane.getPrefWidth();
		double newHeight = homeChilderPane.getPrefHeight();
		System.out.println(newWidth+":"+newHeight);
        AnchorPane pane = new AnchorPane();//面板
    	pane.setPrefWidth(newWidth);
    	pane.setPrefHeight(newHeight);
    	Parent parent = loader.load("/fxml/addContentType.fxml");
    	menuButtonParam.setText(text);
    	pane.getChildren().setAll(parent);
    	homeChilderPane.getChildren().setAll(pane);
	}
	
	@FXML
	public void get() {
		menuButtonUrl.setText(nenuItemGet.getText());
	}
	
	@FXML
	public void head() {
		menuButtonUrl.setText(nenuItemHead.getText());
	}
	
	@FXML
	public void post() {
		menuButtonUrl.setText(nenuItemPost.getText());
	}
	
	@FXML
	public void put() {
		menuButtonUrl.setText(nenuItemPut.getText());
	}
	
	@FXML
	public void patch() {
		menuButtonUrl.setText(nenuItemPatch.getText());
	}
	
	@FXML
	public void delete() {
		menuButtonUrl.setText(nenuItemDelete.getText());
	}
	
	@FXML
	public void trace() {
		menuButtonUrl.setText(nenuItemTrace.getText());
	}

	@FXML
	public void run() {
		String method = menuButtonUrl.getText();
		String url = urlText.getText();
		System.out.println(method+":"+url);
		String result = HttpUtils.getInstance().checkHttps(url);
		if(null != result) {
			resultText.setText(result);
			return ;
		}
		Object body = MapCache.getCache(MapCache.body);
		String jsonContent=null;
		if(null != body) {
			System.out.println(body);
			boolean bool = new JsonValidator().validate(body.toString());
			if(!bool) {
				result = "body json format is error!";
				resultText.setText(result);
				return ;
			}
			jsonContent = String.valueOf(body);
		}
		Object parameter = MapCache.getCache(MapCache.parameter);
		Map parameterMap =null;
		if(null!=parameter) {
			parameterMap = (Map)parameter;
			System.out.println(parameterMap);
		}
		Object header = MapCache.getCache(MapCache.header);
		Map headerMap = null;
		if(null!=header) {
			headerMap = (Map)header;
			System.out.println(headerMap);
		}
		try {
			if(method.equals(MethodType.GET.name())) {
				result = HttpUtils.getInstance().get(url, parameterMap, headerMap);
			}else if(method.equals(MethodType.POST.name())) {
				result = HttpUtils.getInstance().postJson(url, jsonContent, headerMap);
			}else if(method.equals(MethodType.HEAD.name())) {
				result = HttpUtils.getInstance().head(url, jsonContent, headerMap);
			}else if(method.equals(MethodType.OPTIONS.name())) {
				result = HttpUtils.getInstance().options(url, jsonContent, headerMap);
			}else if(method.equals(MethodType.PUT.name())) {
				result = HttpUtils.getInstance().put(url, jsonContent, headerMap);
			}else if(method.equals(MethodType.DELETE.name())) {
				result = HttpUtils.getInstance().delete(url, jsonContent, headerMap);
			}else if(method.equals(MethodType.TRACE.name())) {
				result = HttpUtils.getInstance().trace(url, jsonContent, headerMap);
			}else {
				resultText.setText("请求方式有误！");
			}
			if(null!=result) {
				resultText.setText(result);
			}
		} catch (Exception e) {
			resultText.setText(e.getMessage());
		}
	}
}
