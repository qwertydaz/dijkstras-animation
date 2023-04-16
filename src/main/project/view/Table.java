package project.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Table
{
	private ScrollPane tableScrollPane;
	private TableView<String[]> lTable;

	public Table()
	{
		setupTablePane();
		setupTableScrollPane();
	}

	private void setupTablePane()
	{
		Pane tablePane = new Pane();

		tablePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		lTable = new TableView<>();
		ObservableList<String[]> data = FXCollections.observableArrayList();
		lTable.setItems(data);

		tablePane.getChildren().add(lTable);
	}

	private void setupTableScrollPane()
	{
		tableScrollPane = new ScrollPane();
		tableScrollPane.setFitToWidth(true);
		tableScrollPane.setFitToHeight(true);
		tableScrollPane.setContent(lTable);
	}

	public ScrollPane getPane()
	{
		return tableScrollPane;
	}

	public void clearAll()
	{
		lTable.getItems().clear();
		lTable.getColumns().clear();
	}

	public void addRow(String[] row)
	{
		lTable.getItems().add(row);
	}

	public void removeLastRow()
	{
		lTable.getItems().remove(lTable.getItems().size() - 1);
	}

	public void setColumnNames(String[] columnNames)
	{
		for (int i = 0; i < columnNames.length; i++)
		{
			TableColumn<String[], String> column = new TableColumn<>(columnNames[i]);
			final int columnIndex = i;
			column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()[columnIndex]));
			lTable.getColumns().add(column);
		}
	}
}
