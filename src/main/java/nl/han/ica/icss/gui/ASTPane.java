/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
