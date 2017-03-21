/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen n� het eerste commit vallen onder de MIT Licentie:
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

@SuppressWarnings("restriction")
public class OutputPane extends BorderPane {

    private Label title;
    private TextArea content;

    public OutputPane() {
        super();

        title = new Label("Output (CSS):");
        title.setPadding(new Insets(5, 5, 5, 5));

        content = new TextArea();
        content.setEditable(false);

        setTop(title);
        setCenter(content);
    }

    public String getText() {
        return content.getText();
    }

    public void setText(String text) {
        content.setText(text);
    }

    public void writeToFile(File file) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(file));
            out.print(this.getText());
            out.close();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }
}
