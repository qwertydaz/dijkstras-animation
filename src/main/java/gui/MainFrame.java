package src.main.java.gui;

import src.main.java.controller.Controller;
import src.main.java.gui.edge.EdgeFormPanel;
import src.main.java.gui.edge.EdgeTableModel;
import src.main.java.gui.node.NodeFormPanel;
import src.main.java.gui.node.NodeTableModel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame
{
	private static final String CONNECTION_ERROR_MESSAGE = "Database Connection Error";
	private final Controller controller;
	private final Toolbar toolbar;
	private final TablePanel nodeTablePanel;
	private final TablePanel edgeTablePanel;
	private final NodeFormPanel nodeFormPanel;
	private final EdgeFormPanel edgeFormPanel;
	private final GraphPanel graphPanel;
	private final JTabbedPane formTabPane;
	private final JTabbedPane tableTabPane;

	public MainFrame()
	{
		super("Dijkstra's Algorithm");
		setLayout(new BorderLayout());

		controller = new Controller();

		// Views
		toolbar = new Toolbar();
		nodeTablePanel = new TablePanel<>(new NodeTableModel());
		edgeTablePanel = new TablePanel<>(new EdgeTableModel());
		nodeFormPanel = new NodeFormPanel();
		edgeFormPanel = new EdgeFormPanel();
		graphPanel = new GraphPanel();
		formTabPane = new JTabbedPane();
		tableTabPane = new JTabbedPane();

		formTabPane.addTab("Node Form", nodeFormPanel);
		formTabPane.addTab("Edge Form", edgeFormPanel);

		tableTabPane.addTab("Node Database", nodeTablePanel);
		tableTabPane.addTab("Edge Database", edgeTablePanel);

		// Listeners
		nodeTablePanel.setData(controller.getNodes());
		nodeTablePanel.setTableListener(controller::removeNode);

		edgeTablePanel.setData(controller.getEdges());
		edgeTablePanel.setTableListener(controller::removeEdge);

		nodeFormPanel.setNodeFormListener(event ->
		{
			controller.addNode(event);
			nodeTablePanel.refresh();
		});

		edgeFormPanel.setEdgeFormListener(event ->
		{
			controller.addEdge(event);
			edgeTablePanel.refresh();
		});

		toolbar.setToolbarListener(new ToolbarListener()
		{
			@Override
			public void saveEventOccurred()
			{
				connect();

				try
				{
					controller.saveNodes();
					controller.saveEdges();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.",
							CONNECTION_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void refreshEventOccurred()
			{
				connect();

				try
				{
					controller.loadNodes();
					nodeTablePanel.refresh();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load nodes from database.",
							CONNECTION_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				}

				try
				{
					controller.loadEdges();
					edgeTablePanel.refresh();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to load edges from database.",
							CONNECTION_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				}
			}
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
		add(formTabPane, BorderLayout.WEST);
		add(graphPanel, BorderLayout.CENTER);
		add(tableTabPane, BorderLayout.EAST);
		add(toolbar, BorderLayout.NORTH);

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
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.",
					CONNECTION_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}
}
