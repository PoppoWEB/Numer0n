package tool;


import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonImg {

    public static ImageView makeImgButton(String Path) {
        ImageView ViewBtn = new ImageView(new Image(Path));

        // クリック時のフィルタ効果を設定
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.2); // 明度を少し下げる
        ViewBtn.setOnMousePressed(event -> ViewBtn.setEffect(colorAdjust));
        ViewBtn.setOnMouseReleased(event -> ViewBtn.setEffect(null));

        return ViewBtn;
    }
}
