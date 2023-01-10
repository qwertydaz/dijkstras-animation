package src.main.java.gui.swing.node;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;

public class NodeFormPanel extends JPanel
{
	private final JLabel nameLabel;
	private final JTextField nameField;

	private final JButton addButton;
	private NodeFormListener nodeFormListener;

	private final Insets rightInset;
	private final Insets noInset;

	public NodeFormPanel()
	{
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);

		nameLabel = new JLabel("Name: ");
		nameField = new JTextField(10);
		addButton = new JButton("ADD");

		rightInset = new Insets(0, 0, 0, 5);
		noInset = new Insets(0, 0, 0, 0);

		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameField);
		addButton.setMnemonic(KeyEvent.VK_A);

		addButton.addActionListener(e ->
		{
			String name = nameField.getText();

			NodeFormEvent event = new NodeFormEvent(this, name);

			if (nodeFormListener != null)
			{
				nodeFormListener.nodeFormEventOccurred(event);

				resetFormFields();
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add Node");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public void layoutComponents()
	{
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// Next Row

		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.fill = GridBagConstraints.NONE;

		layoutColumn(gc, 0, GridBagConstraints.LINE_END, rightInset, nameLabel);
		layoutColumn(gc, 1, GridBagConstraints.LINE_START, noInset, nameField);

		// Next Row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 2.0;

		layoutColumn(gc, 1, GridBagConstraints.FIRST_LINE_START, noInset, addButton);
	}

	public void layoutColumn(GridBagConstraints gc, int gridx, int anchor, Insets insets, Component component)
	{
		gc.gridx = gridx;
		gc.anchor = anchor;
		gc.insets = insets;
		add(component, gc);
	}

	public void resetFormFields()
	{
		nameField.setText("");
	}

	public void setNodeFormListener(NodeFormListener listener)
	{
		this.nodeFormListener = listener;
	}
}
