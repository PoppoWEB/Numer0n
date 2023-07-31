package frame.game;

import game.GameTable;
import game.level.Human;
import game.level.Level;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tool.NewLabel;
// 2686
public class SidePlayBoard extends VBox {
    private final Text name;
    private final Text rate;
    private final GameTable boardName;
    private final NewLabel[] inputLabels;
    private final int number[];

    public SidePlayBoard(String boardName[], int number[]) {
        this.number = number;
        this.boardName = new GameTable(boardName);
        this.name = new Text(Human.getName());
        this.rate = new Text("Rate: "+Human.getRate());
        this.inputLabels = new NewLabel[number.length];

        getChildren().addAll(setTextBox(), setNumberBoardBox());
    }

    public SidePlayBoard(String boardName[], int number[], Level enemy) {
        this.number = number;
        this.name = new Text(enemy.getMyName());
        this.rate = new Text(enemy.level() == 3 ? "Rate: " + enemy.getMyRate() : enemy.getMyRate());
        this.boardName = new GameTable(boardName);
        this.inputLabels = new NewLabel[number.length];

        getChildren().addAll(setTextBox(), setNumberBoardBox());
    }

    private VBox setTextBox() {
        VBox textBox = new VBox();
        textBox.getChildren().addAll(name, rate);
        return textBox;
    }

    private VBox setNumberBoardBox() {
        VBox numAndBoardBox = new VBox();
        numAndBoardBox.setSpacing(5);
        numAndBoardBox.setAlignment(Pos.CENTER);
        boardName.setMaxWidth(200);
        numAndBoardBox.getChildren().addAll(setNumberLabel(), boardName);

        return numAndBoardBox;
    }

    private HBox setNumberLabel() {
        HBox numberBox = new HBox();
        numberBox.setSpacing(20);

        for (int i = 0; i < number.length; i++) {
            inputLabels[i] = new NewLabel(String.valueOf(number[i]));
            numberBox.getChildren().add(inputLabels[i]);
        }

        return numberBox;
    }

    public void setFontColor(Color c) {
        name.setFill(c);
        rate.setFill(c);
        for (int i = 0; i < inputLabels.length; i++) {
            inputLabels[i].setColor(c);
        }
    }

    public void setTextFont(double size) {
        name.setFont(new Font(size));
        rate.setFont(new Font(size));
    }

    public void setLabelSize(int size) {
        for (int i = 0; i < inputLabels.length; i++) {
            inputLabels[i].setLabelSize(size);
        }
    }

    public void setNumberFont(int size) {
        for (int i = 0; i < inputLabels.length; i++) {
            inputLabels[i].setLabelSize(size);
        }
    }

    public void addData(String result[]) {
        boardName.addData(result);
    }

    public void setBoardPrefWidth(int idx, int size) {
        boardName.setPrefWidth(idx, size);
    }

    public void setBoardPrefHeight(int size) {
        boardName.setPrefHeight(size);
    }

    public void hide(String h) {
        for (int i = 0; i < inputLabels.length; i++) {
            inputLabels[i].setText(h);
        }
    }

    public void open() {
        for (int i = 0; i < inputLabels.length; i++) {
            inputLabels[i].setText(String.valueOf(number[i]));
        }
    }
}