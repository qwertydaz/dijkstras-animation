package src.main.java.gui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Toolbar extends JToolBar implements ActionListener
{
	private ToolbarListener toolbarListener;
	private JButton saveButton;
	private JButton refreshButton;

	public Toolbar()
	{
		setBorder(BorderFactory.createEtchedBorder());

		saveButton = new JButton();
		saveButton.setIcon(createIcon("../resource/img/Save16.gif"));
		saveButton.setToolTipText("Save");

		refreshButton = new JButton();
		refreshButton.setIcon(createIcon("../resource/img/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");

		saveButton.addActionListener(this);
		refreshButton.addActionListener(this);

		setLayout(new FlowLayout(FlowLayout.RIGHT));

//		JPanel tableOptionsPanel = new JPanel();
//		tableOptionsPanel.add(saveButton);
//		tableOptionsPanel.add(refreshButton);
//
//		add(tableOptionsPanel, BorderLayout.EAST);

		add(saveButton);
		add(refreshButton);
	}

	private ImageIcon createIcon(String path)
	{
		URL url = getClass().getResource(path);

		if (url == null)
		{
			System.err.println("Unable to load image: " + path);
			return null;
		}

		return new ImageIcon(url);
	}

	public void setToolbarListener(ToolbarListener listener)
	{
		this.toolbarListener = listener;
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		if (toolbarListener != null)
		{
			JButton clicked = (JButton) actionEvent.getSource();

			if (clicked == saveButton)
			{
				toolbarListener.saveEventOccurred();
			}
			else if (clicked == refreshButton)
			{
				toolbarListener.refreshEventOccurred();
			}
		}
	}
}
