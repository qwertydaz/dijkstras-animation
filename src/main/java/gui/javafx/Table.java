package src.main.java.gui.javafx;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Table
{
	private Pane tablePane;
	private final Controller controller;
	private TableView<String> lTable;

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

		tablePane.getChildren().add(lTable);
	}

	public Pane getTablePane()
	{
		return tablePane;
	}

	public void refreshTable()
	{
		List<Circle> nodes = controller.getNodes();

		for (Circle node : nodes)
		{
			lTable.getColumns().add(new TableColumn<>(controller.findLabel(node).getText()));
		}
	}

	public void fillTable()
	{
		List<ArrayList<String>> results = controller.runDijkstra();

		for (ArrayList<String> resultRow : results)
		{
			for (String result : resultRow)
			{
				lTable.getItems().add(result);
			}
		}
	}
}
