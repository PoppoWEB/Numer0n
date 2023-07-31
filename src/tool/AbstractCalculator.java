package tool;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public abstract class AbstractCalculator {
    private Button[] numberBtns = new Button[10];
    private Button decisionBtn = new Button("OK");
    private Button cancelBtn = new Button("←");

    public abstract void numberButtonAction(Button button);
    public abstract void cancelButtonAction();
    public abstract void okButtonAction();

    public AbstractCalculator() {

        for (int i = 0; i < numberBtns.length; i++) {
            numberBtns[i] = new Button(String.valueOf(i));
        }

        for (Button button : numberBtns) {
            button.setOnAction(event -> {
                numberButtonAction(button);
            });
        }
        
        cancelBtn.setOnAction(event -> cancelButtonAction());
        decisionBtn.setOnAction(event -> okButtonAction());
    }

    protected GridPane createNumberGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));
        gridPane.setAlignment(Pos.CENTER);

        int columnCount = 3;
        int rowCount = 3;
        int buttonIndex = 1;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                gridPane.add(numberBtns[buttonIndex%10], col, row);
                buttonIndex++;
            }
        }

        gridPane.add(numberBtns[0], 1, 3);
        gridPane.add(cancelBtn, 3, 0);
        gridPane.add(decisionBtn, 3, 2);

        return gridPane;
    }

    protected void disiableOn() {
        decisionBtn.setDisable(false);
        cancelBtn.setDisable(false);
        for (int i = 0; i < numberBtns.length; i++) {
            numberBtns[i].setDisable(false);
        }
    }
    
    protected void disiableOff() {
        decisionBtn.setDisable(true);
        cancelBtn.setDisable(true);
        for (int i = 0; i < numberBtns.length; i++) {
            numberBtns[i].setDisable(true);
        }
    }

    protected Button getCancelBtn() {
        return cancelBtn;
    }

    protected Button getDecisionBtn() {
        return decisionBtn;
    }
    
    protected Button[] getNumberBtns() {
        return numberBtns;
    }
}