package sys.client;

import frame.OnLineInputScene;
import game.level.Human;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import tool.FlashText;
import tool.FrameFadeout;

public class ClientFormat extends StackPane {
    private TextField ipAddressField;
    private TextField passwordField;
    private Button deleteButton;
    private Button okButton;
    private double WIDTH = 300;
    private double HEIGHT = 200;

    private final FlashText waitText;
    private final Text titleText;
    private final Runnable filterOn;
    private final Runnable filterOff;
    private final Pane mainPane;

    public ClientFormat(FlashText waitText, Text titleText, Runnable filterOn, Runnable filterOff, Pane mainPane) {
        this.waitText = waitText;
        this.titleText = titleText;
        this.filterOn = filterOn;
        this.filterOff = filterOff;
        this.mainPane = mainPane;

        VBox format = new VBox();
        Rectangle background = new Rectangle(WIDTH, HEIGHT);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);

        Label titleLabel = new Label("ゲーム参加");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // 下線を引く
        Line underline = new Line(0, 0, 200, 0);
        underline.setStroke(Color.BLACK);
        underline.setStrokeWidth(2);

        ipAddressField = new TextField();
        passwordField = new TextField();
        deleteButton = new Button("Delete");
        okButton = new Button("OK");

        deleteButton.setOnMouseClicked(event -> textDelete());
        okButton.setOnMouseClicked(event -> connect());

        HBox ipRow = createFormRow("IPアドレス:", ipAddressField);
        ipRow.setAlignment(Pos.CENTER);
        HBox passwordRow = createFormRow("パスワード:", passwordField);
        passwordRow.setAlignment(Pos.CENTER);
        HBox buttonRow = createFormRow(deleteButton, okButton);
        buttonRow.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonRow, new Insets(10, 0, 0, 0));

        // タイトルと下線を追加
        format.setSpacing(10);
        format.setAlignment(Pos.CENTER);
        format.getChildren().addAll(titleLabel, underline, ipRow, passwordRow, buttonRow);

        getChildren().addAll(background, format);
        setVisible(false);
        setPrefSize(250, 100);
        setMinSize(200, 100); // 最小サイズを設定
    }

    private HBox createFormRow(String label, TextField textField) {
        HBox row = new HBox();
        row.setSpacing(10);
        Label titleLabel = new Label(label);
        titleLabel.setPrefWidth(80);
        row.getChildren().addAll(titleLabel, textField);
        return row;
    }

    private HBox createFormRow(Button button1, Button button2) {
        HBox row = new HBox();
        row.setSpacing(10);
        row.getChildren().addAll(button1, button2);
        return row;
    }

    private void connect() {
        waitText.setVisible(true);
        titleText.setVisible(false);
        filterOn.run();

        new Thread(new Runnable() {
            public void run() {
                try {
                    waitText.setVisible(true);
                    Thread.sleep(3000);
                    Client client = new Client(getIpAddress(), getPassword(), Human.getMyHuman());
                    
                    waitText.setText("マッチング");
                    Thread.sleep(3000);
                    
                    OnLineInputScene numberScene = new OnLineInputScene(client.getEnemyHuman(), client);
                    FrameFadeout.nextScene(mainPane, numberScene.getMainScene());
                } catch (Exception e) {
                    showlog("通信に失敗しました。", "対戦相手が見つかりませんでした。");
                    filterOff.run();
                } finally {
                    titleText.setVisible(true);
                    waitText.setVisible(false);
                    waitText.setText("待機中");
                    filterOff.run();
                }
            }
        }).start();
    }

    private void showlog(String title, String str) {
        Platform.runLater(() -> {
            Alert dialog = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            dialog.setHeaderText(title);
            dialog.setContentText(str);
            dialog.showAndWait();
        });
    }

    public void textDelete() {
        ipAddressField.setText("");
        passwordField.setText("");
    }

    public String getIpAddress() {
        return ipAddressField.getText();
    }

    public int getPassword() {
        return Integer.parseInt(passwordField.getText());
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getOkButton() {
        return okButton;
    }
}