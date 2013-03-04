package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;

/**
 * 段落記号の情報を保持し、タグ変換処理を行うクラスです。
 */
public class SimpleParagraphSign extends ParagraphSign {
	/* タグ置換の際に、元の記号を削除するかどうか */
	private boolean removeSign = true;
	
	/**
	 * 開始記号だけを持つ段落記号オブジェクトを生成する。
	 * @param sign 開始記号
	 * @param tag 段落タグ
	 */
	public SimpleParagraphSign(String sign, ParagraphTag tag) {
		super(sign, tag);
	}
	
	public SimpleParagraphSign(String startSign, String endSign, ParagraphTag tag) {
		super(startSign, endSign, tag);
	}
	
	/**
	 * タグ置換の際に、元の記号を削除するかどうかを設定する。
	 * @param removeSign false を渡すと記号を残す。
	 */
	public void setRemoveSign(boolean removeSign) {
		this.removeSign = removeSign;
	}
	
	@Override
	protected String whenEndSignMuches(String line) throws IOException, SourceParserException {
		if (! endSign.equals(line)) {
			line = removeSign ? 
					line.substring(0, line.length() - endSign.length()) : line;
//			App.out.println(line);
			App.getInstance().getPrintController().addCommand("print");
		}
//		App.getInstance().getActiveParagraphTag().removeLast();
		App.getInstance().getPrintController().addCommand("remove last para");
		return line;
	}

	@Override
	protected String whenStartSignMuches(String line) throws IOException, SourceParserException {
		App.getInstance().getActiveParagraphTag().add(tag);
		line = removeSign ? line.substring(startSign.length()) : line;
		if (hasEndSign()) {
//			if (line.trim().length() != 0) App.out.println(line);
			if (line.trim().length() != 0) App.getInstance().getPrintController().addCommand("print");
		} else {
//			App.out.println(line);
//			App.getInstance().getActiveParagraphTag().removeLast();
			App.getInstance().getPrintController().addCommand("print");
			App.getInstance().getPrintController().addCommand("remove last para");
		}
		return line;
	}
}
