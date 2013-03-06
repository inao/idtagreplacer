package jp.gihyo.wdp.idtagreplacer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * サンプルデータを利用した結合テストです。
 * 
 * @author taichi
 */
@RunWith(Parameterized.class)
public class AppTest {

	String testdata;

	Charset charset = null;
	String lineFeedCode = null;

	public AppTest(String testdata) {
		this.testdata = testdata;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> generateData() {
		return Arrays.asList(new Object[][] { { "sjis" } });
	}

	@Before
	public void setUp() throws Exception {
		String filePath = fixture("tagconf.xml");
		PropertiesInfo info = new PropertiesLoaderHelper()
				.readProperties(filePath);
		this.lineFeedCode = info.lineFeedCode;
		this.charset = info.charset;

		File build = new File("build");
		if (build.exists() == false && build.mkdir() == false) {
			throw new AssertionError();
		}
	}

	String fixture(String suffix) {
		return "src/test/resources/AppTest/" + this.testdata + "/" + suffix;
	}

	String readLines(Path p) throws Exception {
		return new String(Files.readAllBytes(p), this.charset);
	}

	private String getStartTagString() {
		String c = charset.name();
		if (c.equals("UTF-8"))
			return "<UNICODE-MAC>";
		else if (c.equals("Shift_JIS"))
			return "<SJIS-MAC>";
		else
			return null;
	}

	@Test
	public void サンプルデータを使った変換が適切に実行される事() throws Exception {
		String sourceFile = fixture("sample.txt");
		File actual = new File("build/sample.actual." + this.testdata + ".txt");
		convertToInao(sourceFile, actual);

		String expectedLines = readLines(Paths
				.get(fixture("sample.expected.txt")));
		String actualLines = readLines(actual.toPath());

		String[] el = expectedLines.split(App.out.getLineFeedCode());
		String[] al = actualLines.split(App.out.getLineFeedCode());

		assertEquals(el.length, al.length);

		for (int i = 0, l = el.length; i < l; i++) {
			assertEquals(i + 1 + "行目で不一致", el[i], al[i]);
		}
	}

	private void convertToInao(String sourceFile, File output) throws Exception {
		SourceReader sr = new SourceReader(sourceFile, this.charset);
		String startTagLine = getStartTagString();
		assertNotNull(startTagLine);

		App.out = new Printer(output, this.charset);
		App.out.setLineFeedCode(this.lineFeedCode);
		App.out.print(startTagLine);
		App.out.print(App.out.getLineFeedCode());

		SourceParser p = new SourceParserImpl();
		sr.readWithParser(p);
		sr.close();
		App.out.close();
	}
}
