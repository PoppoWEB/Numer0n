package game;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@SuppressWarnings("all")
public class GameTable extends TableView<GameTable.Data> {
    private ObservableList<Data> data;

    public GameTable(String[] dataNames) {
        TableColumn<Data, String>[] columns = new TableColumn[dataNames.length]; // 列の定義
        data = FXCollections.observableArrayList();

        for (int i = 0; i < dataNames.length; i++) {
            final int columnIndex = i;
            columns[i] = new TableColumn<>(dataNames[i]);
            columns[i].setCellFactory(column -> new CenteredTableCell<>());
            columns[i].setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber(columnIndex)));
            columns[i].setSortable(false);

            getColumns().add(columns[i]);
        }
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setEditable(false);
        setItems(data);
    }

    public void addData(String[] newData) {
        Data tmp = new Data(newData);
        data.add(tmp);
    }

    public void setPrefWidth(int columnIndex, int size) {
        getColumns().get(columnIndex).setPrefWidth(size);
        double sum = 0;
        for (TableColumn<Data, ?> column : getColumns()) {
            sum += column.getPrefWidth();
        }
        setPrefWidth(sum);
    }

    // セルのスタイルを設定するTableCellのサブクラス
    private static class CenteredTableCell<S, T> extends TableCell<S, T> {
        public CenteredTableCell() {
            setAlignment(Pos.CENTER);
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.toString());
            }
        }
    }

    public static class Data {
        private final String[] numbers;

        public Data(String[] numbers) {
            this.numbers = numbers;
        }

        public String getNumber(int index) {
            if (index >= 0 && index < numbers.length) {
                return numbers[index];
            } else {
                return null;
            }
        }
    }
}