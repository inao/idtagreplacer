package jp.gihyo.wdp.idtagreplacer.gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * これは、アプリケーションのメインウィンドウです。
 * ウィンドウの中身は、MainPanel オブジェクトです。
 */
class DnDFrame implements MainFrame {
	private String caption = null;
	private MainPanel panel = null;
	private JFrame frame = null;
	
	public DnDFrame() { }
	
	public DnDFrame(String caption) {
		this.caption = caption;
	}
	
	/* (non-Javadoc)
	 * @see jp.gihyo.wdp.idtagreplacer.gui.MainFrame#show()
	 */
	public void show() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createFrame();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see jp.gihyo.wdp.idtagreplacer.gui.MainFrame#setContentPane(jp.gihyo.wdp.idtagreplacer.gui.DnDMainPanel)
	 */
	public void setContentPane(MainPanel panel) {
		this.panel = panel;
	}
	
	void createFrame() {
		if (caption == null) frame = new JFrame("InDesign Tag Replacer");
		else frame = new JFrame(caption);
		
		JPanel c = this.panel;
		frame.setContentPane(c);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see jp.gihyo.wdp.idtagreplacer.gui.MainFrame#showErrorMessage(java.lang.String)
	 */
	public void showErrorMessage(String message) {
		MessageThread t = new MessageThread("エラー", message, JOptionPane.ERROR_MESSAGE);
		SwingUtilities.invokeLater(t);
	}
	
	/* (non-Javadoc)
	 * @see jp.gihyo.wdp.idtagreplacer.gui.MainFrame#showMessage(java.lang.String, java.lang.String)
	 */
	public void showMessage(String title, String message) {
		MessageThread t = new MessageThread(title, message, JOptionPane.INFORMATION_MESSAGE);
		SwingUtilities.invokeLater(t);
	}
	
	class MessageThread implements Runnable {
		private String message = null;
		private String title = null;
		private int option = JOptionPane.INFORMATION_MESSAGE;
		
		MessageThread(String title, String message, int option) {
			this.message = message;
			this.title = title;
			this.option = option;
		}
		public void run() {
			JOptionPane.showMessageDialog(frame, message, title, option);
		}
	}

	/* (non-Javadoc)
	 * @see jp.gihyo.wdp.idtagreplacer.gui.MainFrame#setStatusMessage(java.lang.String)
	 */
	public void setStatusMessage(String string) {
		panel.setStatusMessage(string);
	}

	public String showFileChooser(String title) {
		JFileChooser c = new JFileChooser();
		c.setDialogTitle(title);
		if (c.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			return c.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}

}
