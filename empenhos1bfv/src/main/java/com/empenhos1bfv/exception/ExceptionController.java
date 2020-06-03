package com.empenhos1bfv.exception;

import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javassist.NotFoundException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(UsernameNotFoundException.class)
	public ModelAndView usuarioNaoEncontradoException(UsernameNotFoundException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", 404);
		model.addObject("error", "Operação não pode ser realizada.");
		model.addObject("message", ex.getMessage());
		return model;
	}
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView ValorNaoEncontradoException(NoSuchElementException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", 404);
		model.addObject("error", "Operação não pode ser realizada.");
		model.addObject("message", ex.getMessage());
		return model;
	}
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView ValorNaoEncontradoException(NotFoundException ex) {
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", 404);
		model.addObject("error", "Página não encontrada");
		model.addObject("message", ex.getMessage());
		return model;
	}
}
