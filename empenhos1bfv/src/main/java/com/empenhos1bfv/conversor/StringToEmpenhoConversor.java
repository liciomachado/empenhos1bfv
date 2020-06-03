package com.empenhos1bfv.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.repository.EmpenhoRepository;

@Component
public class StringToEmpenhoConversor implements Converter<String, Empenho> {

	@Autowired
	EmpenhoRepository empenhoRespository;
	
	@Override
	public Empenho convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		int id = Integer.valueOf(text);
		return empenhoRespository.findById(id).get();
	}

}
