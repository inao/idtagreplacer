package jp.gihyo.wdp.idtagreplacer;

/**
 * <p>
 * 自由置換設定の情報を格納するためのクラスです。
 * </p>
 * <p>
 * このオブジェクトの動作は、一般的なビーンと同様です。 コンストラクタで受け取った内容を保持し、ゲッターでそれを返します。
 * </p>
 */
public class ReplaceSign {
	private String sign = null;
	private ReplaceTag tag = null;

	/**
	 * 自由置換設定情報オブジェクトを生成します。
	 * 
	 * @param sign
	 *            置換前の文字列
	 * @param tag
	 *            置換後の情報
	 */
	public ReplaceSign(String sign, ReplaceTag tag) {
		this.sign = sign;
		this.tag = tag;
	}

	/**
	 * 置換前の文字列を返します。
	 * 
	 * @return 置換前の文字列
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * 置換後の情報を返します。
	 * 
	 * @return 置換後の情報
	 */
	public ReplaceTag getTag() {
		return tag;
	}
}
