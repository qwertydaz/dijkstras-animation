package project.gui.javafx.buttons;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import project.gui.javafx.Animation;
import project.gui.javafx.ComparisonChart;
import project.gui.javafx.controller.Controller;
import project.gui.javafx.Graph;
import project.gui.javafx.Table;
import project.gui.javafx.util.Util;

import java.util.Objects;

public class Buttons
{
	private HBox buttonsPane;

	private final Pane mainPane;
	private final HBox animationBox;

	private final ComparisonChart comparisonChart;

	private final Separator separator = new Separator();

	private final Button runButton;
	private final Button resetButton;
	private final Button saveButton;
	private final Button loadButton;

	private final Button forwardButton;
	private final Button backButton;
	private final Button stopButton;

	private Button swapButton;

	private VBox totalNodesBox;
	private VBox stepSizeBox;
	private Button createChartButton;

	private final Animation animation;

	private boolean isSwapped = false;

	public Buttons(Controller controller, Graph graph, Table table, ComparisonChart comparisonChart, Pane mainPane,
	               HBox animationBox)
	{
		setupButtonsPane();

		separator.setOrientation(Orientation.VERTICAL);
		animation = new Animation(controller, graph, table);

		this.mainPane = mainPane;
		this.animationBox = animationBox;
		this.comparisonChart = comparisonChart;

		AnimationButtons animationButtons = new AnimationButtons(this, animation);
		MainButtons mainButtons = new MainButtons(this, controller, graph, table);
		ChartButtons chartButtons = new ChartButtons(this, comparisonChart);

		// Main Buttons
		this.runButton = mainButtons.getRunButton();
		this.resetButton = mainButtons.getResetButton();
		this.saveButton = mainButtons.getSaveButton();
		this.loadButton = mainButtons.getLoadButton();

		// Animation Buttons
		this.forwardButton = animationButtons.getForwardButton();
		this.backButton = animationButtons.getBackButton();
		this.stopButton = animationButtons.getStopButton();

		// Chart Buttons
		this.totalNodesBox = chartButtons.getTotalNodesBox();
		this.stepSizeBox = chartButtons.getStepSizeBox();
		this.createChartButton = chartButtons.getCreateChartButton();

		setupButtons();
		setupButtonsActions();
	}

	private void setupButtonsPane()
	{
		buttonsPane = new HBox();

		buttonsPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));
	}

	public Pane getPane()
	{
		return buttonsPane;
	}

	private void setupButtons()
	{
		// Load Icons
		Image swapIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/swap.png")));
		ImageView swapIconView = new ImageView(swapIcon);
		swapIconView.setFitHeight(50);
		swapIconView.setFitWidth(50);

		// Button 1
		swapButton = new Button("\u200E");
		swapButton.setGraphic(swapIconView);

		// Add buttons to buttonsPane
		buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

		// Centre buttons
		buttonsPane.setAlignment(Pos.CENTER);
	}

	private void setupButtonsActions()
	{
		// Button 5
		swapButton.setOnAction(actionEvent -> swap());
	}

	private void swap()
	{
		if (isSwapped)
		{
			// Swap to animation window
			mainPane.getChildren().remove(comparisonChart.getPane());
			mainPane.getChildren().add(animationBox);

			// Swap buttons
			buttonsPane.getChildren().removeAll(totalNodesBox, stepSizeBox, separator, createChartButton, swapButton);
			buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

			isSwapped = false;
		}
		else
		{
			// Swap to chart window
			mainPane.getChildren().remove(animationBox);
			mainPane.getChildren().add(comparisonChart.getPane());

			// Swap buttons
			buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton, swapButton);
			buttonsPane.getChildren().addAll(totalNodesBox, stepSizeBox, separator, createChartButton, swapButton);

			isSwapped = true;
		}
	}

	public void startAnimation()
	{
		// Toggle button visibility
		buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton, swapButton);
		buttonsPane.getChildren().addAll(backButton, forwardButton, stopButton);

		animation.start();
	}

	public void stopAnimation()
	{
		if (Util.displayOptionDialog("Stop", "Are you sure you want to stop the animation?"))
		{
			// Toggle button visibility
			buttonsPane.getChildren().removeAll(backButton, forwardButton, stopButton);
			buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

			animation.stop();
		}
	}
}
