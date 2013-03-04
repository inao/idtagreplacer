package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>文字編集記号クラスの、インターフェイスとして利用するクラスです。</p>
 * <p>文字編集記号クラスは、変換元となる開始／終了記号と、変換先のスタイル情報を保持します。
 * 具体的な変換処理の内容は、サブクラスで実装します。</p>
 * <p>構造は単純です。パラメータとなる編集記号やスタイル情報を受け取るコンストラクタと、
 * 変換処理を行う <code>convertSign</code> メソッドがあるだけです。</p>
 */
public abstract class CharacterSign {

	protected String startSign = null;
	protected String endSign = null;
	protected CharacterTag tag = null;

	/**
	 * 指定された開始記号、終了記号、タグ情報で、文字編集記号オブジェクトを作成します。
	 * @param startSign 開始記号
	 * @param endSign 終了記号
	 * @param tag タグ情報
	 */
	public CharacterSign(String startSign, String endSign, CharacterTag tag) {
		this.startSign = startSign;
		this.endSign = endSign;
		this.tag = tag;
	}
	
	/**
	 * 引数で渡された文字列を、タグ変換して返します。
	 * @param line 変換対象の文字列
	 * @return 変換された文字列
	 */
	public abstract String convertSign(String line);
}