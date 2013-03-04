package jp.gihyo.wdp.idtagreplacer;

import java.util.logging.Logger;

public class ScriptCharacterSign extends CharacterSign {
	private String functionString = null;

	public ScriptCharacterSign(String startSign, String endSign, CharacterTag tag) {
		super(startSign, endSign, tag);
		
		String funcKey = tag.getStyleName().substring("!!".length());
		functionString = getFunctionString(funcKey);
	}
	
	private String getFunctionString(String key) {
		PropertiesLoader p = PropertiesLoader.getInstance();
		return p.getProperties().getProperty(key, "");
	}

	@Override
	public String convertSign(String line) {
		if (line.indexOf(startSign) == -1) return line;
		if (functionString.length() == 0) {
			Logger.global.warning("'" + startSign + "'を検出しましたが、" + 
					"呼び出すべきスクリプトの設定が見つかりません。タグ置換処理をスキップします。");
			return line;
		}
		
		String tagName = tag.getStyleName().substring("!!".length());

		RhinoLauncher r = new RhinoLauncher();
		r.addBasicAPI();
		r.addPreCode("var startSign = '" + startSign.replace("'", "\\'") + "';");
		r.addPreCode("var endSign = '" + endSign.replace("'", "\\'") + "';");
		r.addPreCode("var targetLine = '" + line.replace("'", "\\'") + "';");
		r.addPreCode("var tagName = '" + tagName.replace("'", "\\'") + "';");
		r.addPreCode("function searchTag(func) {");
		r.addPreCode("for (;;) {");
		r.addPreCode("var s = targetLine.indexOf(startSign);");
		r.addPreCode("var e = targetLine.indexOf(endSign);");
		r.addPreCode("if (s == -1 || e == -1) break;");
		r.addPreCode("var left = targetLine.substring(0, s);");
		r.addPreCode("var body = targetLine.substring(s + startSign.length, e);");
		r.addPreCode("var right = targetLine.substring(e + endSign.length);");
		r.addPreCode("targetLine = func(left, body, right);");
		r.addPreCode("}");
		r.addPreCode("return targetLine;");
		r.addPreCode("}");
		String ret = null;
		try {
			ret = r.launch(functionString);
		} catch (Exception e) {
			Logger.global.warning("'" + tagName + "'のJavaScriptでエラーが発生しました。" +
					"メッセージ：" + e.getMessage());
		}
		
		return ret;
	}

}
