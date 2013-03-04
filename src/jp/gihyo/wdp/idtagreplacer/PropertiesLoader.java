package jp.gihyo.wdp.idtagreplacer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Properties;
import java.util.logging.Logger;

class PropertiesLoader {
	private static PropertiesLoader instance = null;
	private Properties properties = null;

	/**
	 * PropertiesLoader オブジェクトを初期化して返します。
	 * 必ず新規のオブジェクトを返します。既存のオブジェクトを返すわけではありません。
	 * @param fileName タグ設定ファイルのパス
	 * @return PropertiesLoader オブジェクト
	 * @throws IOException
	 */
	static PropertiesLoader getNewInstance(String fileName) throws IOException {
		instance = new PropertiesLoader(fileName);
		return instance;
	}
	
	/**
	 * 既存の PropertiesLoader オブジェクトを返します。
	 * 既存のオブジェクトがない場合（まだ getNewInstance を呼んでいない場合）は
	 * null を返します。
	 */
	static PropertiesLoader getInstance() {
		return instance;
	}
	
	private PropertiesLoader(String fileName) throws IOException {
		Logger.global.info("タグ設定ファイル'" + fileName + "'を読み込みます。");
		
		properties = new Properties() {
				private static final long serialVersionUID = 1643376174439610083L;
				public String getProperty(String key) {
					Logger.global.info("プロパティ'" + key + "'の値を取り出します。");
					return super.getProperty(key);
				}
		};
		
		InputStream s = new FileInputStream(fileName);
		properties.loadFromXML(s);
		s.close();
		Logger.global.info("タグ設定ファイルの読み込みを終了します。");
	}
	
	Properties getProperties() {
		return properties;
	}
}

class PropertiesInfo {
	String lineFeedCode = null;
	String savefileFormat = null;
	String defaultParagraphName = null;
	Charset charset = null;
	
	String charsetName = null;
	String paraSettings = null;
	String charSettings = null;
	String freeSettings = null;
}

class PropertiesLoaderHelper {
	private void getValues(Properties properties, PropertiesInfo info) {
		info.charsetName = properties.getProperty("エンコード");
		info.lineFeedCode = properties.getProperty("改行コード");
		info.savefileFormat = properties.getProperty("保存ファイル名");
		info.defaultParagraphName = properties.getProperty("段落タグの既定値");
		info.paraSettings = properties.getProperty("段落タグ設定");
		info.charSettings = properties.getProperty("文字タグ設定");
		info.freeSettings = properties.getProperty("自由置換設定");
	}
	
	private void readTagsSettings(SimpleTagconfParser parser, PropertiesInfo info) throws TagconfException {
		if (info.paraSettings != null) parser.parseParaSetting(info.paraSettings);
		if (info.charSettings != null) parser.parseCharSetting(info.charSettings);
		if (info.freeSettings != null) parser.parseReplaceSetting(info.freeSettings);
	}
	
	private void readJavaScriptSettings(Properties prop, PropertiesInfo info) throws TagconfException {
		// JavaScript を実行するプロパティー名のリスト
		String[] settings = {
				"黒丸数字書式", "白丸数字書式", "アルファベット", 
				"黒四角数字書式", "その他自由設定"
		};

		RhinoLauncher r = new RhinoLauncher();
		r.addBasicAPI();
		r.addPreCode("importClass(Packages.jp.gihyo.wdp.idtagreplacer.ReplaceSign);");
		r.addPreCode("importClass(Packages.jp.gihyo.wdp.idtagreplacer.ReplaceTag);");
		r.addPreCode("var encode = '" + info.charset.name() + "';");
		try {
			for (String n: settings)
				r.launch(prop.getProperty(n, ""));
		} catch (Exception e) {
			throw new TagconfException("tagconf.xml の JavaScript を実行中にエラーが発生しました。\nメッセージ : " + e.getMessage());
		}
	}
	
	public PropertiesInfo readProperties(String filePath) throws IOException, TagconfException {
		
		PropertiesInfo info = new PropertiesInfo();
		Properties prop = PropertiesLoader.getNewInstance(filePath).getProperties();
		
		getValues(prop, info);
		
		if (info.charsetName == null) {
			Logger.global.severe("エンコードの設定が見つかりません。");
			throw new TagconfException("エンコードの設定が見つかりません");
		}
		if (info.defaultParagraphName == null) {
			throw new TagconfException("段落タグの既定値が設定されていません");
		}

		try {
			info.charset = Charset.forName(info.charsetName);
		} catch (IllegalCharsetNameException icne) {
			Logger.global.severe("指定されたエンコード'" + info.charsetName + "'は不適切な値です。");
			throw new TagconfException("エンコードの指定が不適切です");
		} catch (UnsupportedCharsetException uce) {
			Logger.global.severe("指定されたエンコード'" + info.charsetName + "'はサポートされていません。");
			throw new TagconfException("サポートされていないエンコードです");
		}
		
		App.getInstance().getActiveParagraphTag().add(new ParagraphTag(info.defaultParagraphName));
		readTagsSettings(new SimpleTagconfParser(), info);
		readJavaScriptSettings(prop, info);
		
		return info;
	}
	
}
