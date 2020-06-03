package com.empenhos1bfv.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.empenhos1bfv.model.Secao;
import com.empenhos1bfv.repository.SecaoRepository;

public class StringToSecaoConversor implements Converter<String, Secao> {

	@Autowired
	SecaoRepository secaoRepository;
	
	@Override
	public Secao convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		int id = Integer.valueOf(text);
		return secaoRepository.findById(id).get();
	}

}
