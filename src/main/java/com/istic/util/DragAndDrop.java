package com.istic.util;

import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DragAndDrop {
	
    private static final DataFormat nodeFormat = new DataFormat("MyNode");
    private static Node draggingNode;
    private Controller controller;

    public DragAndDrop(Controller controller) {
        this.controller = controller;
    }

    /**
     * permet le drag sur le Node
     * @param b the node
     */
    public void dragNode(Node b) {
        b.setOnDragDetected(e -> {
                    if(controller.getTemporaryCableModuleController() == null) {
                        Dragboard db = b.startDragAndDrop(TransferMode.MOVE);
                        db.setDragView(b.snapshot(null, null),e.getX(),e.getY());
                        ClipboardContent cc = new ClipboardContent();
                        cc.put(nodeFormat, " ");
                        db.setContent(cc);
                        draggingNode = b;
                    }
        });
    }

    /**
     * evenement sur le node
     * @param pane the pane
     */
    public void addDropHandling(StackPane pane) {
        // onDragOver
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(nodeFormat) && draggingNode != null && pane.getChildren().isEmpty()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        // onDragDropped
        pane.setOnDragDropped(e -> {

            Dragboard db = e.getDragboard();
            if (db.hasContent(nodeFormat)) {
                ((Pane)draggingNode.getParent()).getChildren().remove(draggingNode);
                pane.getChildren().add(draggingNode);
                e.setDropCompleted(true);

                draggingNode = null;
            }

            // Update cable position
            AnchorPane p = (AnchorPane) e.getGestureSource();
            ModuleController moduleController = (ModuleController) p.getUserData();
            moduleController.updateCablesPosition();

        });

    }
}
