package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * アプリケーションのメインウィンドウの実装を返します。
 */
public class MainFrameFactory {
	/**
	 * アプリケーションのメインウィンドウの実装を返します。
	 * @return MainFrame インターフェイスの具体的な実装
	 */
	public static MainFrame create() {
		return new DnDFrame();
	}
	
	private MainFrameFactory() { }
}
