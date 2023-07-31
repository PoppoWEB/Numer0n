package frame.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tool.NewLabel;
import tool.SlideTextAnimation;

public class CenterTexts extends VBox{
    private Text R;
    private NewLabel roundLabel;
    private SlideTextAnimation plyerText;
    private SlideTextAnimation enemyText;

    public CenterTexts() {
        roundLabel = new NewLabel("1");
        roundLabel.setLabelSize(25);
        plyerText = new SlideTextAnimation("あなたのターン", Duration.seconds(2), Duration.seconds(1));
        enemyText = new SlideTextAnimation("相手のターン", Duration.seconds(2), Duration.seconds(1));

        setSpacing(70);
        setPadding(new Insets(30));
        getChildren().addAll(round() ,northComponents());
    }

    private Pane northComponents() {
        StackPane textPane = new StackPane();
        textPane.getChildren().addAll(plyerText, enemyText);

        return textPane;
    }

    private HBox round() {
        HBox roundBox = new HBox();
        roundBox.setAlignment(Pos.CENTER);
        roundBox.setSpacing(10);

        R = new Text("R");
        R.setFont(new Font(80));

        roundBox.getChildren().addAll(R, roundLabel);
        return roundBox;
    }

    public void plyerPlay(Runnable logic) {
        plyerText.play(logic);
    }

    public void enemyPlay(Runnable logic) {
        enemyText.play(logic);
    }

    public void setRound(String round) {
        roundLabel.setText(round);
    }
}