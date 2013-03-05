package jp.gihyo.wdp.idtagreplacer.gui;

/**
 * アプリケーションのメインウィンドウとなるフレームです。
 */
public interface MainFrame {

	/**
	 * フレームを表示します。
	 */
	public void show();

	/**
	 * 指定されたメッセージを持つ、エラーメッセージボックスを表示します。
	 * 
	 * @param message
	 *            エラーメッセージボックスに表示したい文字列
	 */
	public void showErrorMessage(String message);

	/**
	 * 指定されたタイトルとメッセージを持つ、メッセージボックスを表示します。
	 * 
	 * @param title
	 *            メッセージボックスのタイトルバーに表示したい文字列
	 * @param message
	 *            メッセージボックスに表示したい文字列
	 */
	public void showMessage(String title, String message);

	/**
	 * フレームのステータスバーの領域に表示したいメッセージを指定します。
	 * 
	 * @param string
	 *            ステータスバーの領域に表示したい文字列
	 */
	public void setStatusMessage(String string);

	/**
	 * フレームに乗せるパネルを指定します。
	 * 
	 * @param p
	 *            フレームに乗せたいパネル
	 */
	public void setContentPane(MainPanel p);

	/**
	 * ファイル選択ダイアログを表示する。
	 * 
	 * @return 選択されたファイルのパス。キャンセルの場合は null。
	 */
	public String showFileChooser(String title);
}