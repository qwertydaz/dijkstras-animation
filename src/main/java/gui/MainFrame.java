package src.main.java.gui;

import src.main.java.controller.Controller;
import src.main.java.gui.edge.EdgeFormPanel;
import src.main.java.gui.node.NodeFormPanel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame
{
	private static final String CONNECTION_ERROR_MESSAGE = "Database Connection Error";
	private final Controller controller;
	private final TablePanel tablePanel;
	private final NodeFormPanel nodeFormPanel;
	private final EdgeFormPanel edgeFormPanel;
	private final GraphPanel graphPanel;
	private final JTabbedPane tabPane;

	public MainFrame()
	{
		super("Dijkstra's Algorithm");
		setLayout(new BorderLayout());

		controller = new Controller();

		// Views
		tablePanel = new TablePanel();
		nodeFormPanel = new NodeFormPanel();
		edgeFormPanel = new EdgeFormPanel();
		graphPanel = new GraphPanel();
		tabPane = new JTabbedPane();

		tabPane.addTab("Node", nodeFormPanel);
		tabPane.addTab("Edge", edgeFormPanel);

		// Listeners
		nodeFormPanel.setNodeFormListener(event ->
		{
			controller.addNode(event);
		});

		edgeFormPanel.setEdgeFormListener(event ->
		{
			controller.addEdge(event);
		});

		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				controller.disconnect();
				dispose();
			}
		});

		// Set Main Frame
		add(tabPane, BorderLayout.WEST);
		add(graphPanel, BorderLayout.CENTER);
		add(tablePanel, BorderLayout.EAST);

		setMinimumSize(new Dimension(1000, 400));
		setSize(1500, 500);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		Dimension size = getToolkit().getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
	}

	private void connect()
	{
		try
		{
			controller.connect();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.",
					CONNECTION_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}
}
