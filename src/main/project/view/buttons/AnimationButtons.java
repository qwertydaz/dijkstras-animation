package project.view.buttons;

import javafx.scene.control.Button;
import project.view.Animation;

public class AnimationButtons
{
	private Button forwardButton;
	private Button backButton;
	private Button stopButton;

	private final Buttons buttons;
	private final Animation animation;

	public AnimationButtons(Buttons buttons, Animation animation)
	{
		this.buttons = buttons;
		this.animation = animation;

		setupButtons();
		setupButtonActions();
	}

	private void setupButtons()
	{
		// Button 1
		forwardButton = new Button("→");

		// Button 2
		backButton = new Button("←");

		// Button 3
		stopButton = new Button("Stop");
	}

	private void setupButtonActions()
	{
		// Button 1
		forwardButton.setOnAction(actionEvent -> forward());

		// Button 2
		backButton.setOnAction(actionEvent -> backward());

		// Button 3
		stopButton.setOnAction(actionEvent -> stopAnimation());
	}

	private void forward()
	{
		animation.forward();
	}

	private void backward()
	{
		animation.backward();
	}

	private void stopAnimation()
	{
		buttons.stopAnimation();
	}

	public Button getForwardButton()
	{
		return forwardButton;
	}

	public Button getBackButton()
	{
		return backButton;
	}

	public Button getStopButton()
	{
		return stopButton;
	}
}
