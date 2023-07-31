package frame.title;

import frame.InputScene;
import game.GameSetter;
import game.level.Easy;
import game.level.Hard;
import game.level.Normal;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sys.client.ClientFormat;
import sys.server.ServerFormat;
import tool.ButtonImg;
import tool.FlashText;
import tool.FrameFadeout;

public class CenterComponentTitle extends VBox{
    private Text titleText = new Text("NUMER0N");
    private FlashText waitText = new FlashText("待機中", 2, 1, 2);
    private final String LEVEL_NAMES[]      = {"Easy", "Normal", "Hard"};

    private Button levelBtns[];
    private final Button singleBtn          = new Button("シングルプレイ");   
    private final Button friendBtn          = new Button("フレンドマッチ");
    private final Button serverBtn          = new Button("ゲーム開催");
    private final Button clientBtn          = new Button("ゲーム参加");
    private final static HBox   levelBox = new HBox();
    private final static HBox   socketBox    = new HBox();
    private final static ImageView backBtn  = ButtonImg.makeImgButton(GameSetter.RETURN_FILE_PATH);

    private final Runnable filterOn;
    private final Runnable filterOff;
    private final BorderPane mainPane;

    private final ServerFormat sf;
    private final ClientFormat cf;
    
    public CenterComponentTitle(Runnable filterOn, Runnable filterOff, BorderPane mainPane) {
        this.filterOn = filterOn;
        this.filterOff = filterOff;
        this.mainPane = mainPane;

        sf = new ServerFormat(waitText, titleText, filterOn, filterOff, mainPane);
        cf = new ClientFormat(waitText, titleText, filterOn, filterOff, mainPane);

        setAlignment(Pos.TOP_CENTER);
        getChildren().addAll(setText(), setButton(), setConnect());
    }

    private Pane setText() {
        StackPane centerPanel = new StackPane();
        centerPanel.setAlignment(Pos.CENTER);
        titleText.setFont(Font.font("Arial", 70));
        titleText.setFill(Color.RED);

        waitText.setFont(Font.font("Arial", 70));
        waitText.setFill(Color.RED);
        waitText.setVisible(false);
        
        centerPanel.getChildren().addAll(titleText, waitText);
        
        return centerPanel;
    }

    private StackPane setButton() {
        StackPane buttonPane = new StackPane();
        HBox typeBox = new HBox();
        typeBox.setAlignment(Pos.CENTER);

        singleBtn.setPrefHeight(30);
        singleBtn.setPrefWidth(120);
        singleBtn.setOnMouseClicked(event -> pushButton(singleBtn));
        
        friendBtn.setPrefHeight(30);
        friendBtn.setPrefWidth(120);
        friendBtn.setOnMouseClicked(event -> pushButton(friendBtn));

        typeBox.setSpacing(40);
        typeBox.getChildren().addAll(singleBtn, friendBtn);

        levelBtns = new Button[LEVEL_NAMES.length];
        for (int i = 0; i < levelBtns.length; i++) {
            int index = i;
            levelBtns[i] = new Button(LEVEL_NAMES[i]);
            levelBtns[i].setPrefWidth(100);
            levelBtns[i].setOnMouseClicked(event -> pushButton(levelBtns[index]));

            levelBox.getChildren().add(levelBtns[i]);
        }
        levelBox.setAlignment(Pos.CENTER);
        levelBox.setSpacing(10);
        levelBox.setVisible(false);

        serverBtn.setPrefHeight(30);
        serverBtn.setPrefWidth(120);
        serverBtn.setOnMouseClicked(event -> pushButton(serverBtn));
        
        clientBtn.setPrefHeight(30);
        clientBtn.setPrefWidth(120);
        clientBtn.setOnMouseClicked(event -> pushButton(clientBtn));
        clientBtn.setVisible(true);
        
        socketBox.setAlignment(Pos.CENTER);
        socketBox.setSpacing(40);
        socketBox.getChildren().addAll(serverBtn, clientBtn);
        socketBox.setVisible(false);

        setBackButton();

        buttonPane.setTranslateY(280);
        buttonPane.getChildren().addAll(typeBox, levelBox, socketBox, backBtn);
        return buttonPane;
    }
    
    private void setBackButton() {
        backBtn.setTranslateY(80);
        backBtn.setOnMouseClicked(event -> pushButton(backBtn));
        backBtn.setVisible(false);
    }

    private Pane setConnect() {
        StackPane panel = new StackPane();
        
        panel.setTranslateY(100);
        panel.getChildren().addAll(setClient(), setServer());

        return panel;
    }

    private ClientFormat setClient() {
        cf.setAlignment(Pos.CENTER);

        cf.setVisible(false);
        
        return cf;
    }

    private ServerFormat setServer() {
        sf.setAlignment(Pos.CENTER);

        sf.setVisible(false);
        
        return sf;
    }

    private void pushButton(Object button) {
        if (button.equals(singleBtn)) {
            switchSingleVisible();
        } else if (button.equals(friendBtn)) {
            switchFriendVisible();
        } else if (button.equals(backBtn)) {
            if (levelBox.isVisible()) switchSingleVisible();
            else if (socketBox.isVisible()) switchFriendVisible();
            else if (sf.isVisible()) switchServerVisiable();
            else if (cf.isVisible()) switchClientVisiable();
        } else if (button.equals(serverBtn)) {
            switchServerVisiable();
        } else if (button.equals(clientBtn)) {
            switchClientVisiable();
        } else {
            filterOn.run();
            if (button.equals(levelBtns[0])) {
                InputScene numberScene = new InputScene(new Easy(3));
                FrameFadeout.nextScene(mainPane ,numberScene.getMainScene());
            } else if (button.equals(levelBtns[1])) {
                InputScene numberScene = new InputScene(new Normal(3));
                FrameFadeout.nextScene(mainPane ,numberScene.getMainScene());
            } else if (button.equals(levelBtns[2])) {
                InputScene numberScene = new InputScene(new Hard(3));
                FrameFadeout.nextScene(mainPane ,numberScene.getMainScene());
            }  
            filterOff.run();
        }
    }
    
    private void switchSingleVisible() {
        singleBtn.setVisible(!singleBtn.isVisible());
        friendBtn.setVisible(!friendBtn.isVisible());
        levelBox.setVisible(!levelBox.isVisible());
        backBtn.setVisible(!backBtn.isVisible());
    }

    private void switchFriendVisible() {
        singleBtn.setVisible(!singleBtn.isVisible());
        friendBtn.setVisible(!friendBtn.isVisible());
        socketBox.setVisible(!socketBox.isVisible());
        backBtn.setVisible(!backBtn.isVisible());
    }

    private void switchServerVisiable() {
        socketBox.setVisible(!socketBox.isVisible());
        sf.setVisible(!sf.isVisible());
    }

    private void switchClientVisiable() {
        socketBox.setVisible(!socketBox.isVisible());
        cf.setVisible(!cf.isVisible());
    }
}