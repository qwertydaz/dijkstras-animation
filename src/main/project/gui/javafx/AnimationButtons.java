package project.gui.javafx;

import javafx.scene.control.Button;

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
		// Button 6
		forwardButton = new Button("→");

		// Button 7
		backButton = new Button("←");

		// Button 8
		stopButton = new Button("Stop");
	}

	private void setupButtonActions()
	{
		// Button 6
		forwardButton.setOnAction(actionEvent -> forward());

		// Button 7
		backButton.setOnAction(actionEvent -> backward());

		// Button 8
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
