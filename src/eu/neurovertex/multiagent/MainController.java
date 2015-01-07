package eu.neurovertex.multiagent;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by benji on 02/01/15.
 */

public class MainController implements Initializable {
    private final int NB_X_CELLS = 100;
    private final int NB_Y_CELLS = 100;

    @FXML
    private GameController gameController;
    @FXML
    private TextField timeStepText;
    @FXML
    private TextField iterationsText;
    @FXML
    private Label currentIterationLabel;
    @FXML
    private Button go;
    @FXML
    private CheckBox startRunAuto;
    @FXML
    private TextField probabilityText;
    @FXML
    private ComboBox gridChoice;

    private ArrayList<GlobalGrid> history = new ArrayList<>();

    private int animationCount;
    private int currentIteration;
    private Task<Void> task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emptyGrid();
    }

    public void emptyGrid() {
        GlobalGrid grid = new GlobalGrid(NB_X_CELLS, NB_Y_CELLS);
        gameController.setGrid(grid);
        restart();
    }

    private void restart() {
        if (task != null)
            task.cancel();

        history.clear();
        history.add(gameController.getGrid());
        currentIteration = 0;
        updateStatus();
    }

    private void updateView() {
        updateStatus();
        gameController.setGrid(history.get(currentIteration).clone());
    }

    void updateStatus() {
        //currentIterationLabel.setText(currentIteration + " / " + (history.size() - 1));
        //go.setDisable(currentIteration != (history.size() - 1) || (task != null && task.isRunning()));
        //gameController.setEditable(currentIteration == 0 && history.size() == 1);
    }

    @FXML
    private void onGoClick() {
        final int iterations = Integer.parseInt(iterationsText.getText());
        final int timeStep = Integer.parseInt(timeStepText.getText());

        if (currentIteration == 0)
            history.set(0, gameController.getGrid().clone());

        animationCount = 0;
        updateStatus();
        go.setDisable(true);

        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (!isCancelled()) {
                    Platform.runLater(() -> {
                        if (animationCount >= iterations || isCancelled()) {
                            return;
                        }
                        gameController.play();

                        GlobalGrid newGrid = gameController.getGrid().clone();

                        int oldIndex = history.indexOf(newGrid);

                        if (oldIndex == -1) {
                            animationCount++;
                            currentIteration++;

                            history.add(newGrid);

                            if (animationCount >= iterations) {
                                cancel();
                            }
                        } else {
                            cancel();
                            currentIteration = oldIndex;
                            updateView();
                        }

                        updateStatus();
                    });
                    Thread.sleep(timeStep);
                }
                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onPrevClick() {
        if (currentIteration >= 0) {
            currentIteration--;
            if (currentIteration < 0) {
                currentIteration = history.size() - 1;
            }
            updateView();
        }
    }

    @FXML
    private void onNextClick() {
        if (currentIteration <= (history.size() - 1)) {
            currentIteration++;
            if (currentIteration == history.size()) {
                currentIteration = 0;
            }
            updateView();
        }
        if (currentIteration == history.size()) {
            currentIteration = 0;
            updateView();
        }
    }

    @FXML
    private void onRandomClick() {
        emptyGrid();

        GlobalGrid grid = gameController.getGrid();

        for(int i = 0; i < NB_X_CELLS; i++) {
            for (int j = 0; j < NB_Y_CELLS; j++) {
                //grid.setState(i, j, true);
            }
            i = i + 5;
        }
        if (startRunAuto.isSelected())
            onGoClick();
    }

    @FXML
    private void onCurrentClick() {
        restart();
    }

    @FXML
    private void onEmptyClick() {
        emptyGrid();
    }

    @FXML
    private void onApplyGridChoice(){
        gameController.applyGridChoice(gridChoice.getSelectionModel().getSelectedItem());
    }
}
