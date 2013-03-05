package jp.gihyo.wdp.idtagreplacer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class SimpleTagconfParserTest {
	SimpleTagconfParser parser = null;
	String paraconf = null;
	ParagraphSign pl = null;

	@Before
	public void _セットアップ() {
		parser = new SimpleTagconfParser();
	}

	@Test
	public void 普通の記述が正しくパースできること_行の設定() throws TagconfException {
		paraconf = "start_sign   end_sign:style_name";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("start_sign"));
		assertTrue(pl.endSign.equals("end_sign"));
		assertTrue(pl.tag.getTagName().equals("style_name"));

		paraconf = "開始記号\t\t終了記号\t\t:スタイル名";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("開始記号"));
		assertTrue(pl.endSign.equals("終了記号"));
		assertTrue(pl.tag.getTagName().equals("スタイル名"));

		paraconf = "行頭記号 : スタイル名";
		parser.parseParaSetting(paraconf);
		pl = App.getInstance().getParagraphSigns().getLast();
		assertTrue(pl.startSign.equals("行頭記号"));
		assertTrue(pl.endSign == null);
		assertTrue(pl.tag.getTagName().equals("スタイル名"));
	}

	@Test
	public void 空行は無視されるだけで例外を送出しないこと() throws TagconfException {
		paraconf = "";
		parser.parseParaSetting(paraconf);

		paraconf = " ";
		parser.parseParaSetting(paraconf);

		paraconf = "\n";
		parser.parseParaSetting(paraconf);
	}

	@Test
	public void コロンがない場合は例外を送出すること() {
		paraconf = "行頭記号  スタイル名";
		try {
			parser.parseParaSetting(paraconf);
			fail("エラーが送出されませんでした");
		} catch (TagconfException e) {
			System.out.println(e.getMessage());
		}
	}
}
