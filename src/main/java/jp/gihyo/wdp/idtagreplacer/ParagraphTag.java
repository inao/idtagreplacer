package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>タグ設定ファイルから読み込まれた、段落タグの情報を格納するためのクラスです。</p>
 * <p>オブジェクトはコンストラクタで渡されたスタイル名を保持し、
 * <code>getTagString</code> メソッドが呼ばれた際に、段落タグの文字列を返します。</p>
 */
public class ParagraphTag {
	private String tagName = null;

	/**
	 * スタイル名を指定して、段落タグ情報オブジェクトを作成します。
	 * @param tagName スタイル名
	 */
	public ParagraphTag(String tagName) {
		this.tagName  = tagName;
	}
	
	/**
	 * 保持しているスタイル名を返します。
	 * @return スタイル名
	 */
	public String getTagName() {
		return this.tagName;
	}
	
	/**
	 * 保持しているスタイル名をもとに、段落タグの文字列を生成して返します。
	 * 返す値は <code>&lt;ParaStyle:<i>style name</i>&gt;</code> です。
	 * @return 段落タグの文字列
	 */
	public String getTagString() {
		return "<ParaStyle:" + this.tagName + ">";
	}
}
