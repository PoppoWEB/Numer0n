package tool;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SlideTextAnimation extends StackPane {
    private Text text;
    private final TranslateTransition translateTransition;
    private final FadeTransition fadeTransition;
    
    public SlideTextAnimation(String message, Duration duration, Duration pauseDuration) {
        text = new Text(message);
        text.setFont(Font.font(24));
        
        // テキストを初期位置に配置
        text.setTranslateX(-text.getBoundsInLocal().getWidth());
        text.setTranslateY(0);
        
        translateTransition = new TranslateTransition(duration, text);
        translateTransition.setFromX(-text.getBoundsInLocal().getWidth());
        translateTransition.setToX(getWidth() / 2 - text.getBoundsInLocal().getWidth() / 2);
        
        fadeTransition = new FadeTransition(pauseDuration);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(1.0);
        
        text.setVisible(false);
        getChildren().add(text);
    }

    public void setText(Text text) {
        this.text = text;
    }
    
    public void play(Runnable logic) {
        text.setVisible(true);
        translateTransition.play();
        translateTransition.setOnFinished(event -> fadeTransition.play());
        
        fadeTransition.setOnFinished(event -> {
            text.setVisible(false);
            logic.run();
        });
    }
}