package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;

/**
 * 段落の編集記号の情報を格納するためのクラスに、基本となる実装を提供する抽象クラスです。
 */
public abstract class ParagraphSign {

	protected String startSign = null;
	protected String endSign = null;
	protected ParagraphTag tag = null;

	/**
	 * 開始記号だけを持つ段落記号オブジェクトを生成します。
	 * @param sign 開始記号
	 * @param tag 段落タグ
	 */
	public ParagraphSign(String sign, ParagraphTag tag) {
		this.startSign = sign;
		this.tag= tag;
	}
	
	/**
	 * 　開始記号と終了記号を持つ段落記号オブジェクトを生成します。
	 * @param startSign 開始記号
	 * @param endSign 終了記号
	 * @param tag 段落タグ
	 */
	public ParagraphSign(String startSign, String endSign, ParagraphTag tag) {
		this.startSign = startSign;
		this.endSign = endSign;
		this.tag = tag;
	}

	/**
	 * 終了記号を持っているかどうかを返します。
	 * @return 終了記号を持っているなら true
	 */
	protected boolean hasEndSign() {
		return endSign != null;
	}

	/**
	 * 記号をタグに変換して対象の行を出力します。
	 * @param line 処理対象の行
	 * @return 変換を行って出力した場合は true
	 * @throws IOException 出力時にエラーが起こった場合
	 * @throws SourceParserException タグ変換時にエラーが起こった場合
	 */
	public String convertSign(String line) throws IOException, SourceParserException {

		if (line.startsWith(startSign)) {
//			whenStartSignMuches(line);
//			return true;
			return whenStartSignMuches(line);
		}
		
//		if (! hasEndSign()) return false;
		if (! hasEndSign()) return line;
		
		if (line.endsWith(endSign)) {
//			whenEndSignMuches(line);
//			return true;
			return whenEndSignMuches(line);
		}
		
//		return false;
		return line;
	}

	/**
	 * このメソッドは、処理対象の行に開始記号が見つかった場合にconvertSignメソッドから呼び出されます。
	 * ここで具体的な変換処理が実行されます。
	 * @param line 処理対象の行
	 * @throws IOException 出力時にエラーが起こった場合
	 * @throws SourceParserException タグ変換時にエラーが起こった場合
	 */
	protected abstract String whenStartSignMuches(String line) throws IOException, SourceParserException;
	
	/**
	 * このメソッドは、処理対象の行に終了記号が見つかった場合にconvertSignメソッドから呼び出されます。
	 * ここで具体的な処理が実行されます。
	 * @param line 処理対象の行
	 * @throws IOException 出力時にエラーが起こった場合
	 * @throws SourceParserException タグ変換時にエラーが起こった場合
	 */
	protected abstract String whenEndSignMuches(String line) throws IOException, SourceParserException;
}