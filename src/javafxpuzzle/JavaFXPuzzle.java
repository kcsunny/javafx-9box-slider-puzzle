
package javafxpuzzle;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class JavaFXPuzzle extends Application {

//    create an array of buttons
    Button box[] = new Button[9];
    GridPane root;
    boolean isSolved = false;
    private int posC;
    private int posR;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX 9-Box Puzzle");
        primaryStage.setResizable(false);

        root = new GridPane();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 245, 240);

        if (!isSolved) {
            scene.setOnKeyPressed(this::handleKeyEvent);
            scene.setOnKeyReleased((ke) -> {
                switch (ke.getCode()) {
                    case UP:
                    case RIGHT:
                    case DOWN:
                    case LEFT:
                        checkSolved();
                }

            });
        }

//        initialize the buttons in the box
        for (int i = 0; i < box.length; i++) {
            box[i] = new Button(String.valueOf(i));
            box[i].setFont(new Font("DejaVu Sans ExtraLight", 40));
            box[i].setFocusTraversable(false);  //to disable the selection on buttons
        }
        box[0].setVisible(false);
//        box[0].setDisable(true);

        constructGrid(getSolvableList());

        root.getChildren().addAll(box);

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public void swapWith(int col, int row) {
        Button btn = (Button) getNodeByRowColumnIndex(row, col);
        int v = Integer.parseInt(btn.getText());
        root.setConstraints(box[v], posC, posR);
        root.setConstraints(box[0], col, row);
        posC = col;
        posR = row;
    }

//  Randomize the boxes in the grid    
    public void constructGrid(ArrayList<Integer> solvableList) {
        int k = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                int index = solvableList.get(k++);
                root.setConstraints(box[index], j, i);
            }
        }
        posC = root.getColumnIndex(box[0]);
        posR = root.getRowIndex(box[0]);
    }

    public ArrayList<Integer> getSolvableList() {
        ArrayList<Integer> solvableList = new ArrayList<>();
        int inversion;
        int x = 0;
        while (true) {
            inversion = 0;


//  generate a list with random combination of numbers
            for (int i = 0; i < box.length; i++) {
                boolean isAdded = false;
                while (!isAdded) {
                    int k = (int) (Math.random() * 100) % 9;
                    if (!solvableList.contains(k)) {
                        solvableList.add(k);
                        isAdded = true;
                    }
                }
            }


//  check if the combination generated is solvable:
//  the combination is solvable if the no. of inversions are even
//  for more info visit:
//  https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
            for (int i = 0; i < solvableList.size(); i++) {
//              Oth box represents empty box so this must not be considered
                if (solvableList.get(i) > 0) {
                    for (int j = i + 1; j < solvableList.size(); j++) {
                        if (solvableList.get(j) > 0
                                && solvableList.get(i) > solvableList.get(j)) {
                            inversion++;
                        }
                    }
                }
            }

            if (inversion % 2 == 0) {
                return solvableList;
            }
            solvableList.clear();
        }
    }

    public void checkSolved() {
        int col1 = root.getColumnIndex(box[1]);
        int row1 = root.getRowIndex(box[1]);
        int col2 = root.getColumnIndex(box[2]);
        int row2 = root.getRowIndex(box[2]);
        int col3 = root.getColumnIndex(box[3]);
        int row3 = root.getRowIndex(box[3]);

        int col4 = root.getColumnIndex(box[4]);
        int row4 = root.getRowIndex(box[4]);
        int col5 = root.getColumnIndex(box[5]);
        int row5 = root.getRowIndex(box[5]);
        int col6 = root.getColumnIndex(box[6]);
        int row6 = root.getRowIndex(box[6]);

        int col7 = root.getColumnIndex(box[7]);
        int row7 = root.getRowIndex(box[7]);
        int col8 = root.getColumnIndex(box[8]);
        int row8 = root.getRowIndex(box[8]);

        if (col1 == 1 && row1 == 1
                && col2 == 2 && row2 == 1
                && col3 == 3 && row3 == 1
                && col4 == 1 && row4 == 2
                && col5 == 2 && row5 == 2
                && col6 == 3 && row6 == 2
                && col7 == 1 && row7 == 3
                && col8 == 2 && row8 == 3) {
            root.getChildren().removeAll(box);
            Label btn = new Label("Solved");
            btn.setFont(new Font("DejaVu Sans ExtraLight", 50));
            root.getChildren().add(btn);
            isSolved = true;
        }
    }

    public void handleKeyEvent(KeyEvent ke) {
        if (posC == 1 && posR == 1) {
            switch (ke.getCode()) {
                case LEFT:
                    swapWith(2, 1);
                    break;
                case UP:
                    swapWith(1, 2);
                    break;
            }
        } else if (posC == 2 && posR == 1) {
            switch (ke.getCode()) {
                case LEFT:
                    swapWith(3, 1);
                    break;
                case UP:
                    swapWith(2, 2);
                    break;
                case RIGHT:
                    swapWith(1, 1);
                    break;
            }
        } else if (posC == 3 && posR == 1) {
            switch (ke.getCode()) {
                case UP:
                    swapWith(3, 2);
                    break;
                case RIGHT:
                    swapWith(2, 1);
                    break;
            }
        } else if (posC == 1 && posR == 2) {
            switch (ke.getCode()) {
                case DOWN:
                    swapWith(1, 1);
                    break;
                case UP:
                    swapWith(1, 3);
                    break;
                case LEFT:
                    swapWith(2, 2);
                    break;
            }
        } else if (posC == 2 && posR == 2) {
            switch (ke.getCode()) {
                case DOWN:
                    swapWith(2, 1);
                    break;
                case LEFT:
                    swapWith(3, 2);
                    break;
                case UP:
                    swapWith(2, 3);
                    break;
                case RIGHT:
                    swapWith(1, 2);
                    break;
            }
        } else if (posC == 3 && posR == 2) {
            switch (ke.getCode()) {
                case DOWN:
                    swapWith(3, 1);
                    break;
                case UP:
                    swapWith(3, 3);
                    break;
                case RIGHT:
                    swapWith(2, 2);
                    break;
            }
        } else if (posC == 1 && posR == 3) {
            switch (ke.getCode()) {
                case LEFT:
                    swapWith(2, 3);
                    break;
                case DOWN:
                    swapWith(1, 2);
                    break;
            }
        } else if (posC == 2 && posR == 3) {
            switch (ke.getCode()) {
                case LEFT:
                    swapWith(3, 3);
                    break;
                case DOWN:
                    swapWith(2, 2);
                    break;
                case RIGHT:
                    swapWith(1, 3);
                    break;
            }
        } else if (posC == 3 && posR == 3) {
            checkSolved();
            switch (ke.getCode()) {
                case DOWN:
                    swapWith(3, 2);
                    break;
                case RIGHT:
                    swapWith(2, 3);
                    break;
            }

        }
    }

    //  Method to get the node at specific row and column from GridPane
    public Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = root.getChildren();
        for (Node node : childrens) {
            if (root.getRowIndex(node) == row && root.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
