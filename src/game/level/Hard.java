package game.level;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.ArrayList;

public class Hard extends Level{
    private final String myName = "CPU";
    private final String myRate = "強さ: Hard";
    private final int level = 2;
    private Set<String> used; 
    private int H = 0;
    private int B = 0;
    private int B1;
    private int B2;
    private int H1;
    private int H2;
    private int H3;
    private int B3;
    private int count = 2;
    private int cn1;
    private int cn2;
    private int cn3;
    
    public Hard(int MAX_SELECTED_NUMBERS) { 
        super(MAX_SELECTED_NUMBERS);
        used = new HashSet<>(); 
        
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
    public int level() {
        return level;
    }

    @Override
    public int[] getAttackNumber() { 
        int attack[] = new int[MAX_SELECTED_NUMBERS];


        Collections.shuffle(number);
        for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
            attack[i] = number.get(i);
        }
        
        String attackStr = Arrays.toString(attack); 
        System.out.println(attackStr);



        if(H+B == 0 || H+B == 3){
            while (used.contains(attackStr)) { // 条件を変更(作られて�?たら入�?)
                Collections.shuffle(number); 
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                
                attackStr = Arrays.toString(attack); 
            }
        }

        else if(count==2 || count==3){
            while (true) { // 条件を変更
                Collections.shuffle(number); 
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) && !attackStr.contains(String.valueOf(cn1)) && !attackStr.contains(String.valueOf(cn2))&& !attackStr.contains(String.valueOf(cn3))) break;
            }

        }

        

        else if(H == 2 && B == 0){
            while (true) { // 条件を変更
                Collections.shuffle(number); 
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) && attack[0] == H1 && attackStr.contains(String.valueOf(H3)) && !attackStr.contains(String.valueOf(H2))) break;
            }
        }

        else if(H ==1  && B == 0){
            while (true) { // 条件を変更
                Collections.shuffle(number); 
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) && attack[0] == H1 && !attackStr.contains(String.valueOf(H3)) && !attackStr.contains(String.valueOf(H2))) break;
            }
        }
        
        else if(B == 1){
            while(true){
                Collections.shuffle(number);
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) && attack[0] != B2 && attack[1] == B1) break;
            }
        }

        else if(B == 2){
            while(true){
                Collections.shuffle(number);
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) && attack[0] == B2 && attack[1] == B3 && !attackStr.contains(String.valueOf(B1))) break;
            }
        }

        else if(H == 1 && B == 1){
            while(true){
                Collections.shuffle(number);
                for (int i = 0; i < MAX_SELECTED_NUMBERS; i++) {
                    attack[i] = number.get(i);
                }
                attackStr = Arrays.toString(attack);
                if (!used.contains(attackStr) &&attack[0] == H3 && !attackStr.contains(String.valueOf(H2))) break;
            }
        }


        
        // hukumareteinai 
        used.add(attackStr); 
        count ++ ;
        return attack;
    }



    @Override
    public void feedback(int[] fb, int H, int B) {
        this.H = H;
        this.B = B;

        if(count<=3){
            cn1 = fb[0];
            cn2 = fb[1];
            cn3 = fb[2];
        }

       
        if(H+B == 3 && H != 3){
        ArrayList<Integer> temp1 = new ArrayList<>();
        for(int i = 0; i < MAX_SELECTED_NUMBERS; i++){
            temp1.add(fb[i]);
        }

        number = temp1;
       }

       if(H+B == 0){
        for(int i = 0;i<MAX_SELECTED_NUMBERS;i++){
            number.remove(Integer.valueOf(fb[i]));
        }
       }

       if(B == 1 ){
        B1 = fb[0];
        B2 = fb[1];
       }

       if(B == 2 ){
        B1 = fb[1];
        B2 = fb[2];
        B3 = fb[0];
       }

       if(H == 1){
        H1 = fb[0];
        H2 = fb[1];
        H3 = fb[2];
        
       }
       if(H == 2){
        H1 = fb[0];
        H2 = fb[1];
        H3 = fb[2];
       }
       
    }
}
