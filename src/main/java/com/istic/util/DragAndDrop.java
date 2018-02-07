package com.istic.util;

import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DragAndDrop {
	
    private static final DataFormat nodeFormat = new DataFormat("MyNode");
    private static Node draggingNode;
	
    public static void dragNode(Node b) {
        b.setOnDragDetected(e -> {
        Dragboard db = b.startDragAndDrop(TransferMode.MOVE);
        db.setDragView(b.snapshot(null, null)); 
        ClipboardContent cc = new ClipboardContent();
        cc.put(nodeFormat, " "); 
        db.setContent(cc); 
        draggingNode = b;    
    });
    }
    
    public static void addDropHandling(StackPane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(nodeFormat) && draggingNode != null) {
                e.acceptTransferModes(TransferMode.MOVE);

            }
        });

        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            if (db.hasContent(nodeFormat)) {
                ((Pane)draggingNode.getParent()).getChildren().remove(draggingNode);
                pane.getChildren().add(draggingNode);
                e.setDropCompleted(true);

                draggingNode = null;
            }           
        });

    }
}
