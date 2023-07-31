package frame;

import java.util.Optional;

import game.GameSetter;
import game.level.Human;
import game.level.Level;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tool.AbstractCalculator;
import tool.ButtonImg;
import tool.CountTimer;
import tool.FlashText;
import tool.FrameFadeout;
import tool.NewPopupLabel;

public class InputScene extends AbstractCalculator {
    private Level level;
    private int selectCount = 0;
    protected final int MAX_SELECTED_NUMBERS = 3;
    private int playerNumber[] = new int[MAX_SELECTED_NUMBERS];
    private NewPopupLabel[] inputLabel = new NewPopupLabel[MAX_SELECTED_NUMBERS];
    protected Scene mainScene;
    protected BorderPane mainPane = new BorderPane();
    protected CountTimer timer;
    private FlashText topText;

    public InputScene() {
        init();
    }

    public InputScene(Level level) {
        this.level = level;
        init();
    }

    private void init() {
        setStartLabel();
        mainPane.setTop(topText);
        BorderPane.setAlignment(topText, Pos.TOP_CENTER);

        mainPane.setCenter(setCenter());
        
        mainPane.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, null, null)));
        mainScene = new Scene(mainPane, GameSetter.STAGE_W, GameSetter.STAGE_H);
    }

    private void timeLogic() {
        lose();
    }

    private void setStartLabel() {
        topText = new FlashText("3つの数字を選んでください", 3, 2, 3);
        topText.setFontSize(50);
    }

    private VBox setCenter() {
        VBox centerBox = new VBox();
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(50);

        centerBox.getChildren().addAll(setSelectLabel(), setCalculatorAndOther());
        return centerBox;
    }

    private HBox setSelectLabel() {
        HBox labelBox = new HBox();
        labelBox.setAlignment(Pos.CENTER);
        labelBox.setSpacing(30);
        
        for (int i = 0; i < inputLabel.length; i++) {
            inputLabel[i] = new  NewPopupLabel("file:./lib/imgs/Popup"+(i+1)+".png");
            inputLabel[i].setLabelSize(20);
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

        timer = new CountTimer(60, this::timeLogic);
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

    @Override
    public void numberButtonAction(Button button) {
        if (selectCount < MAX_SELECTED_NUMBERS) {
            inputLabel[selectCount].setText(button.getText());
            inputLabel[selectCount].play(-50);
            playerNumber[selectCount] = Integer.parseInt(inputLabel[selectCount].getText());
            selectCount++;
            button.setDisable(true);
            if (selectCount == MAX_SELECTED_NUMBERS) getDecisionBtn().setVisible(true);
        }
    }    

    @Override
    public void cancelButtonAction() {
        if (0 < selectCount) {
            selectCount--;
            inputLabel[selectCount].reset();
            getNumberBtns()[Integer.valueOf(inputLabel[selectCount].getText())].setDisable(false);
            inputLabel[selectCount].setText("");
            getDecisionBtn().setVisible(false);
        }
    }

    @Override
    public void okButtonAction() {
        timer.stopTime();
        disiableOff();

        mainScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
        GameScene gameScene = new GameScene(level, playerNumber);
        FrameFadeout.nextScene(mainPane, gameScene.getMainScene());
    }

    public int[] getPlayerNumber() {
        return playerNumber;
    }

    public FlashText getTopText() {
        return topText;
    }

    protected void lose() {
        showlog("You Lose...","対戦に敗北しました。");
        Human.setRecord(level.level(), GameSetter.GAMELOSE);
    }

    protected void showlog(String title, String str) {
        timer.stopTime();
        Platform.runLater(() -> {
            Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            dialog.setHeaderText(title);
            dialog.setContentText(str);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.get() == ButtonType.OK) {
                mainScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
                FrameFadeout.nextScene(mainPane, TitleFrame.getMainScene());
            }
        });
    }

    protected boolean showlog(String str) {
        Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText(null);
        dialog.setContentText(str);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void returnButtonAction() {
        if (showlog("対戦を終了し、タイトル画面へ戻りますか?")) {
            lose();
        }
    }

    public void filterScene() {
        mainScene.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> event.consume());
    }

    public Scene getMainScene() {
        timer.startTime();
        return mainScene;
    }
}