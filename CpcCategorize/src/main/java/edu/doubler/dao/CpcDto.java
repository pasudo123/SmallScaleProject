package edu.doubler.dao;

public class CpcDto {
	private String code = null;
	private String originalText = null;
	private String translationText = null;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOriginalText() {
		return originalText;
	}
	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
	public String getTranslationText() {
		return translationText;
	}
	public void setTranslationText(String translationText) {
		this.translationText = translationText;
	}
}
