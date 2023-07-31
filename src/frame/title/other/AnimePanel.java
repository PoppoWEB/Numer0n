package frame.title.other;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class AnimePanel extends StackPane{
    private static final int DISPLAY_SPEED = 500; // 表示スピード（ミリ秒）
    public abstract void playAnime();

    public void playAnime(Text texts[]) {

        for (int i = 0; i < texts.length; i++) {
            // フェードインアニメーション
            FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.millis(DISPLAY_SPEED), texts[i]);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);

            // 上からスライドアニメーション
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(DISPLAY_SPEED), texts[i]);
            translateTransition.setFromY(-20); // Y軸方向の初期位置（上から50ピクセル上）
            translateTransition.setToY(0); // Y軸方向の移動先（元の位置）

            // アニメーションの実行
            fadeTransition.play();
            translateTransition.play();
        }
    }
}