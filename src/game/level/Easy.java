package game.level;

import java.util.Collections;

public class Easy extends Level{
    private final int level = 0;
    private final String myName = "CPU";
    private final String myRate = "強さ: Easy";

    public Easy(int MAX_SELECTED_NUMBERS) { 
        super(MAX_SELECTED_NUMBERS);
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public String getMyName() { 
        return myName;
    }

    @Override
    public String getMyRate() {
        return myRate;
    }

    @Override
    public int[] getAttackNumber() { 
        int attack[] = new int[MAX_SELECTED_NUMBERS];

        Collections.shuffle(number);
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            attack[i] = number.get(i);
        }

        return attack;
    }

    @Override
    public void feedback(int[] fb, int H, int B) {}
}