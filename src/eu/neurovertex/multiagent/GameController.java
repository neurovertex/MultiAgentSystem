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
import java.util.*;

/**
 * Created by benji on 02/01/15.
 */
public class GameController implements EventHandler<GlobalGrid.CellChanged>, Initializable {
    private static String EMPTY_COLOR = "#FFFFEE";//"#334455";
    private static String UNKNOW_COLOR = "#889977";//"#FFFF7F";
    private static String OBSTACLE_COLOR = "#334455";
    private static String AGENT_COLOR = "#FFBB11";//"#FFFF7F";
    private static String SELECTED_COLOR = "#5599FF";//"#FFFF7F";
    private static String EXPLORED_COLOR = "#D8D8D8";

    @FXML
    private Pane rootPane;
    @FXML
    private GridPane gridPane;

    private Pane[][] panes;
    private boolean editable = true;
    private GlobalGrid grid;
    //private ObservableList<Pane> selectedList = FXCollections.observableList();
    private HashMap<Pane, int[]> selectedList;

    private Simulator simulateur;

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
        simulateur = new Simulator(grid);
    }

    @Override
    public void handle(GlobalGrid.CellChanged event) {
        updateCell(event.getX(), event.getY());
    }

    public void play() {
        for (GridAgent a: grid.getAgents()) {
            panes[grid.getPosAgent(a.getAgent()).getX()][grid.getPosAgent(a.getAgent()).getY()].setStyle("-fx-background-color: " + EMPTY_COLOR);
        }
        simulateur.run();
        for (GridAgent a: grid.getAgents()) {
            panes[grid.getPosAgent(a.getAgent()).getX()][grid.getPosAgent(a.getAgent()).getY()].setStyle("-fx-background-color: " + AGENT_COLOR);
        }
    }

    private void updateCell(int x, int y) {
        //panes[x][y].setStyle("-fx-background-color: " + (grid.isLive(x, y) ? LIVE_COLOR : DEAD_COLOR));
        panes[x][y].setStyle("-fx-background-color: " + EMPTY_COLOR);
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

    public void applyGridChoice(String selectedItem) {
        //for (Pane paneSelected:selectedList) {
        for (Pane paneSelected: selectedList.keySet()) {
            switch (selectedItem){
                case "EMPTY":
                    paneSelected.setStyle("-fx-background-color: " + EMPTY_COLOR);
                    break;
                case "UNKNOW":
                    paneSelected.setStyle("-fx-background-color: " + UNKNOW_COLOR);
                    break;
                case "OBSTACLE":
                    paneSelected.setStyle("-fx-background-color: " + OBSTACLE_COLOR);
                    grid.set(selectedList.get(paneSelected)[0],selectedList.get(paneSelected)[1],Grid.StaticElement.OBSTACLE);
                    break;
                case "AGENT":
                    paneSelected.setStyle("-fx-background-color: " + AGENT_COLOR);
                    Agent a = new AgentRobot();
                    Position p = new Position(selectedList.get(paneSelected)[0],selectedList.get(paneSelected)[1]);
                    grid.addAgent(a,p);
                    break;
            }
        }
        this.selectedList.clear();
    }

    public void setGrid(GlobalGrid grid) {
        if (this.grid == grid)
            return;

        this.grid = grid;

        this.panes = new Pane[grid.getWidth()][grid.getHeight()];
        this.selectedList = new HashMap<Pane, int[]>();

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
                        if (panes[xx][yy].getStyle().equals("-fx-background-color: #FFFFEE")) {
                            if (!selectedList.containsValue(panes[xx][yy])) {//!selectedList.contains(panes[xx][yy])) {
                                panes[xx][yy].setStyle("-fx-background-color: " + SELECTED_COLOR);
                                int[] tab = {xx,yy};
                                selectedList.put(panes[xx][yy],tab);
                            }
                            else {
                                panes[xx][yy].setStyle("-fx-background-color: " + EMPTY_COLOR);
                                int[] tabR = {xx,yy};
                                selectedList.remove(tabR);
                            }
                        }
                    }
                });

                gridPane.add(panes[x][y], x, y);
                updateCell(x, y);
            }
        }

        grid.setOnCellChanged(this);
    }
}
