package frame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import game.GameManager;
import game.GameSetter;
import game.level.Human;
import game.level.Level;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sys.Connect;
import tool.FrameFadeout;

@SuppressWarnings("unused")
public class OnLineGameScene extends GameScene {
    private boolean myTurn;
    private final Connect connect;

    public OnLineGameScene(Level level,int[] playerNumber, int[] enemyNumber, Connect connect, boolean myTurn) {
        super(level, playerNumber);
        this.myTurn = myTurn;
        this.connect = connect;

        this.disiableOff();
    }

    private void gameCommunicate() {
        new Thread(new Runnable() {
            public void run() {
                String line;
                try {
                    while (gm.isGame()) {
                        if ((line = connect.receive()) != null) {
                            command(line);
                        }
                    }
                } catch (IOException e) {
                    showlog("相手の通信が途切れました", "ゲームを中断してタイトル画面に戻ります。");
                    end();
                }
            }
        }).start();
    }

    private void command(String line) {
        if (line.startsWith("#START#")) {
            System.out.println("GAME START");
        }
        else if (line.startsWith("#YOUWIN#")) {
            super.showVictoryOfDefeat(GameSetter.GAMEWIN);
            end();
        } else if (line.startsWith("#YOULOSE#")) {
            super.showVictoryOfDefeat(GameSetter.GAMELOSE);
            end();
        } else {
            enemyAttack(line);
        }
    }

    @Override
    protected void timeLogic() {
        if (gm.isGame()) lose(); 
    }

    @Override
    protected void playerAttack() {
        // GameManagerクラスから、HBの結果を受け取る
        int[] playerNums = new int[MAX_SELECTED_NUMBERS];
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            playerNums[i] = Integer.valueOf(inputLabel[i].getText());
        }
        
        String result[] = gm.game(playerNums, true);
        playerBoard.addData(result);
        connect.send(String.join(",", result)); // 相手に入力した数値を送る
        
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            cancelButtonAction();
        }

        if (gm.getROUND()%2 == 0) {
            if (this.showVictoryOfDefeat(gm.judge())) 
                this.enemyTurn();
        } else {
            this.enemyTurn();
        }
    }

    @Override
    protected void enemySetup() {

    }

    protected void enemyAttack(String line) {
        gm.addROUND();
        String tmp[] = line.split(",");
        enemyBoard.addData(tmp); // 相手のBoardに記録

        String nums[] = tmp[0].split(" ");
        int[] ie = new int[MAX_SELECTED_NUMBERS];
        for (int i = 0; i < ie.length; i++) {
            ie[i] = Integer.parseInt(nums[i]);
        }

        gm.setInputEnemy(ie); // 敵の入力値を記録
        if (gm.getROUND()%2 == 0) {
            if (this.showVictoryOfDefeat(gm.judge())) 
                this.playerTurn();
        } else this.playerTurn();
    }

    private void win() {
        connect.send("#YOULOSE#");
        super.showVictoryOfDefeat(GameSetter.GAMEWIN);
        Human.setRate(50);
        end();
    }

    private void lose() {
        connect.send("#YOUWIN#");
        super.showVictoryOfDefeat(GameSetter.GAMELOSE);
        Human.setRate(-50);
        end();
    }

    private void end() {
        gm.gameStop();
        connect.close();
    }    

    @Override
    public void returnButtonAction() {
        if (showlog("対戦を終了し、タイトル画面へ戻りますか?")) {
            timer.stopTime();
            lose();
        }
    }

    @Override
    protected boolean showVictoryOfDefeat(int val) {
        if (GameSetter.GAMEWIN == val) {
            win();
        } else if (GameSetter.GAMELOSE == val) {
            lose();
        } else if (GameSetter.GAMEDRAW == val) {
            super.showVictoryOfDefeat(val);
        } else return true;
        return false;
    }

    @Override
    protected Scene getMainScene() {
        gameCommunicate();
        connect.send("#START#");
        if (myTurn) {  
            playerTurn();
        } else enemyTurn();
        return mainScene;
    }
}