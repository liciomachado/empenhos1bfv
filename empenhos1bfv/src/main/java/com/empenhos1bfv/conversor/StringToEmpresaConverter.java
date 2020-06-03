package com.empenhos1bfv.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.empenhos1bfv.model.Empresa;
import com.empenhos1bfv.repository.EmpresaRepository;

@Component
public class StringToEmpresaConverter implements Converter<String, Empresa> {

	@Autowired
	EmpresaRepository empresaRepository;
	
	@Override
	public Empresa convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		int id = Integer.valueOf(text);
		return empresaRepository.findById(id).get();
	}

}
