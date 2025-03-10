package com.empenhos1bfv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.empenhos1bfv.dto.EmpenhoDTO;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoDTORepository;
import com.empenhos1bfv.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	EmpenhoDTORepository dtoRepository;
	
	@ModelAttribute("empenhosNavbar")
	public List<EmpenhoDTO> getEmpenhos() {
		return dtoRepository.findAllWithoutFIle();
	}
	@PostMapping("/update")
	public String atualiza(@Valid Usuario usuario) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		return "redirect:/";
	}
	@PostMapping("/novouser")
	public String criaUser(@Valid Usuario usuario,ModelMap model) {
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuario.setTipoUser("1");
		usuarioRepository.save(usuario);
		model.addAttribute("success", "sucesso");
		return "redirect:/redirecionalogin";
	}
}
