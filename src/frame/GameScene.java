package frame;
import java.util.Optional;
import java.util.Random;

import frame.game.CenterTexts;
import frame.game.SidePlayBoard;
import game.GameManager;
import game.GameSetter;
import game.level.Human;
import game.level.Level;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tool.AbstractCalculator;
import tool.ButtonImg;
import tool.CountTimer;
import tool.FrameFadeout;
import tool.NewLabel;

public class GameScene extends AbstractCalculator {
    protected Level level;
    protected GameManager gm;

    protected int playerNumber[];
    protected int selectCount = 0;
    final int MAX_SELECTED_NUMBERS = 3;
    final String boardName[] = {"推論","H","B"};

    protected Scene mainScene;
    protected HBox mainPane = new HBox();

    protected NewLabel[] inputLabel = new NewLabel[MAX_SELECTED_NUMBERS];

    protected CountTimer timer;
    protected CenterTexts textTools;

    protected SidePlayBoard playerBoard;
    protected SidePlayBoard enemyBoard;

    public GameScene(Level level, int[] playerNumber) {
        this.level = level;
        this.playerNumber = playerNumber;

        gm = new GameManager(playerNumber, level.getEnemyNum(), MAX_SELECTED_NUMBERS);
        init();
    }

    private void init() {
        playerBoard = new SidePlayBoard(boardName, playerNumber);     
        playerBoard.setTextFont(20);
        playerBoard.setLabelSize(20);
        playerBoard.setBoardPrefWidth(1, 40);
        playerBoard.setBoardPrefWidth(2, 40);
        playerBoard.setPadding(new Insets(5));
        
        enemyBoard = new SidePlayBoard(boardName, level.getEnemyNum(), level);
        enemyBoard.setTextFont(20);
        enemyBoard.setLabelSize(20);
        enemyBoard.setBoardPrefWidth(1, 40);
        enemyBoard.setBoardPrefWidth(2, 40);
        enemyBoard.setPadding(new Insets(5));
        enemyBoard.hide("?");
        enemyBoard.setFontColor(Color.RED);

        mainPane.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));
        mainPane.setAlignment(Pos.CENTER);

        mainPane.getChildren().addAll(playerBoard, centerComponents(), enemyBoard);
        mainScene = new Scene(mainPane, GameSetter.STAGE_W, GameSetter.STAGE_H);
        this.disiableOff();
    }

    private VBox centerComponents() {
        VBox center = new VBox();
        textTools = new CenterTexts();
        center.getChildren().addAll(textTools, setSelectLabel(), setCalculatorAndOther());

        return center;
    }

    private HBox setSelectLabel() {
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setSpacing(30);

        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            inputLabel[i] = new NewLabel("");
            inputLabel[i].setLabelSize(15);
            inputLabel[i].setWidth(40);
            inputLabel[i].setHight(40);
            labelBox.getChildren().add(inputLabel[i]);
        }

        return labelBox;
    }

    private HBox setCalculatorAndOther() {
        GridPane calcPane = createNumberGridPane();
        calcPane.setScaleX(1.8);
        calcPane.setScaleY(1.8);
        getDecisionBtn().setVisible(false);

        VBox otherBox = new VBox();
        otherBox.setSpacing(100);
        ImageView returnView = ButtonImg.makeImgButton(GameSetter.RETURN_FILE_PATH);
        returnView.setOnMouseClicked(event -> returnButtonAction());

        timer = new CountTimer(30, this::timeLogic);
        timer.setTranslateX(-10);
        otherBox.getChildren().addAll(timer, returnView);

        HBox calcAndOtherBox = new HBox();
        calcAndOtherBox.setSpacing(50);
        calcAndOtherBox.setTranslateY(50);
        calcAndOtherBox.setPadding(new Insets(0, 100, 0, 0));
        calcAndOtherBox.getChildren().addAll(otherBox, calcPane);
        calcAndOtherBox.setAlignment(Pos.CENTER);

        return calcAndOtherBox;
    }

    protected void timeLogic() {
        if (gm.isGame()) showVictoryOfDefeat(GameSetter.GAMELOSE);
    }

    protected void playerAttack() {
        // GameManagerクラスから、HBの結果を受け取る
        int[] playerNums = new int[MAX_SELECTED_NUMBERS];
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            playerNums[i] = Integer.valueOf(inputLabel[i].getText());
        }
        
        String result[] = gm.game(playerNums, true);
        playerBoard.addData(result);
        
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            cancelButtonAction();
        }

        if (gm.getROUND()%2==0) {
            if (showVictoryOfDefeat(gm.judge())) 
                enemyTurn();
        } else enemyTurn();
    }

    protected void playerSetup() {
        this.disiableOn();
        timer.startTime();
    }

    protected void playerTurn() {
        // 貴方のターンアニメーション
        textTools.plyerPlay(this::playerSetup);
        if (gm.getROUND()%2 == 0) {
            Platform.runLater(() -> {
                textTools.setRound(String.valueOf((gm.getROUND()/2)+1));
            });
        }   
    }

    protected void enemyAttack() {
        // 相手のターンにし、結果をEnemyBoardに表示
        int[] enemyNums = level.getAttackNumber();
        String result[] = gm.game(enemyNums, false);
        enemyBoard.addData(result);
        level.feedback(enemyNums, Integer.parseInt(result[1]), Integer.parseInt(result[2]));
        if (gm.getROUND()%2==0) {
            if (showVictoryOfDefeat(gm.judge()))
                playerTurn();
        } else playerTurn();
    }

    protected void enemySetup() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3 + new Random().nextInt(2)));
        delay.setOnFinished(event -> {
            enemyAttack();
        });
        delay.play();
    }

    protected void enemyTurn() {
        // 相手のターンアニメーション
        this.disiableOff();
        textTools.enemyPlay(this::enemySetup);

        if (gm.getROUND()%2 == 0) {
            Platform.runLater(() -> {
                textTools.setRound(String.valueOf((gm.getROUND()/2)+1));
            });
        }
    }

    protected boolean showVictoryOfDefeat(int val) {
        if (GameSetter.GAMEWIN == val) {
            enemyBoard.open();
            showlog("You Win!!", "対戦に勝利しました!\n");
            Human.setRecord(level.level(), GameSetter.GAMEWIN);
        } else if (GameSetter.GAMELOSE == val) {
            enemyBoard.open();
            showlog("You Lose...", "対戦に敗北しました!\n");
            Human.setRecord(level.level(), GameSetter.GAMELOSE);
        } else if (GameSetter.GAMEDRAW == val) {
            enemyBoard.open();
            showlog("Draw", "引き分けです\n");
            Human.setRecord(level.level(), GameSetter.GAMEDRAW);
        } else return true;
        return false;
    }

    protected void showlog(String title, String str) {
        timer.stopTime();
        Platform.runLater(() -> {
            Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            dialog.setHeaderText(title);
            dialog.setContentText(str);
            dialog.showAndWait();
            mainScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
            FrameFadeout.nextScene(mainPane, TitleFrame.getMainScene());
        });
    }

    protected boolean showlog(String str) {
        Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText(null);
        dialog.setContentText(str);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == ButtonType.OK;
    }

    @Override
    public void numberButtonAction(Button button) {
        if (selectCount < MAX_SELECTED_NUMBERS) {
            inputLabel[selectCount++].setText(button.getText());
            button.setDisable(true);
            if (selectCount == MAX_SELECTED_NUMBERS) getDecisionBtn().setVisible(true);
        }
    }

    @Override
    public void cancelButtonAction() {
        if (0 < selectCount) {
            selectCount--;
            getNumberBtns()[Integer.valueOf(inputLabel[selectCount].getText())].setDisable(false);
            inputLabel[selectCount].setText("");
            getDecisionBtn().setVisible(false);
        }
    }
    
    @Override
    public void okButtonAction() {
        timer.stopTime();
        playerAttack();
    }

    public void returnButtonAction() {
        if (showlog("対戦を終了し、タイトル画面へ戻りますか?")) {
            gm.gameStop();
            showVictoryOfDefeat(GameSetter.GAMELOSE);
        }
    }

    protected Scene getMainScene() {
        if (gm.getFirstSecond()) {
            playerTurn();
        } else {
            enemyTurn();
        }
        return mainScene;
    }
}