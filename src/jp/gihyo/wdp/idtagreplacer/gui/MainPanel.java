package jp.gihyo.wdp.idtagreplacer.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * アプリケーションのメインウィンドウの中身です。
 */
public abstract class MainPanel extends JPanel {
	MainPanel(LayoutManager layout) {
		super(layout);
	}

	abstract void setStatusMessage(String message);
}