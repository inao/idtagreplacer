package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;

/**
 * 原稿ファイル用を解析するためのクラスです。
 */
public interface SourceParser {
	/**
	 * 原稿ファイルの一行を受け取り、解析処理を行います。
	 * @param line 原稿ファイルの一行
	 * @throws IOException ファイルの読み込み中に I/O 関連の例外が発生した場合
	 * @throws SourceParserException 解析関連の例外が発生した場合
	 */
	public void parse(String line) throws IOException, SourceParserException;
}
