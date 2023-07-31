package tool;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NewPopupLabel extends NewLabel {
    private ImageView imageView;
    private StackPane imageContainer;
    private double originalY; // 画像の元のy座標を保持する変数
    
    public NewPopupLabel(String path) {
        
        imageView = new ImageView(path);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(WIDTH); // 枠に収めるために幅を調整
        imageView.setFitHeight(HEIGHT);
        
        imageContainer = new StackPane(imageView);
        getChildren().add(imageContainer);
    }
    
    public void play(double targetY) {
        if (imageView.getImage() != null) {
            originalY = imageView.getTranslateY(); // 元のy座標を保存
            
            // 画像をアニメーションさせるTranslateTransitionを作成
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.05), imageView);
            tt.setFromY(originalY);
            tt.setToY(targetY - HEIGHT / 2); // 指定したy座標まで画像を移動
            tt.play();
        }
    }
    
    public void reset() {
        if (imageView.getImage() != null) {
            // 画像を元の位置にアニメーションさせるTranslateTransitionを作成
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.05), imageView);
            tt.setFromY(imageView.getTranslateY());
            tt.setToY(originalY); // 画像を元のy座標に戻す
            tt.play();
        }
    }
}
