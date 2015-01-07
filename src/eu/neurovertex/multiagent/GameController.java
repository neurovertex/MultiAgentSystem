package eu.neurovertex.multiagent;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class GameController implements EventHandler<GlobalGrid.CellChanged>, Initializable {
    private static String DEAD_COLOR = "#334455";
    private static String LIVE_COLOR = "#FFFF7F";

    @FXML
    private Pane rootPane;
    @FXML
    private GridPane gridPane;

    private Pane[][] panes;
    private boolean editable = true;
    private GlobalGrid grid;
    //private ObservableList<Pane> selectedList = FXCollections.observableList();
    private ArrayList<Pane> selectedList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ChangeListener<Number> listener = (o, oldVal, newVal) -> {
            double ratio = grid.getWidth() / (double)grid.getHeight();
            double maxHeight, maxWidth;
            maxWidth = Math.min(rootPane.getWidth(), rootPane.getHeight() * ratio);
            maxHeight = Math.min(rootPane.getHeight(), rootPane.getWidth() / ratio);
            gridPane.setMaxWidth(maxWidth);
            gridPane.setMaxHeight(maxHeight);
        };

        rootPane.widthProperty().addListener(listener);
        rootPane.heightProperty().addListener(listener);

    }

    @Override
    public void handle(GlobalGrid.CellChanged event) {
        updateCell(event.getX(), event.getY());
    }

    public void play() {
    }

    private void updateCell(int x, int y) {
        //panes[x][y].setStyle("-fx-background-color: " + (grid.isLive(x, y) ? LIVE_COLOR : DEAD_COLOR));
        panes[x][y].setStyle("-fx-background-color: " + DEAD_COLOR);
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public GlobalGrid getGrid() {
        return grid;
    }

    public void applyGridChoice(Object selectedItem) {

    }

    public void setGrid(GlobalGrid grid) {
        if (this.grid == grid)
            return;

        this.grid = grid;

        this.panes = new Pane[grid.getWidth()][grid.getHeight()];
        this.selectedList = new ArrayList<Pane>();

        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        for (int i = 0; i < grid.getWidth(); i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(c);
        }

        gridPane.getRowConstraints().clear();
        for (int i = 0; i < grid.getHeight(); i++) {
            RowConstraints c = new RowConstraints();
            c.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(c);
        }

        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                final int xx = x;
                final int yy = y;
                panes[x][y] = new Pane();
                panes[x][y].setOnMouseClicked((e) -> {
                    if (getEditable()) {
                        if(!selectedList.contains(panes[xx][yy])) {
                            panes[xx][yy].setStyle("-fx-background-color: " + LIVE_COLOR);
                            selectedList.add(panes[xx][yy]);
                        }
                        else {
                            panes[xx][yy].setStyle("-fx-background-color: " + DEAD_COLOR);
                            selectedList.remove(selectedList.indexOf(panes[xx][yy]));
                        }
                        //grid.setLive(xx, yy, !grid.isLive(xx, yy));
                    }
                });

                gridPane.add(panes[x][y], x, y);
                updateCell(x, y);
            }
        }

        grid.setOnCellChanged(this);
    }
}
