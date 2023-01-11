package src.main.java.gui.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table
{
	private Pane tablePane;
	private final Controller controller;
	private TableView<String[]> lTable;

	public Table(Controller controller)
	{
		this.controller = controller;

		setupTablePane();
	}

	private void setupTablePane()
	{
		tablePane = new Pane();

		tablePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		lTable = new TableView<>();
		lTable.setStyle("-fx-font-size: 20;");

		tablePane.getChildren().add(lTable);
	}

	public Pane getTablePane()
	{
		return tablePane;
	}

	public void fillTable()
	{
		// TODO: Clear table before filling it
		if (lTable.getColumns() != null)
		{
			lTable.getColumns().clear();
		}

		if (lTable.getItems() != null)
		{
			lTable.getItems().clear();
		}

		List<String[]> results = controller.runDijkstra();

		for (String[] result : results)
		{
			System.out.println(Arrays.toString(result));
		}

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
