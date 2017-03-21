package nl.han.ica.icss.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import nl.han.ica.icss.ast.AST;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.checker.errors.SemanticError;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ASTPane extends BorderPane {

    private TreeView<ASTNode> content;
    private Label title;

    public ASTPane() {
        super();

        title = new Label("Internal (AST):");
        content = new TreeView<>();
        content.setCellFactory(treeview -> new TreeCell<ASTNode>() {
            @Override
            public void updateItem(ASTNode item, boolean empty) {
                super.updateItem(item, empty);

                getStyleClass().removeAll("error");

                setOnMouseClicked(event -> {
                    if(item.hasError())
                        JOptionPane.showMessageDialog(null, displayErrors(item.getErrors()));
                });

                if (empty) {
                    setText("");
                } else {
                    setText(item.getNodeLabel());
                    if (item.hasError()) {
                        getStyleClass().add("error");
                    }
                }
            }
        });
        title.setPadding(new Insets(5, 5, 5, 5));

        setTop(title);
        setCenter(content);
        setMinWidth(400);
    }

    private String displayErrors(ArrayList<SemanticError> errors) {
        StringBuilder builder = new StringBuilder();
        for (SemanticError error : errors)
            builder.append(error).append(System.lineSeparator());
        return builder.toString();
    }

    /**
     * Updates the panes based on the current content of the AST
     *
     * @param ast
     */
    public void update(AST ast) {
        List<ASTNode> nodesInTree = new ArrayList<>();
        content.setRoot(astNodeToTreeItem(ast.root, nodesInTree));
    }

    private TreeItem<ASTNode> astNodeToTreeItem(ASTNode astNode, List<ASTNode> nodesInTree) {
        TreeItem<ASTNode> tvNode = new TreeItem<ASTNode>(astNode);
        tvNode.setExpanded(true);

        for (ASTNode child : astNode.getChildren()) {
            if(nodesInTree.contains(child))
                continue;
            tvNode.getChildren().add(astNodeToTreeItem(child, nodesInTree));
        }
        return tvNode;
    }
}
