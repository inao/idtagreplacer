package jp.gihyo.wdp.idtagreplacer;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

/**
 * <p>
 * Rhino のスクリプトを実行するためのクラスです。
 * </p>
 */
public class RhinoLauncher {
	private StringBuilder preCode = new StringBuilder();

	/**
	 * <p>
	 * スクリプト本体を実行する前にロードしておくべきスクリプトを指定します。
	 * </p>
	 * <p>
	 * スクリプトの実行は、<code>launch</code> メソッドで行います。 ここで指定されたスクプリトは、
	 * <code>launch</code> メソッドの中で、 本体のスクリプトに先立って実行されます。
	 * </p>
	 * 
	 * @param code
	 *            あらかじめロードするスクリプト
	 */
	public void addPreCode(String code) {
		preCode.append(code + "\n");
	}

	public void addBasicAPI() {
		String appInstance = "Packages.jp.gihyo.wdp.idtagreplacer.App.getInstance()";
		addPreCode("var App = {");
		addPreCode(String.format(
				"activeParagraphTag: %s.getActiveParagraphTag(),", appInstance));
		addPreCode(String.format("characterSigns: %s.getCharacterSigns(),",
				appInstance));
		addPreCode(String.format("paragraphSigns: %s.getParagraphSigns(),",
				appInstance));
		addPreCode(String.format("replaceSigns: %s.getReplaceSigns(),",
				appInstance));
		addPreCode(String.format("out: %s.out,", appInstance));
		addPreCode(String.format("printController: %s.getPrintController()",
				appInstance));
		addPreCode("}");
	}

	/**
	 * <p>
	 * 引数で与えられたスクリプトを Rhino の処理系に渡して実行し、その処理結果を文字列として返します。
	 * </p>
	 * 
	 * @param code
	 *            実行するスクリプト
	 * @return 処理結果
	 * @throws Exception
	 *             スクリプトの処理中にエラーが発生した場合
	 */
	public String launch(String code) throws Exception {
		Context cx = new ContextFactory().enterContext();
		Object ret = null;
		try {
			Scriptable scope = new ImporterTopLevel(cx);
			cx.evaluateString(scope, preCode.toString(), "", 0, null);
			ret = cx.evaluateString(scope, code, "", 0, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Context.exit();
		}
		return Context.toString(ret);
	}
}
