package com.istic.util;
import javafx.scene.layout.AnchorPane;


public class Style {
	
	static final String[] colorBorder = new String[]{"#74777c","#4b1610", "#e1e4ed"};
	static final String[] colorBackground = new String[]{"#c4c7ce","#e0ac69", "#e6e8ed"};
	static final String[] imageBackground = new String[]{"metal1.jpg","wood.png"};
	
	
	public static void updateStyleTheme(AnchorPane pane, int i){
		
    	if(i < 2){
    		pane.setStyle("-fx-border-radius: 6; -fx-border-width: 5; -fx-border-color: "+colorBorder[i]+";"
    			+ " -fx-background-radius: 11;  -fx-background-color: "+colorBackground[i]+"; "
    			+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); ");
    	}
    	else pane.setStyle("-fx-border-radius: 6; -fx-border-width: 5; -fx-border-color: "+colorBorder[i]+";"
    			+ " -fx-background-radius: 11;  -fx-background-color: "+colorBackground[i]+"; "
    			+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0); ");
	 }
	
	
	public static void updateStyle(AnchorPane pane, int i){
		if(i < 2){ pane.setStyle("-fx-background-image: url("+ imageBackground[i] +");");}
		else pane.setStyle("");
	}

}
