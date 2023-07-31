package tool;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FrameFadeout {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        FrameFadeout.primaryStage = primaryStage;
    }

    private static boolean checkStage() {
        return primaryStage != null ? false : true;
    }

    public static void nextScene(Pane closePane, Scene nextScene) {
        if (checkStage()) return;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), closePane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        fadeTransition.setOnFinished(event -> {
            // フェードアウト完了後の処理
            primaryStage.setScene(nextScene);
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), closePane);
            fadeInTransition.setFromValue(0.0);   // 開始時の不透明度（完全に透明）
            fadeInTransition.setToValue(1.0);     // 終了時の不透明度（完全に不透明）
            fadeInTransition.play();
        });
        fadeTransition.play();
    }
}