package game;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameSetter {
    public static final int STAGE_H = 600;
    public static final int STAGE_W = 950;
    public static final int MAX_NAME_LENGTH = 10;
    public static final Duration FADE_OUT_TIME = Duration.seconds(2);
    public static final int GAMEWIN = 0;
    public static final int GAMELOSE = 1;
    public static final int GAMEDRAW = 2;
    public static final int GAMECONT = 3;
    public static final boolean PLAYER = true;
    public static final boolean ENEMY = false;
    public static Stage primaryStage = null;

    public static final String ICON_FILE_PATH = "file:./lib/imgs/Title.png";
    public static final String SETTING_FILE_PATH = "file:./lib/imgs/Setting.png";
    public static final String RETURN_FILE_PATH = "file:./lib/imgs/Return.png";
    public static final String FIGHT_FILE_PATH = "file:./lib/imgs/Fight_log.png";
}