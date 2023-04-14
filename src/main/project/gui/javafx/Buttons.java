package project.gui.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Buttons
{
	private HBox buttonsPane;

	private final Pane mainPane;
	private final HBox animationBox;
	private final HBox chartBox;

	private Button runButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;
	private Button forwardButton;
	private Button backButton;
	private Button stopButton;
	private Button swapButton;

	private final Animation animation;

	private AnimationButtons animationButtons;
	private MainButtons mainButtons;
	private ChartButtons chartButtons;

	private boolean isSwapped = false;

	public Buttons(Controller controller, Graph graph, Table table, Pane mainPane,
	               HBox animationBox, HBox chartBox)
	{
		setupButtonsPane();

		animation = new Animation(controller, graph, table);

		this.mainPane = mainPane;
		this.animationBox = animationBox;
		this.chartBox = chartBox;

		this.animationButtons = new AnimationButtons(this, animation);
		this.mainButtons = new MainButtons(this, controller, graph, table);
		this.chartButtons = new ChartButtons(this, controller);

		// Main Buttons
		this.runButton = mainButtons.getRunButton();
		this.resetButton = mainButtons.getResetButton();
		this.saveButton = mainButtons.getSaveButton();
		this.loadButton = mainButtons.getLoadButton();

		// Animation Buttons
		this.forwardButton = animationButtons.getForwardButton();
		this.backButton = animationButtons.getBackButton();
		this.stopButton = animationButtons.getStopButton();

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

		// Button 5
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
			mainPane.getChildren().remove(chartBox);
			mainPane.getChildren().add(animationBox);

			buttonsPane.getChildren().remove(swapButton);
			buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

			isSwapped = false;
		}
		else
		{
			mainPane.getChildren().remove(animationBox);
			mainPane.getChildren().add(chartBox);

			buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton);

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
