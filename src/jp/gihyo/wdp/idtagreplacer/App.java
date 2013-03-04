package jp.gihyo.wdp.idtagreplacer;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class App {
	private static App instance = new App();
	
	/**
	 * アプリケーションが利用する出力用オブジェクトを保持します。
	 */
	public static Printer out = null;
	
	/**
	 * 現在有効な段落タグを保持します。
	 * これはリストとして実装されていますが、現状、単純なスタックのように動作しています。
	 * アプリケーションは起動時、タグ設定ファイルを読み込んだ際に、
	 * まずこのリストに「デフォルトの段落スタイル」を登録します。
	 * その後、処理対象のテキストファイルを読み込みながら、
	 * 段落の開始記号が出現するたびに、このリストに対応する段落スタイルをpushし、
	 * 終了記号が出現するたびに、popします。
	 */
	private LinkedList<ParagraphTag> activeParagraphTag = null;
	
	/**
	 * 段落編集記号のリストを保持します。
	 * タグ設定ファイルに記述された内容が、パーサを通じてここに格納されます。
	 */
	private LinkedList<ParagraphSign> paragraphSigns = null;
	
	/**
	 * 文字編集記号のリストを保持します。
	 * タグ設定ファイルに記述された内容が、パーサを通じてここに格納されます。
	 */
	private LinkedList<CharacterSign> characterSigns = null;
	
	/**
	 * 自由置換編集記号のリストを保持します。
	 * タグ設定ファイルに記述された内容が、パーサを通じてここに格納されます。
	 */
	private LinkedList<ReplaceSign> replaceSigns = null;
	
	private PrintController controller = null;
	
	private App() {
		cleanupActiveParagraphTag();

		paragraphSigns = new LinkedList<ParagraphSign>();
		
		characterSigns = new LinkedList<CharacterSign>();
		
		replaceSigns = new LinkedList<ReplaceSign>();
	}
	
	/**
	 * 現在の段落スタイルの情報をすべてクリアします。
	 * <br/>
	 * 段落スタイルの既定値も含め、すべてクリアしてしまいます。注意。
	 */
	public void cleanupActiveParagraphTag() {
		activeParagraphTag = new LinkedList<ParagraphTag>() {
			private static final long serialVersionUID = 1925385660123868542L;

			public boolean add(ParagraphTag o) {
				Logger.global.info("現在の段落タグとして、'" + o.getTagName() + "'が登録されました。");
				return super.add(o);
			}
			
			public ParagraphTag removeLast() throws NoSuchElementException {
				ParagraphTag ret = super.removeLast();
				Logger.global.info("現在の段落タグから'" + ret.getTagName() + "'を取り除きました。");
				return ret;
			}
		};
	}
	
	/**
	 * 現在の段落スタイルをリストで返します。
	 * <br/>
	 * このプログラムでは、段落スタイルをネスト可能にしています。
	 * これは、出現した段落スタイルをリスト（実質スタックとして利用されている）で保持することで実現しています。
	 * このメソッドは、そのリストを返します。
	 * 
	 * @return 現在の段落スタイル
	 */
	public LinkedList<ParagraphTag> getActiveParagraphTag() {
		return activeParagraphTag;
	}
	
	/**
	 * 設定ファイルから読み込んだ、段落編集記号の全情報をリストで返します。
	 * @return 段落編集記号の全情報
	 */
	public LinkedList<ParagraphSign> getParagraphSigns() {
		return paragraphSigns;
	}
	
	/**
	 * 設定ファイルから読み込んだ、文字編集記号の全情報をリストで返します。
	 * @return 文字編集記号の全情報
	 */
	public LinkedList<CharacterSign> getCharacterSigns() {
		return characterSigns;
	}

	/**
	 * 設定ファイルから読み込んだ、自由置換設定の全情報をリストで返します。
	 * @return 自由置換設定の全情報
	 */
	public LinkedList<ReplaceSign> getReplaceSigns() {
		return replaceSigns;
	}
	
	/**
	 * 出力時の制御オブジェクトを新たに作成します。
	 */
	public void makeNewPrintController() {
		controller = new PrintController();
	}
	
	/**
	 * 現在使われている、出力時の制御オブジェクトを返します。
	 * @return 出力時の制御オブジェクト
	 */
	public PrintController getPrintController() {
		return controller;
	}
	
	/**
	 * このクラスのインスタンスを返します。
	 * @return このクラスのインスタンス
	 */
	public static App getInstance() {
		if (instance == null) instance = new App();
		return instance;
	}
}
