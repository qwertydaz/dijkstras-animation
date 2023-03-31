package src.main.java.gui.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

import java.util.List;

public class Table
{
	private ScrollPane tableScrollPane;
	private final Controller controller;
	private TableView<String[]> lTable;

	public Table(Controller controller)
	{
		this.controller = controller;

		setupTablePane();
		setupTableScrollPane();
	}

	private void setupTablePane()
	{
		Pane tablePane = new Pane();

		tablePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		lTable = new TableView<>();

		tablePane.getChildren().add(lTable);
	}

	private void setupTableScrollPane()
	{
		tableScrollPane = new ScrollPane();
		tableScrollPane.setFitToWidth(true);
		tableScrollPane.setFitToHeight(true);
		tableScrollPane.setContent(lTable);
	}

	public ScrollPane getTablePane()
	{
		return tableScrollPane;
	}

	public void clearAll()
	{
		if (!lTable.getColumns().isEmpty())
		{
			for (int i = 0; i < lTable.getColumns().size(); i++)
			{
				lTable.getColumns().clear();
			}
		}

		if (!lTable.getItems().isEmpty())
		{
			for ( int i = 0; i < lTable.getItems().size(); i++)
			{
				lTable.getItems().clear();
			}
		}
	}

	public void fillTable()
	{
		clearAll();

		List<String[]> results = controller.runDijkstra();

		String[] headers = results.get(0);

		for (int i = 0; i < headers.length; i++)
		{
			TableColumn<String[], String> column = new TableColumn<>(headers[i]);
			final int colIndex = i;

			column.setCellValueFactory(param ->
			{
				String[] values = param.getValue();

				if (colIndex < values.length)
				{
					return new SimpleStringProperty(values[colIndex]);
				}
				else
				{
					return new SimpleStringProperty("");
				}
			});

			lTable.getColumns().add(column);
		}

		lTable.setItems(FXCollections.observableArrayList(results.subList(1, results.size())));
	}
}
