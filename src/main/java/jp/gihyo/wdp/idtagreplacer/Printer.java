package jp.gihyo.wdp.idtagreplacer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 保存先のファイルを出力するためのクラスです。
 */
public class Printer {
	private String lineFeedCode = null;
	private BufferedWriter writer = null;

	/**
	 * 指定されたファイルと文字コードで、出力用オブジェクトを生成します。
	 * 
	 * @param file
	 *            保存先のファイル
	 * @param charset
	 *            保存ファイルの文字コード
	 * @throws FileNotFoundException
	 */
	public Printer(File file, Charset charset) throws FileNotFoundException {
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), charset));
		lineFeedCode = System.getProperty("line.separator");
	}

	/**
	 * 指定されたファイルパスと文字コードで、出力用オブジェクトを生成します。
	 * 
	 * @param filePath
	 *            保存先のファイルパス
	 * @param charset
	 *            保存ファイルの文字コード
	 * @throws FileNotFoundException
	 */
	public Printer(String filePath, Charset charset)
			throws FileNotFoundException {
		this(new File(filePath), charset);
	}

	/**
	 * 改行コードを指定します。 指定できる値は原則的に <code>LF</code>、<code>CR</code>、
	 * <code>CRLF</code> のいずれかです。 これはリテラルで "LF" のように指定しても、直接文字コードで指定してもかまいません。
	 * プログラムは、上記のリテラルに対しては、該当する文字コードに変換する処理を行いますが、
	 * それ以外の文字列が渡された場合は、ノーチェックでそれを改行コードと見なし、出力ファイルの 各行末に付加します。
	 * 
	 * @param lineFeedCode
	 *            改行コードを表す文字列
	 */
	public void setLineFeedCode(String lineFeedCode) {
		String c = lineFeedCode;
		if (lineFeedCode.equals("LF")) {
			c = (new Character((char) 10)).toString();
		} else if (lineFeedCode.equals("CR")) {
			c = (new Character((char) 13)).toString();
		} else if (lineFeedCode.equals("CRLF")) {
			c = (new Character((char) 13)).toString()
					+ (new Character((char) 10)).toString();
		}
		this.lineFeedCode = c;
	}

	/**
	 * オブジェクトに設定されている改行コードを返します。 これは "LF" のような可読の文字列ではなく、実際の改行コードが返されます。
	 * 
	 * @return 改行コード
	 */
	public String getLineFeedCode() {
		return lineFeedCode;
	}

	/**
	 * 渡された引数の文字列を出力ファイルに出力します。 改行コードは付加されません。
	 * 
	 * @param buf
	 *            出力する文字列
	 * @throws IOException
	 *             出力時に例外が発生した場合
	 */
	public void print(String buf) throws IOException {
		writer.write(buf);
	}

	/**
	 * 渡された引数の文字列を出力ファイルに出力します。 <code>print</code> メソッドと違い、出力時に改行コードが付加されます。
	 * しかし、<code>print</code> メソッドとの違いは、改行コードのあり、なしに留まりません。 両者は意味的に大きく違っています。
	 * <code>println</code> メソッドは、行が出力されたことをプログラムに明示します。
	 * プログラムはこれによって、段落タグの制御を行います。 これに対し、<code>print</code>
	 * メソッドによって直接改行コードを出力した場合は、 プログラムは段落タグの制御を行いません。
	 * 
	 * @param buf
	 *            出力する文字列
	 * @throws IOException
	 *             出力時に例外が発生した場合
	 * @throws SourceParserException
	 */
	public void println(String buf) throws IOException, SourceParserException {
		ParagraphTag t = null;
		if (App.getInstance().getActiveParagraphTag().size() == 0) {
			throw new SourceParserException("段落タグの開始タグと閉じタグの対応に不正がありました。");
		} else {
			t = App.getInstance().getActiveParagraphTag().getLast();
			writer.write(t.getTagString());
		}
		writer.write(buf + lineFeedCode);
	}

	/**
	 * 保存先のファイルを閉じます。
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.close();
	}
}
