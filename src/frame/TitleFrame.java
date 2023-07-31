package frame;

import java.util.concurrent.Executors;

import frame.title.CenterComponentTitle;
import frame.title.TopComponentTitle;
import frame.title.other.FightPanel;
import frame.title.other.RulePanel;
import game.GameSetter;
import game.level.Human;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import tool.FrameFadeout;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class TitleFrame extends Application{
    private static final EventHandler<MouseEvent> eventFilter = event -> event.consume();
    private final String BACKGROUND_FILE_PATH = "file:./lib/imgs/Space.gif";
    private final Human myHuman = Human.loadFromFile();
    private final BorderPane mainPane = new BorderPane();
    private static Scene mainScene;
    private Stage primaryStage;
    

    @Override
    public void start(Stage primaryStage) { 
        this.primaryStage = primaryStage;
        GameSetter.primaryStage = primaryStage;
        initUI(primaryStage);
        // Scene変更用のメインStageをセット
        FrameFadeout.setPrimaryStage(primaryStage);
        // シーンの状態セット
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Numer0n");
        primaryStage.setResizable(true);
        primaryStage.setFullScreen(false);
        primaryStage.setOnCloseRequest(e -> {
            Human.saveToFile();
            System.exit(0);
        });
        // 表示
        primaryStage.show();
    }

    private void initUI(Stage primaryStage) {
        // パネルIcon
        Image icon = new Image(GameSetter.ICON_FILE_PATH);
        primaryStage.getIcons().add(icon);

        // 各パネルとメインシーンの設定
        RulePanel rulePane = new RulePanel(this::returnMain);
        FightPanel fightPane = new FightPanel(this::returnMain);

        mainPane.getChildren().add(setBack());
        CenterComponentTitle center = new CenterComponentTitle(this::sceneFilterAdd, this::sceneFilterRemove, mainPane);
        mainPane.setCenter(center);
        TopComponentTitle top = new TopComponentTitle(this::changePanel, rulePane, fightPane, myHuman);
        mainPane.setTop(top);
    
        mainScene = new Scene(mainPane, GameSetter.STAGE_W, GameSetter.STAGE_H);
    }

    private Pane setBack() {
        // パネルbackground(GIF)
        Pane panel = new Pane();
        Image gif = new Image(BACKGROUND_FILE_PATH);
        ImageView imageView = new ImageView(gif);

        imageView.setFitWidth(primaryStage.getWidth());
        imageView.setFitHeight(primaryStage.getHeight());

        // ウィンドウサイズが変更された場合に画像をリサイズするイベントを設定
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitWidth(newVal.doubleValue());
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            imageView.setFitHeight(newVal.doubleValue());
        });

        panel.getChildren().add(imageView);

        return panel;
    }

    private void returnMain() {
        mainScene.setRoot(mainPane);
    }

    private void changePanel(Pane panel) {
        mainScene.setRoot(panel);
    }

    private void sceneFilterAdd() {
        mainScene.addEventFilter(MouseEvent.MOUSE_CLICKED, eventFilter);
    }

    private void sceneFilterRemove() {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                
            }
            mainScene.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventFilter);
        });
    }

    public static Scene getMainScene() {            
        return mainScene;
    }

    public static void main(String[] args) {
       // Title画面
       launch(args);
    }
}