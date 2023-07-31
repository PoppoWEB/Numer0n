package frame.title.other;

import game.GameSetter;
import game.level.Human;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tool.ButtonImg;
import tool.PointAllCover;

public class FightPanel extends AnimePanel{
    private Text fightDesText[];
    private final Text fightTitle = new Text("戦績");
    private final String fightString[] = {"Rate","Easy:\t", "Normal:", "Hard:", "Human:"};
    private final ImageView backBtn  = ButtonImg.makeImgButton(GameSetter.RETURN_FILE_PATH);
    private final Runnable returnMain;
    private final Font font = Font.font("Meiryo UI", 35); // フォントサイズは適宜調整
    private final String BACKGROUND_FILE_PATH = "file:./lib/imgs/FIGHT_back.jpeg";

    public FightPanel(Runnable returnMain) {
        this.returnMain = returnMain;

        setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));

        getChildren().addAll(setBack(),setComponent());
    }

    private Pane setBack() {
        // パネルbackground(GIF)
        Pane panel = new Pane();
        Image gif = new Image(BACKGROUND_FILE_PATH);
        ImageView imageView = new ImageView(gif);

        imageView.setFitWidth(GameSetter.primaryStage.getWidth());
        imageView.setFitHeight(GameSetter.primaryStage.getHeight());

        // ウィンドウサイズが変更された場合に画像をリサイズするイベントを設定
        GameSetter.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitWidth(newVal.doubleValue());
        });
        GameSetter.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitHeight(newVal.doubleValue());
        });

        panel.getChildren().add(imageView);

        return panel;
    }

    private Pane setComponent() {
        BorderPane panel = new BorderPane();

        fightTitle.setFont(new Font(50));
        fightTitle.setFill(Color.RED);
        
        panel.setTop(fightTitle);
        panel.setCenter(getStringAndBackBox());

        BorderPane.setAlignment(fightTitle, Pos.CENTER);

        return panel;
    }

    private Pane getStringAndBackBox() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);

        VBox fightBox = new VBox();
        fightBox.setSpacing(30);
        fightBox.setAlignment(Pos.TOP_CENTER);

        fightDesText = new Text[fightString.length];
        fightDesText[0] = new Text(fightString[0] + "\t" + Human.getRate());
        fightDesText[0].setFont(font);
        Human.rateProperty().addListener((observable, oldValue, newValue) -> {
            fightDesText[0].setText(fightString[0] + "\t" + newValue);
        });

        fightBox.getChildren().add(fightDesText[0]);
        for (int i = 1; i < fightString.length; i++) {
            int index = i - 1;
            fightDesText[i] = new Text(fightString[index] + "\t" + Human.getRate());
            fightDesText[i].setFont(font);
            fightDesText[i].textProperty().bind(new SimpleStringProperty(fightString[i]).concat("\t")
                .concat(Human.recordProperty(index, 0).asString())
                .concat("勝 ")
                .concat(Human.recordProperty(index, 1).asString())
                .concat("敗 ")
                .concat(Human.recordProperty(index, 2).asString())
                .concat("分"));
            fightBox.getChildren().add(fightDesText[i]);
        }

        PointAllCover cover = new PointAllCover(fightBox);
        backBtn.setOnMouseClicked(event -> pushButton(backBtn));
        backBtn.setTranslateY(34);

        panel.getChildren().addAll(cover, backBtn);
        return panel;
    }

    @Override
    public void playAnime() {
        super.playAnime(fightDesText);
    }

    private void pushButton(Object button) {
        returnMain.run();
    }
}
