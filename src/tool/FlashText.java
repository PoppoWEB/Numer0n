package tool;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FlashText extends Text{
    public FlashText(String str, int st, int ct, int et) {
        super(str);

        // Timelineを使用してラベルの点滅アニメーションを作成
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(st), new KeyValue(opacityProperty(), 1.0)),
                new KeyFrame(Duration.seconds(ct), new KeyValue(opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(et), new KeyValue(opacityProperty(), 1.0))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setFontSize(int size) {
        setFont(new Font(size));
    }
}