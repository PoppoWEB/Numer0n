package tool;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class NewLabel extends StackPane {
    private String text = "";
    private Label label;
    private Rectangle background;

    private double TEXT_SIZE = 3;
    protected double WIDTH = 80; // 固定幅
    protected double HEIGHT = 80; // 固定高さ

    public NewLabel() {
        this("");
    }

    public NewLabel(String text) {
        this.text = text;
        label = new Label(text);
        label.setTextFill(Color.BLUE); // ラベルの文字色を設定
        label.setScaleX(TEXT_SIZE);
        label.setScaleY(TEXT_SIZE);

        background = new Rectangle(WIDTH, HEIGHT); // 幅と高さを固定値で設定

        background.setFill(Color.GRAY);
        background.setStroke(Color.BLACK);

        getChildren().addAll(background, label);
    }

    public NewLabel(String text, double WIDTH, double HEIGHT) {
        this(text);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        background.setWidth(WIDTH);
        background.setHeight(HEIGHT);
    }

    public void setWidth(int wid) {
        background.setWidth(wid);
    }

    public void setHight(int hig) {
        background.setHeight(hig);
    }

    public void setBackgroundColor(Color color) {
        background.setFill(color);
    }

    public void setText(String text) {
        this.text = text;
        this.label.setText(this.text);
    }

    public int getInt() {
        return Integer.parseInt(text);
    }

    public String getText() {
        return this.text;
    }

    public void setLabelSize(int i) {
        this.label.setFont(new Font(i));
    }

    public int getTextNumber() {
        return Integer.valueOf(text);
    }

    public void setColor(Color c) {
        label.setTextFill(c);
    }
}