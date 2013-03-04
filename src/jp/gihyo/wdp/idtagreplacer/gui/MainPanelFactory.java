package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * MainPanel の具体的な実装を返すためのオブジェクトです。
 * 
 */
public class MainPanelFactory {
	/**
	 * MainPanel の具体的な実装を返します。
	 * @return MainPanel の具体的な実装
	 */
	public static MainPanel create() {
		return new DnDMainPanel();
	}
	
	private MainPanelFactory() { }
}
