package jp.gihyo.wdp.idtagreplacer;

import java.io.IOException;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

public class SourceParserImpl implements SourceParser {
	private long lineNumber = 0;

	public void parse(String line) throws IOException, SourceParserException {
		lineNumber++;
		try {
			App.getInstance().makeNewPrintController();
			line = getTagEscapedLine(line);
			line = addParagraphTag(line);
			line = addCharacterTag(line);
			line = addReplaceTag(line);

			App.getInstance().getPrintController().execute(line);
		} catch (IOException ioe) {
			Logger.global.severe(ioe.getMessage());
			throw ioe;
		} catch (SourceParserException spe) {
			Logger.global.severe("原稿ファイルの解析中にエラーが発生しました。\n行番号: " + lineNumber);
			throw spe;
		}
	}
	
	/*
	 * <, > をエスケープ
	 */
	private String getTagEscapedLine(String line) {
		line = line.replaceAll("([<>])", "<005C>$1");
		return line;
	}

	private String addReplaceTag(String line) {
		ListIterator<ReplaceSign> it = 
			App.getInstance().getReplaceSigns().listIterator();
		while (it.hasNext()) {
			ReplaceSign sign = it.next();
			ReplaceTag tag = sign.getTag();
			line = line.replace(sign.getSign(), tag.getReplacement());
		}
		return line;
	}

	private String addCharacterTag(String line) {
		ListIterator<CharacterSign> it = 
			App.getInstance().getCharacterSigns().listIterator();
		while (it.hasNext()) {
			CharacterSign sign = it.next();
			line = sign.convertSign(line);
		}
		return line;
	}
	
	private String addParagraphTag(String line) throws IOException, SourceParserException, NoSuchElementException {
		ListIterator<ParagraphSign> it = 
			App.getInstance().getParagraphSigns().listIterator();
		PrintController ctl = App.getInstance().getPrintController();
		while (it.hasNext()) {
			ParagraphSign sign = it.next();
//			if (sign.convertSign(line)) return;
			line = sign.convertSign(line);
			if (ctl.hasCommands()) return line;
		}
//		App.out.println(line);
		ctl.addCommand("print");
		return line;
	}
}
