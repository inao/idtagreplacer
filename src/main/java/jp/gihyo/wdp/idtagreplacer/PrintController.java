package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * 出力時に行うべき操作の予約を受付け、実行するためのオブジェクトです。
 */
public class PrintController {
	private LinkedList<String> cmds = null;

	PrintController() {
		cmds = new LinkedList<String>();
	}

	/**
	 * 出力時に行うべき操作を追加します。 <br/>
	 * 操作は、特定のコマンドを発行して予約します。 利用できるコマンドは、
	 * <ul>
	 * <li>print</li>
	 * <li>remove last para</li>
	 * <li>その他</li>
	 * </ul>
	 * の 3 種類です。<br/>
	 * 「print」はファイルへの出力を行います。 これは、現在処理中の行を、コマンド発行時の状態で出力するということではありません。
	 * ファイルへの出力は、処理がすべて完了した段階で行われます。<br/>
	 * 「remove last para」は、現在の段落スタイルのうち、最後のものを削除します。<br/>
	 * その他のコマンドは原則的に使いませんが、意図的に何も出力させたくない場合に使うこともできます。
	 * コマンドをタイポすると、出力されなくなることがあります。<br/>
	 * このメソッドは、タイポ等のために例外を送出することはありません。そのコマンドは単純に無視されます。
	 * 
	 * @param command
	 */
	public void addCommand(String command) {
		cmds.add(command);
	}

	/**
	 * 出力時の操作が何か予約されているかどうかを返します。 <br/>
	 * 何らかの操作が予約されていれば true を返します。
	 * 
	 * @return 操作が予約されているかどうか
	 */
	public boolean hasCommands() {
		return !cmds.isEmpty();
	}

	/**
	 * 予約されている操作を実行します。
	 * 
	 * @param line
	 *            出力する文字列（print コマンドが発行されている場合）
	 * @throws IOException
	 *             I/O 関係の例外が発生した場合
	 * @throws SourceParserException
	 *             　原稿ファイルの解析に関係する例外が発生した場合
	 */
	public void execute(String line) throws IOException, SourceParserException {
		ListIterator<String> it = cmds.listIterator();
		while (it.hasNext()) {
			String c = it.next();
			if (c.equals("print")) {
				App.out.println(line);
			} else if (c.equals("remove last para")) {
				App.getInstance().getActiveParagraphTag().removeLast();
			}
		}
	}
}
