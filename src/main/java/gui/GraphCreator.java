package src.main.java.gui;

import src.main.java.model.dijkstra.Edge;
import src.main.java.model.dijkstra.Node;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class GraphCreator extends JPanel
{
	private static final int NODE_DIAMETER = 40;
	private static final int NODE_RADIUS = NODE_DIAMETER / 2;

	private final Node[] nodes;
	private final Edge[] edges;

	public GraphCreator(Node[] nodes, Edge[] edges)
	{
		this.nodes = nodes;
		this.edges = edges;
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.white);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// draw the edges
		g2.setStroke(new BasicStroke(2f));
		g2.setColor(Color.black);
		for (Edge edge : edges)
		{
			Node node1 = edge.getNode1();
			Node node2 = edge.getNode2();
			g2.draw(new Line2D.Double(node1.getX(), node1.getY(), node2.getX(), node2.getY()));

			// draw the weights
			int x1 = node1.getX();
			int y1 = node1.getY();
			int x2 = node2.getX();
			int y2 = node2.getY();
			int x = (x1 + x2) / 2;
			int y = (y1 + y2) / 2;
			g2.setFont(g2.getFont().deriveFont(EDGE_WEIGHT_FONT_SIZE));
			String weight = Integer.toString(edge.getWeight());
			FontMetrics metrics = g2.getFontMetrics();
			int labelWidth = metrics.stringWidth(weight);
			int labelHeight = metrics.getHeight();

			g2.setColor(Color.white);
			g2.fillRect(x - labelWidth / 2 - EDGE_LABEL_PADDING / 2, y - labelHeight / 2,
					labelWidth + EDGE_LABEL_PADDING, labelHeight);

			g2.setColor(Color.black);
			g2.drawString(weight, x - labelWidth / 2, y + labelHeight / 4);
		}

		// draw the nodes
		for (Node node : nodes)
		{
			int x = node.getX() - NODE_RADIUS;
			int y = node.getY() - NODE_RADIUS;
			g2.setColor(Color.gray);
			g2.fill(new Ellipse2D.Double(x, y, NODE_DIAMETER, NODE_DIAMETER));
			g2.setColor(Color.black);
			g2.draw(new Ellipse2D.Double(x, y, NODE_DIAMETER, NODE_DIAMETER));

			FontMetrics metrics = g2.getFontMetrics();
			int labelWidth = metrics.stringWidth(node.getName());
			g2.drawString(node.getName(), x + NODE_RADIUS - labelWidth / 2, y + NODE_RADIUS + 5);
		}
	}

	private static final int EDGE_LABEL_PADDING = 15;

	private static final int EDGE_WEIGHT_FONT_SIZE = 12;

	public static void main(String[] args)
	{
		Node a = new Node(100, 100, "A");
		Node b = new Node(200, 100, "B");
		Node c = new Node(200, 200, "C");
		Node d = new Node(150, 300, "D");
		Node e = new Node(100, 200, "E");

		Node[] nodes = new Node[]{a, b, c, d, e};

		Edge[] edges = new Edge[]{
				new Edge(a, e, 2),
				new Edge(e, c, 3),
				new Edge(e, d, 7),
				new Edge(c, d, 3),
				new Edge(b, c, 4)
		};

		JFrame frame = new JFrame("Graph Creator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(new GraphCreator(nodes, edges));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}