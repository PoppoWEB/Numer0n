package frame.title.other;

import game.GameSetter;
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

public class RulePanel extends AnimePanel {
    private Text ruleDesText[];
    private final Text ruleTitle = new Text("GAME");
    private final String ruleDesString[] = {"相手よりも先に数字を当てるべし!", "","~HIT~", "数字も位置も合っている", "","~BLOW~","数字は合っているが位置が違う", "","通信対戦では勝敗によってRateが変わるぞ!"};
    private final ImageView backBtn  = ButtonImg.makeImgButton(GameSetter.RETURN_FILE_PATH);
    private final Runnable returnMain;
    private final String BACKGROUND_FILE_PATH = "file:./lib/imgs/RULE_back.png";

    public RulePanel(Runnable returnMain) {
        this.returnMain = returnMain;
        ruleTitle.setFont(new Font(50));
        ruleTitle.setFill(Color.RED);
        setAlignment(ruleTitle, Pos.CENTER);

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

        ruleTitle.setFont(new Font(50));
        ruleTitle.setFill(Color.RED);
        setAlignment(ruleTitle, Pos.CENTER);

        panel.setTop(ruleTitle);
        panel.setCenter(getStringAndBackBox());

        BorderPane.setAlignment(ruleTitle, Pos.CENTER);

        return panel;
    }

    private Pane getStringAndBackBox() {
        VBox panel = new VBox();
        panel.setAlignment(Pos.CENTER);
        panel.setSpacing(10);

        VBox ruleBox = new VBox();
        ruleBox.setAlignment(Pos.TOP_CENTER);

        ruleDesText = new Text[ruleDesString.length];
        for (int i = 0; i < ruleDesString.length; i++) {
            ruleDesText[i] = new Text(ruleDesString[i]);
            ruleDesText[i].setFont(new Font(30));
            ruleBox.getChildren().add(ruleDesText[i]);
        }

        PointAllCover cover = new PointAllCover(ruleBox);
        backBtn.setOnMouseClicked(event -> pushButton(backBtn));
        backBtn.setTranslateY(33);

        panel.getChildren().addAll(cover, backBtn);
        return panel;
    }

    public void playAnime() {
        super.playAnime(ruleDesText);
    }

    private void pushButton(Object button) {
        returnMain.run();
    }
}
