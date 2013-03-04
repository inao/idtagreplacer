package jp.gihyo.wdp.idtagreplacer;

import java.util.logging.Logger;

/**
 * <p>タグ設定ファイル（tagconf.xml）の「段落タグ設定」や「文字タグ設定」の内容を解析するクラスです。
 * 正規表現による単純な解析処理を行います。</p>
 */
public class SimpleTagconfParser {
	
	private static final String REMAIN_SIGN_MARK = "!";
	private static final String CALL_SCRIPT_MARK = "!!";

	/**
	 * 段落タグ設定の内容を解析し、List にセットします。
	 * @param conf 段落タグ設定の内容全文
	 */
	public void parseParaSetting(String conf) throws TagconfException {
		Logger.global.info("'段落タグ設定'の内容を解析します。");
		String[] lines = conf.split("[\n\r]+");
		try {
			for (String l : lines) {
				String[] items = parseLine(l);
				if (items != null) setParaList(items);
			}
		} catch (TagconfException e) {
			Logger.global.severe(e.getMessage());
			throw e;
		}
		Logger.global.info("'段落タグ設定'の解析処理を終了します。");
	}
	
	/**
	 * 文字タグ設定の内容を解析し、List にセットします。
	 * @param conf 文字タグ設定の内容全文
	 */
	public void parseCharSetting(String conf) throws TagconfException {
		Logger.global.info("'文字タグ設定'の内容を解析します。");
		String[] lines = conf.split("[\n\r]+");
		try {
			for (String l : lines) {
				String[] items = parseLine(l);
				if (items != null) setCharList(items);
			}
		} catch (TagconfException e) {
			Logger.global.severe(e.getMessage());
			throw e;
		}
		Logger.global.info("'文字タグ設定'の解析処理を終了します。");
	}

	private String[] getStringsSplitedByColon(String line) throws TagconfException {
		boolean esc = false;
		int loc = 0;
		StringBuilder sb = new StringBuilder(line.length());
		
		for (char c: line.toCharArray()) {
			if (c == '\\') {
				if (esc) sb.append(c);
				esc = !esc;
			} else if (c == ':') {
				if (! esc) return new String[]{ sb.toString(), line.substring(loc + 1) };
				sb.append(c);
				esc = false;
			} else {
				if (esc) sb.append('\\');
				sb.append(c);
				esc = false;
			}
			loc++;
		}
		throw new TagconfException("タグ設定に ':' が見つかりません。");
	}
	
	private String[] parseLine(String line) throws TagconfException {
		if (line.trim().length() == 0) return null;
		
//		String[] items = line.split(":", 2);
		String[] items = getStringsSplitedByColon(line);

		String[] signs = items[0].trim().split("\\s+");

		if (items.length != 2) {
			throw new TagconfException("タグ設定に ':' が見つかりません。");
		}
		String style = items[1].trim();
		String[] ret = null;
		switch (signs.length) {
		case 2: 
			String[] temp = new String[] {style, signs[0], signs[1]};
			ret = temp;
			break;
		case 1: 
			ret = new String[2];
			ret[0] = style;      ret[1] = signs[0];
			break;
		default:
			Logger.global.warning("" + signs.length + "つの編集記号が見つかりました。2つ目以上の編集記号は無視されます。\n" + line);
		}
		return ret;
	}
	
	private void setParaList(String[] items) {
		ParagraphTag t = null;
		ParagraphSign s = null;
		
		if (items[0].startsWith(CALL_SCRIPT_MARK)) {
			t = new ParagraphTag(items[0].substring(CALL_SCRIPT_MARK.length()));
			s = items.length == 3 ?
					new ScriptParagraphSign(items[1], items[2], t) :
						new ScriptParagraphSign(items[1], t);
		} else if (items[0].startsWith(REMAIN_SIGN_MARK)) {
			t = new ParagraphTag(items[0].substring(REMAIN_SIGN_MARK.length()));
			SimpleParagraphSign ss = items.length == 3 ?
					new SimpleParagraphSign(items[1], items[2], t) :
						new SimpleParagraphSign(items[1], t);
			ss.setRemoveSign(false);
			s = ss;
		} else {
			t = new ParagraphTag(items[0]);
			s = items.length == 3 ?
				new SimpleParagraphSign(items[1], items[2], t) :
					new SimpleParagraphSign(items[1], t);
		}
		App.getInstance().getParagraphSigns().add(s);
		Logger.global.info("段落スタイル'"+ items[0] +"'には、" + 
				(items.length == 3 ? "開始タグ'" + items[1] + "'と閉じタグ'" + items[2] + "'" : 
					"開始タグ'" + items[1] + "'だけ") + "が設定されました。");
	}
	
	private void setCharList(String[] items) {
		CharacterTag t = new CharacterTag(items[0]);
		CharacterSign s = null;
		if (! items[0].startsWith(CALL_SCRIPT_MARK)) {
			s = new SimpleCharacterSign(items[1], items[2], t);
		} else {
			s = new ScriptCharacterSign(items[1], items[2], t);
		}
		App.getInstance().getCharacterSigns().add(s);
		Logger.global.info("文字スタイル'" + items[0] + "'には、開始タグ'" + 
				items[1] + "'と閉じタグ'" + items[2] + "'が設定されました。");
	}

	/**
	 * 自由置換設定の内容を解析し、List にセットします。
	 * @param property 自由置換設定の内容全文
	 */
	public void parseReplaceSetting(String property) {
		Logger.global.info("'自由置換設定'の内容を解析します。");
		String[] lines = property.split("[\n\r]+");
		for (String l : lines) {
			if (l.trim().length() == 0) continue;
			
			String[] items = l.split(":", 2);
			ReplaceTag t = new ReplaceTag(items[1].trim());
			App.getInstance().getReplaceSigns().add(new ReplaceSign(items[0].trim(), t));
		}
		Logger.global.info("'自由置換設定'の解析処理を終了します。");
	}
}
