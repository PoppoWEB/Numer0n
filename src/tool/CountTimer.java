package tool;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class CountTimer extends StackPane{
    private final int time;
    private final Label timeLabel;
    private final String TIMER_FILE_PATH = "file:./lib/imgs/Timer.png";

    private int elapsedTime;
    private boolean isRunning;
    private Thread timeThread;
    private Runnable Logic;

    public CountTimer(int time, Runnable Logic) {
        this.time = time;
        this.elapsedTime = time;
        this.Logic = Logic;

        timeLabel = new Label(String.valueOf(time));
        timeLabel.setFont(Font.font(24));

        Image backgroundImage = new Image(TIMER_FILE_PATH);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(80);
        backgroundImageView.setFitHeight(80);

        getChildren().addAll(backgroundImageView, timeLabel);
    }

    public void startTime() {
        isRunning = true;
        if (timeThread == null || !timeThread.isAlive()) {
            timeThread = new Thread(() -> {
                try {
                    timeReStart();
                    while (isRunning()) {
                        Thread.sleep(1000);
                        System.out.println("tick");
                        if (updateTime() == 0) {
                            Logic.run();
                            break;
                        }
                    }
                } catch (Exception e) {
        
                }
            });
            timeThread.start();
        }
    }

    public void stopTime() {
        if (timeThread != null && timeThread.isAlive()) {
            timeThread.interrupt();
            timeThread = null;
        }
    }

    public void reset() {
        elapsedTime = this.time;
        updateTimeLabel();
    }

    public int updateTime() {
        elapsedTime--;
        Platform.runLater(this::updateTimeLabel);
        return elapsedTime;
    }

    public void updateTimeLabel() {
        Platform.runLater(() -> {
            timeLabel.setText(String.valueOf(elapsedTime));
        });
    }

    public void timeReStart() {
        isRunning = true;
        reset();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
