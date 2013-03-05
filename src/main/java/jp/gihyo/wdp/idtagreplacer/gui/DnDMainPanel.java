package jp.gihyo.wdp.idtagreplacer.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

class DnDMainPanel extends MainPanel {
	private static final long serialVersionUID = -3791091232402041897L;
	private JLabel statusLabel = null;

	public DnDMainPanel() {
		super(new BorderLayout());

		DisplayMode m = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
		int h = m.getHeight() / 2;
		int w = m.getWidth() / 3;

		setPreferredSize(new Dimension(w, h));
		
		statusLabel = new JLabel();
		statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
		add(statusLabel, BorderLayout.SOUTH);
	}
	
	public void setStatusMessage(String message) {
		statusLabel.setText(message);
	}
}
