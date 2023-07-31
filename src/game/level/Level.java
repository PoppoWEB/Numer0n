package game.level;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Level {
    protected ArrayList<Integer> number = new ArrayList<>();
    protected int MAX_SELECTED_NUMBERS;
    private int enemyNum[];

    public Level() {
        super();
    }

    public Level(int MAX_SELECTED_NUMBERS) {
        this.MAX_SELECTED_NUMBERS = MAX_SELECTED_NUMBERS;
        enemyNum = new int[this.MAX_SELECTED_NUMBERS];
        for (int i = 0; i < 10; i++) {
            number.add(i);
        }

        Collections.shuffle(number);
        // Enemy側のナンバーを三つ設定
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            enemyNum[i] = number.get(i);
        }
    }

    // CPUが定めた3桁のナンバー
    public int[] getEnemyNum() { 
        return enemyNum;
    }

    public void setEnemyNum(int[] enemyNum) {
        this.enemyNum = enemyNum;
    }

    public abstract int level();
    public abstract int[] getAttackNumber();
    public abstract void feedback(int[] nums, int H, int B);
    public abstract String getMyName();
    public abstract String getMyRate();
}