package frame;

import java.util.Optional;

import game.level.Human;
import game.level.Level;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sys.Connect;
import tool.FrameFadeout;

public class OnLineInputScene extends InputScene{
    private boolean enemyReady = false;
    private boolean playerReady = false;
    private boolean isRunning = true;
    private boolean myTurn;
    private int[] enemyNumber;
    private final Level level;
    private final Connect connect;

    public OnLineInputScene(Level level, Connect connect) {
        this.level = level;
        this.connect = connect;

        new Thread(new Runnable() {
            public void run() {
                try {
                    String line;
                    while (isRunning) {
                        if ((line = connect.receive()) != null) {
                            command(line);
                        }
                    }
                } catch (Exception e) {
                    getTopText().setText("通信が途切れました");
                    win();
                }
            }
        }).start();
    }

    private void command(String line) {
        if (line.startsWith("#READY#")) {
            line = line.substring("#READY#".length(), line.length());
            enemyNumber = new int[line.length()];
            for (int i = 0; i < enemyNumber.length; i++) {
                enemyNumber[i] =  Character.getNumericValue((line.charAt(i)));
            }
            level.setEnemyNum(enemyNumber);
            enemyReady = true;
            if (playerReady) {
                gameStart();
            }
        } else if (line.startsWith("#ENEMYLOSE#")) {
            win();
        }
    }

    private void gameStart() {
        isRunning = false;
        disiableOff();
        getTopText().setText("バトルスタート!");
        OnLineGameScene gameScene = new OnLineGameScene(level,getPlayerNumber(), enemyNumber, connect, myTurn);
        FrameFadeout.nextScene(mainPane, gameScene.getMainScene());
    }

    protected void win() {
        disiableOff();
        connect.close();
        timer.stopTime();
        Human.setRate(50);
        showlog("YOU WIN!!", "対戦に勝利しました!\n");
    }

    @Override
    protected void lose() {
        timer.stopTime();
        connect.send("#ENEMYLOSE#");
        connect.close();
        Human.setRate(-50);
    }

    @Override
    public void okButtonAction() {
        playerReady = true;
        timer.stopTime();
        disiableOff();
        
        String nums = "";
        int[] attack = getPlayerNumber();
        for (int i = 0; i < attack.length; i++) {
            nums += String.valueOf(attack[i]);
        }
        connect.send("#READY#" + nums);
        
        if (enemyReady) {
            myTurn = false;
            gameStart();
        }
        else {
            getTopText().setText("対戦相手の入力を待っています");
            myTurn = true;
        }
    }

    @Override
    protected boolean showlog(String str) {
        Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText(null);
        dialog.setContentText(str);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.get() == ButtonType.OK) {
            lose();
            return true;
        } else return false;
    }
}