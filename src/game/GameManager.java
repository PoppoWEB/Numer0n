package game;

import java.util.Random;

public class GameManager {
    private boolean isgame = true;
    private int MAX_SELECTED_NUMBERS;
    private int ROUND = 0;

    private int[] player;
    private int[] inputPlayer;

    private int[] enemy;
    private int[] inputEnemy;

    public GameManager(int[] player, int[] enemy, int MAX_SELECTED_NUMBERS) {
        this.player = player;
        this.enemy = enemy;
        this.MAX_SELECTED_NUMBERS = MAX_SELECTED_NUMBERS;
    }

    private String[] culcHandB(int numbers[], int compNumbers[]) {
        String result[] = new String[MAX_SELECTED_NUMBERS];
        // 推論
        result[0] = "";
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            result[0] += String.valueOf(numbers[i]) + " ";
        }

        // HIT
        int sum = 0;
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            if (numbers[i] == compNumbers[i]) sum++;
        }
        result[1] = String.valueOf(sum);

        // BLOW
        sum = 0;
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            for (int j = 0; j < MAX_SELECTED_NUMBERS; j++) {
                if (numbers[i] == compNumbers[j] && i != j) {
                    sum++;
                    break;
                }
            }
        }
        result[2] = String.valueOf(sum);
        return result;
    }

    public int judge() {
        boolean p_flg = true;
        boolean e_flg = true;

        for (int i = 0; i < inputPlayer.length; i++) {
            if (inputPlayer[i] != enemy[i]) {
                p_flg = false;
                break;
            }
        }

        for (int i = 0; i < inputEnemy.length; i++) {
            if (inputEnemy[i] != player[i]) {
                e_flg = false;
                break;
            }
        }
        
        if (p_flg == true && e_flg == true) return GameSetter.GAMEDRAW;
        else if (p_flg == true && e_flg == false) return GameSetter.GAMEWIN;
        else if (p_flg == false && e_flg == true) return GameSetter.GAMELOSE;
        return GameSetter.GAMECONT; 
    }

    public String[] game(int numbers[], boolean flg) {
        ROUND++;
        if (flg) { // player
            this.inputPlayer = numbers;
            return culcHandB(numbers, enemy);
        }
        this.inputEnemy = numbers;
        return culcHandB(numbers, player);
    }

    public boolean getFirstSecond() {
        return new Random(System.currentTimeMillis()).nextBoolean();
    }

    public int getROUND() {
        return ROUND;
    }   

    public void addROUND() {
        ROUND++;
    }

    public void setInputEnemy(int[] ie) {
        this.inputEnemy = ie;
    }

    public boolean isGame() {
        return isgame;
    }

    public void gameStop() {
        isgame = false;
    }
}