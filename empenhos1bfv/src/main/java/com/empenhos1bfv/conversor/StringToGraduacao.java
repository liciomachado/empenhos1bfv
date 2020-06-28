package com.empenhos1bfv.conversor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.empenhos1bfv.model.Graduacao;

@Component
public class StringToGraduacao implements Converter<String, Graduacao> {

	@Override
	public Graduacao convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		return Graduacao.valueOf(text); 
	}
}
