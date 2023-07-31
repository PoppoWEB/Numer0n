package game.level;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Human extends Level implements Serializable{
    private static final long serialVersionUID = 123456789L; // 任意の値を指定
    // Save用
    private String name;
    private String rate;
    private int record[][] = new int[4][3];
    
    // Getter用
    private static SimpleStringProperty globalName = new SimpleStringProperty();
    private static SimpleStringProperty globalRate = new SimpleStringProperty();
    private static SimpleIntegerProperty globalRecord[][] = new SimpleIntegerProperty[4][3];
    private static final int level = 3;
    private static final String filePath = "./lib/data/myplayer_data.dat";

    public Human(String name, String rate, int record[][]) {
        this.name = name;
        this.rate = rate;
        this.record = record;

        globalName.set(name);
        globalRate.set(rate);
        globalRecord = getRecord(record);
    }
    
    public static void saveToFile() {
        saveToFile(getMyHuman());
    }

    // クラスをファイルにセーブするメソッド
    public static void saveToFile(Human obj) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream( new FileOutputStream(filePath)))) {
            out.writeObject(obj);
            System.out.println("Saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ファイルからクラスをロードするメソッド
    public static Human loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            // ファイルが存在しない場合は新しく作成
            Human newObj = new Human("NoName", "1500", new int[4][3]);
            saveToFile(newObj);
            return newObj;
        }

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            Human obj = (Human) in.readObject();
            System.out.println("Loaded from file: " + filePath);
            globalName = new SimpleStringProperty(obj.getMyName());
            globalRate = new SimpleStringProperty(obj.getMyRate());
            globalRecord = Human.getRecord(obj.getMyRecord());
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Human getMyHuman() {
        return new Human(globalName.get(), globalRate.get(), getRecord(globalRecord));
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public int[] getAttackNumber() {
        return null;
    }
    
    @Override
    public void feedback(int[] nums, int H, int B) {
        // NOTHING
    }

    @Override
    public String getMyName() {
        return name;
    }

    @Override
    public String getMyRate() {
        return rate;
    }

    public int[][] getMyRecord() {
        return record;
    }

    public static String getName() {
        return globalName.get();
    }

    public static String getRate() {
        return globalRate.get();
    }

    private static int[][] getRecord(SimpleIntegerProperty srecord[][]) {
        int record[][] = new int[4][3];

        for (int i = 0; i < record.length; i++) {
            for (int j = 0; j < record[i].length; j++) {
                record[i][j] = srecord[i][j].get();
            }
        }

        return record;
    }

    private static SimpleIntegerProperty[][] getRecord(int record[][]) {
        SimpleIntegerProperty srecord[][] = new SimpleIntegerProperty[4][3];

        for (int i = 0; i < srecord.length; i++) {
            for (int j = 0; j < srecord[i].length; j++) {
                srecord[i][j] = new SimpleIntegerProperty(record[i][j]);
            }
        }

        return srecord;
    }

    public static String getRecord(int level) {
        if (level > Human.level) return null;
        int win = globalRecord[level][0].get();
        int lose = globalRecord[level][1].get();
        int draw = globalRecord[level][2].get();
        return win+"勝 "+ lose +"敗 "+ draw +"分";
    }

    public String getRecord() {
        int win = record[level][0];
        int lose = record[level][1];
        int draw = record[level][2];
        return win+"勝 "+ lose +"敗 "+ draw +"分";
    }

    public static void setName(String name) {
        globalName.set(name);
        // saveToFile(Human.getMyHuman());
    }

    public static void setRate(int newRate) {
        int nowRate = Integer.parseInt(globalRate.get());
        int res = 0;
        if (nowRate + newRate <= 0) {
            res = 0;
        } else if (nowRate + newRate >= 9999) {
            res = 9999;
        } else {
            res = nowRate + newRate;
        }
        globalRate.set(String.valueOf(res));
        // saveToFile(Human.getMyHuman());
    }

    public static void setRecord(int level, int record) {
        globalRecord[level][record].set(globalRecord[level][record].get()+1);
        // saveToFile(Human.getMyHuman());
    }

    public static SimpleStringProperty rateProperty() {
        return globalRate;
    }

    public static SimpleIntegerProperty recordProperty(int level, int recordType) {
        return globalRecord[level][recordType];
    }
}