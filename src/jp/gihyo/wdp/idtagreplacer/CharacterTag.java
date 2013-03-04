package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>タグ設定ファイルから読み込まれた、文字スタイルの情報を格納するためのクラスです。</p>
 * <p>このオブジェクトの動作は極めて単純です。
 * オブジェクトはコンストラクタで渡されたスタイル名を保持し、
 * <code>getStartTagString</code> メソッドが呼ばれた際に、
 * <code>&lt;CharStyle: ... &gt;</code> というタグ文字列を作成して返します。</p>
 */
public class CharacterTag {
	private String styleName = null;
	
	/**
	 * スタイル名を指定して文字タグ情報オブジェクトを作成します。
	 * @param styleName スタイル名
	 */
	public CharacterTag(String styleName) {
		this.styleName = styleName;
	}
	
	/**
	 * 開始タグの文字列を返します。
	 * 具体的には、&lt;CharStyle:<i>style name</i>&gt;という文字列を返します。
	 * <i>style name</i> には、コンストラクタで渡された styleName が入ります。
	 * @return 開始タグの文字列
	 */
	public String getStartTagString() {
		return "<CharStyle:" + styleName + ">";
	}
	
	/**
	 * 終了タグの文字列を返します。
	 * 返す値は &lt;CharStyle:&gt; です。
	 * @return 終了タグの文字列を返します。
	 */
	public String getEndTagString() {
		return "<CharStyle:>";
	}
	
	/**
	 * オブジェクトが保持しているスタイル名を返します。
	 * @return スタイル名
	 */
	public String getStyleName() {
		return styleName;
	}
}
