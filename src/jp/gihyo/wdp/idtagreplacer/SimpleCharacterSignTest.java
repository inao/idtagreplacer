package jp.gihyo.wdp.idtagreplacer;


import org.junit.Test;
import org.junit.Assert;

public class SimpleCharacterSignTest {
	@Test public void 
	同一のタグ変換が二回以上行えること
	() {
		CharacterTag t = new CharacterTag("Style");
		CharacterSign s = new SimpleCharacterSign("◆test◆", "◆/test◆", t);
		String testBuf = 
			"あいうえおかきくけこ◆test◆さしすせそ◆/test◆たちつてと◆test◆なにぬねの◆/test◆はひふへほ";
		String expected =
			"あいうえおかきくけこ<CharStyle:Style>さしすせそ<CharStyle:>たちつてと<CharStyle:Style>なにぬねの<CharStyle:>はひふへほ";
		String ret = s.convertSign(testBuf);
		Assert.assertTrue(ret.equals(expected));
		
		s = new SimpleCharacterSign("【《", "》】", t);
		testBuf = 
			"　まず、ユーザは、サービスごとにアカウントの作成／管理をする煩わしさから解放され、OpenID対応サイトであれば、自身の【【OpenID URL】】を入力するだけでログインすることが可能になります。また、OpenIDの仕様は、OpenIDコミュニティやMLでの議論をベースに作成され公開されるため、OpenIDプロバイダ（【《OpenID Provider》】、以下【【OP】】）、OpenIDによる認証を提供するサービスプロバイダ（【《Relying Party》】、以下【【RP】】）のどちらも私達自身で立ち上げる事ができます。そのため、私達の個人情報が特定の一社に集中するという心配もありません。";
		expected =
			"　まず、ユーザは、サービスごとにアカウントの作成／管理をする煩わしさから解放され、OpenID対応サイトであれば、自身の【【OpenID URL】】を入力するだけでログインすることが可能になります。また、OpenIDの仕様は、OpenIDコミュニティやMLでの議論をベースに作成され公開されるため、OpenIDプロバイダ（<CharStyle:Style>OpenID Provider<CharStyle:>、以下【【OP】】）、OpenIDによる認証を提供するサービスプロバイダ（<CharStyle:Style>Relying Party<CharStyle:>、以下【【RP】】）のどちらも私達自身で立ち上げる事ができます。そのため、私達の個人情報が特定の一社に集中するという心配もありません。";
		ret = s.convertSign(testBuf);
		Assert.assertTrue(ret.equals(expected));
	}
}
