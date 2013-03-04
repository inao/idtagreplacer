package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>タグ設定ファイルから読み込まれた、自由置換設定の置換後の内容を保持するためのクラスです。</p>
 * <p><code>ParagraphTag</code> や <code>CharacterTag</code> に合わせて、
 * <code>ReplaceSign</code> と対になるように作られていますが、機能的には
 * <code>ReplaceSign</code> と分ける必要はありません。</p>
 * <p>このオブジェクトの動作は、ごく単純なビーンと同様です。</p>
 */
public class ReplaceTag {
	private String replacement = null;
	
	/**
	 * 置換後の情報を管理するオブジェクトを生成します。
	 * @param replacement 置換後の文字列
	 */
	public ReplaceTag(String replacement) {
		this.replacement = replacement;
	}
	
	/**
	 * 置換後の文字列を返します。
	 * @return 置換後の文字列
	 */
	public String getReplacement() {
		return replacement;
	}
}
