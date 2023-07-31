package frame.title;

import java.util.function.Consumer;

import frame.title.other.AnimePanel;
import game.GameSetter;
import game.level.Human;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tool.ButtonImg;

public class TopComponentTitle extends BorderPane {
    private final AnimePanel rulePane;
    private final AnimePanel fightPane;
    private final Consumer<Pane> panelChange;
    private final Human myHuman;

    public TopComponentTitle(Consumer<Pane> panelChange ,AnimePanel RULE_PANE, AnimePanel fightPane, Human myHuman) {
        this.panelChange = panelChange;
        this.rulePane = RULE_PANE;
        this.fightPane = fightPane;
        this.myHuman = myHuman;

        setLeft(playerInfo());
        setRight(playState());
    }

    private HBox playState() {
        HBox stateBox = new HBox();
        stateBox.setSpacing(10);
        stateBox.setPadding(new Insets(10));

        ImageView settingView = ButtonImg.makeImgButton(GameSetter.SETTING_FILE_PATH);

        settingView.setOnMouseClicked(event -> {
            rulePane.playAnime();
            panelChange.accept(rulePane);
        });

        ImageView fightView = ButtonImg.makeImgButton(GameSetter.FIGHT_FILE_PATH);
        fightView.setOnMouseClicked(event -> {
            fightPane.playAnime();
            panelChange.accept(fightPane);
        });

        stateBox.getChildren().addAll(fightView, settingView);

        return stateBox;
    }

    private VBox playerInfo() {
        VBox playerBox = new VBox();
        playerBox.setSpacing(3);
        playerBox.setPadding(new Insets(5));

        TextField name_field = new TextField(myHuman.getMyName());
        name_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > GameSetter.MAX_NAME_LENGTH) {
                name_field.setText(newValue.substring(0, GameSetter.MAX_NAME_LENGTH));
            }
            Human.setName(name_field.getText());
        });
        name_field.setStyle("-fx-background-radius: 15; -fx-border-radius: 15;");

        Label point_text = new Label("Rate: " + myHuman.getMyRate());
        Human.rateProperty().addListener((observable, oldValue, newValue) -> {
            point_text.setText("Rate: " + Human.getRate());
        });

        point_text.setFont(new Font("Arial", 20));
        point_text.setTextFill(Color.SKYBLUE);

        Text winRate_text = new Text(myHuman.getRecord());
        winRate_text.setFont(new Font("Arial", 20));
        winRate_text.setFill(Color.SKYBLUE);

        playerBox.getChildren().addAll(name_field, point_text, winRate_text);

        return playerBox;
    }
}