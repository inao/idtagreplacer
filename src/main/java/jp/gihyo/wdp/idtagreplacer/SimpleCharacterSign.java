package jp.gihyo.wdp.idtagreplacer;

public class SimpleCharacterSign extends CharacterSign {
	public SimpleCharacterSign(String startSign, String endSign,
			CharacterTag tag) {
		super(startSign, endSign, tag);
	}

	@Override
	public String convertSign(String line) {
		line = line.replace(startSign, tag.getStartTagString());
		if (endSign != null) {
			line = line.replace(endSign, tag.getEndTagString());
		}
		return line;
	}
}
