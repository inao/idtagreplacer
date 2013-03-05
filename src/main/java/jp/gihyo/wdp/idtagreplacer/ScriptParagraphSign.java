package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;
import java.util.logging.Logger;

public class ScriptParagraphSign extends ParagraphSign {
	private String functionString = null;

	public ScriptParagraphSign(String sign, ParagraphTag tag) {
		super(sign, tag);

		String funcKey = tag.getTagName();
		functionString = getFunctionString(funcKey);
	}

	public ScriptParagraphSign(String startSign, String endSign,
			ParagraphTag tag) {
		super(startSign, endSign, tag);

		String funcKey = tag.getTagName();
		functionString = getFunctionString(funcKey);
	}

	private String getFunctionString(String key) {
		PropertiesLoader p = PropertiesLoader.getInstance();
		return p.getProperties().getProperty(key, "");
	}

	@Override
	protected String whenEndSignMuches(String line) throws IOException,
			SourceParserException {
		String ret = launchScript(line, "whenEndSignMuches();");
		PrintController ctl = App.getInstance().getPrintController();
		if (!ret.equals("undefined")) {
			// App.out.println(ret);
			ctl.addCommand("print");
		}
		// App.getInstance().getActiveParagraphTag().removeLast();
		ctl.addCommand("remove last para");
		return ret;
	}

	@Override
	protected String whenStartSignMuches(String line) throws IOException,
			SourceParserException {
		PrintController ctl = App.getInstance().getPrintController();
		App.getInstance().getActiveParagraphTag().add(tag);
		String ret = launchScript(line, "whenStartSignMuches();");
		if (ret.equals("undefined"))
			ret = "";
		if (ctl.hasCommands())
			return ret;

		if (hasEndSign()) {
			// if (ret.trim().length() != 0) App.out.println(ret);
			if (ret.trim().length() != 0)
				ctl.addCommand("print");
		} else {
			// App.out.println(ret);
			// App.getInstance().getActiveParagraphTag().removeLast();
			ctl.addCommand("print");
			ctl.addCommand("remove last para");
		}
		return ret;
	}

	private String launchScript(String line, String appendCode) {
		if (functionString.length() == 0) {
			Logger.global.warning("'" + startSign + "'を検出しましたが、"
					+ "呼び出すべきスクリプトの設定が見つかりません。タグ置換処理をスキップします。");
			return line;
		}

		String tagName = tag.getTagName();

		RhinoLauncher r = new RhinoLauncher();
		// r.addPreCode("importPackage(Packages.jp.gihyo.wdp.idtagreplacer);");
		r.addPreCode("importClass(Packages.jp.gihyo.wdp.idtagreplacer.ParagraphTag);");
		r.addBasicAPI();
		r.addPreCode("var startSign = '" + startSign.replace("'", "\\'") + "';");
		if (endSign != null)
			r.addPreCode("var endSign = '" + endSign.replace("'", "\\'") + "';");
		r.addPreCode("var targetLine = '" + line.replace("'", "\\'") + "';");
		r.addPreCode("var tagName = '" + tagName.replace("'", "\\'") + "';");

		String ret = null;
		try {
			ret = r.launch(functionString + "\n" + appendCode);
		} catch (Exception e) {
			Logger.global.warning("'" + tagName + "'のJavaScriptでエラーが発生しました。"
					+ "メッセージ：" + e.getMessage());
		}
		return ret;
	}

}
