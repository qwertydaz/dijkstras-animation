package src.main.java.gui.edge;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.util.List;

import src.main.java.controller.Controller;
import src.main.java.model.dijkstra.Node;

public class EdgeFormPanel extends JPanel
{
	private final DefaultComboBoxModel<String> nodeModel;
	private final JLabel node1Label;
	private final JComboBox<String> node1Combo;
	private final JLabel node2Label;
	private final JComboBox<String> node2Combo;
	private final JLabel weightLabel;
	private final JSpinner weightSpinner;
	private final SpinnerNumberModel spinnerModel;
	private final JButton addButton;

	private final Controller controller;
	private EdgeFormListener edgeFormListener;

	private final Insets rightInset;
	private final Insets noInset;

	public EdgeFormPanel()
	{
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);

		controller = new Controller();

		rightInset = new Insets(0, 0, 0, 5);
		noInset = new Insets(0, 0, 0, 0);

		nodeModel = generateNodeModel();

		// Node 1
		node1Label = new JLabel("Node1 Name: ");
		node1Combo = new JComboBox<>();
		node1Combo.setModel(nodeModel);
		if (nodeModel.getSize() != 0)
		{
			node1Combo.setSelectedIndex(0);
		}

		node1Label.setDisplayedMnemonic(KeyEvent.VK_1);
		node1Label.setLabelFor(node1Combo);

		// Node 2
		node2Label = new JLabel("Node2 Name: ");
		node2Combo = new JComboBox<>();
		node2Combo.setModel(nodeModel);
		if (nodeModel.getSize() != 0)
		{
			node2Combo.setSelectedIndex(0);
		}

		node2Label.setDisplayedMnemonic(KeyEvent.VK_2);
		node2Label.setLabelFor(node2Combo);

		// Weight
		weightLabel = new JLabel("Weight: ");
		spinnerModel = new SpinnerNumberModel(5, 1, 10, 1);
		weightSpinner = new JSpinner(spinnerModel);

		weightLabel.setDisplayedMnemonic(KeyEvent.VK_W);
		weightLabel.setLabelFor(weightSpinner);

		// Button
		addButton = new JButton("ADD");

		addButton.setMnemonic(KeyEvent.VK_A);

		addButton.addActionListener(e ->
		{
			String node1Name = (String) node1Combo.getSelectedItem();
			String node2Name = (String) node2Combo.getSelectedItem();
			int weight = (int) weightSpinner.getValue();

			EdgeFormEvent event = new EdgeFormEvent(this, node1Name, node2Name, weight);

			if (edgeFormListener != null)
			{
				edgeFormListener.edgeFormEventOccurred(event);

				resetFormFields();
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add Edge");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public DefaultComboBoxModel<String> generateNodeModel()
	{
		List<Node> nodes = controller.getNodes();
		DefaultComboBoxModel<String> newNodeModel = new DefaultComboBoxModel<>();

		for (Node node : nodes)
		{
			newNodeModel.addElement(node.getName());
		}

		return newNodeModel;
	}

	public void layoutComponents()
	{
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// Next Row

		gc.gridy = 0;

		gc.fill = GridBagConstraints.NONE;

		layoutRow(gc, node1Label, node1Combo);

		// Next Row

		gc.gridy++;

		layoutRow(gc, node2Label, node2Combo);

		// Next Row

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.1;

		layoutColumn(gc, 0, GridBagConstraints.LINE_END, rightInset, weightLabel);
		layoutColumn(gc, 1, GridBagConstraints.LINE_START, noInset, weightSpinner);

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

	public void layoutRow(GridBagConstraints gc, Component label, Component component)
	{
		// Set up row
		gc.weightx = 1;
		gc.weighty = 0.1;

		// Label
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = rightInset;
		add(label, gc);

		// Component
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = noInset;
		add(component, gc);
	}

	public void resetFormFields()
	{
		node1Combo.setSelectedIndex(0);
		node2Combo.setSelectedIndex(0);
		weightSpinner.setValue(5);
	}

	public void setEdgeFormListener(EdgeFormListener listener)
	{
		this.edgeFormListener = listener;
	}
}
