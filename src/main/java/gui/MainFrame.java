package src.main.java.gui;

import src.main.java.controller.Controller;
import src.main.java.gui.edge.EdgeFormPanel;
import src.main.java.gui.edge.EdgeTablePanel;
import src.main.java.gui.node.NodeFormPanel;
import src.main.java.gui.node.NodeTablePanel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class MainFrame extends JFrame
{
	private static final String CONNECTION_ERROR_MESSAGE = "Error: Database Connection";
	private static final String FORM_ERROR_MESSAGE = "Error: Invalid Form Entry";
	private final Controller controller;
	private final Toolbar toolbar;
	private final NodeTablePanel nodeTablePanel;
	private final EdgeTablePanel edgeTablePanel;
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
		nodeTablePanel = new NodeTablePanel();
		edgeTablePanel = new EdgeTablePanel();
		nodeFormPanel = new NodeFormPanel();
		edgeFormPanel = new EdgeFormPanel();
		graphPanel = new GraphPanel();
		formTabPane = new JTabbedPane();
		tableTabPane = new JTabbedPane();

		formTabPane.addTab("Node Form", nodeFormPanel);
		edgeFormPanel.setNodes(controller.getNodes());
		formTabPane.addTab("Edge Form", edgeFormPanel);

		tableTabPane.addTab("Node Database", nodeTablePanel);
		tableTabPane.addTab("Edge Database", edgeTablePanel);

		// Listeners
		nodeTablePanel.setData(controller.getNodes());
		nodeTablePanel.setNodeTableListener(controller::removeNode);

		edgeTablePanel.setData(controller.getEdges());
		edgeTablePanel.setEdgeTableListener(controller::removeEdge);

		nodeFormPanel.setNodeFormListener(event ->
		{
			String nodeName = event.getName();

			if (!nodeName.isEmpty())
			{
				if (controller.isNodePresent(nodeName))
				{
					controller.addNode(event);
					nodeTablePanel.refresh();
				}
				else
				{
					JOptionPane.showMessageDialog(MainFrame.this, "All nodes must have a unique name.",
							FORM_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(MainFrame.this, "All nodes must have a name.",
						FORM_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
			}
		});


		// TODO:
		//  - Ensure 2 nodes can only have a single edge
		//  - Add the ability to edit existing nodes and edges
		//  - Flesh out GraphPanel:
		//      - Finish and incorporate GraphCreator using database node and edge data
		//  - Add more validation
		//  - Create unit tests / GitLab CI Pipeline (?)
		edgeFormPanel.setEdgeFormListener(event ->
		{
			if (!event.getNode1Name().equals(event.getNode2Name()))
			{
				controller.addEdge(event);
				edgeTablePanel.refresh();
			}
			else
			{
				JOptionPane.showMessageDialog(MainFrame.this, "An edge cannot point to the same node.",
						FORM_ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
			}
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

					edgeFormPanel.setNodes(controller.getNodes());
					edgeFormPanel.refresh();
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
