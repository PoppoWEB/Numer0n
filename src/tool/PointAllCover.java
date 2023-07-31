package tool;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PointAllCover extends StackPane {
    private final VBox vbox;
    private final Rectangle border;
    private final ChangeListener<Bounds> layoutBoundsListener;

    public PointAllCover(VBox vbox) {
        this.vbox = vbox;
        border = new Rectangle();
        border.setFill(Color.WHITE);
        border.setStroke(Color.BLACK);
        border.getStrokeDashArray().addAll(5d, 5d);
        getChildren().addAll(border, vbox);

        // レイアウトの変更リスナーを作成
        layoutBoundsListener = (observable, oldBounds, newBounds) -> {
            Platform.runLater(this::updateLayout);
        };
        // レイアウトの変更リスナーを追加
        vbox.layoutBoundsProperty().addListener(layoutBoundsListener);
        updateLayout();
    }

    private void updateLayout() {
        // VBox内の全てのコンポーネントを調べて、最大の幅を取得
        double maxChildWidth = 0;
        for (Node child : vbox.getChildren()) {
            maxChildWidth = Math.max(maxChildWidth, child.getBoundsInLocal().getWidth());
        }

        // Rectangleの幅を最大の幅に合わせる
        border.setWidth(maxChildWidth+100);
        border.setHeight(vbox.getHeight());
        border.setTranslateX(vbox.getLayoutX());
        border.setTranslateY(vbox.getLayoutY());
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        // レイアウトの変更リスナーが不要になったら削除
        vbox.layoutBoundsProperty().removeListener(layoutBoundsListener);
    }
}