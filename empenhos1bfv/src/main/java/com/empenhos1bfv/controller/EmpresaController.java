package com.empenhos1bfv.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.empenhos1bfv.dto.EmpenhoDTO;
import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Empresa;
import com.empenhos1bfv.model.ObservacoesEmpresa;
import com.empenhos1bfv.repository.EmpenhoDTORepository;
import com.empenhos1bfv.repository.EmpenhoRepository;
import com.empenhos1bfv.repository.EmpresaRepository;
import com.empenhos1bfv.repository.ObservacaoEmpresaRepository;
import com.empenhos1bfv.repository.UsuarioRepository;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {
	
	private static Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	ObservacaoEmpresaRepository observacaoEmpresaRepository;	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	EmpenhoRepository empenhoRepository;
	@Autowired
	EmpenhoDTORepository dtoRepository;

	
	@ModelAttribute("empenhosNavbar")
	public List<EmpenhoDTO> getEmpenhos() {
		return dtoRepository.findAllWithoutFIle();
	}
	
	@GetMapping("/")
	public ModelAndView empresas() {
		ModelAndView mv = new ModelAndView("empresas");
		List<Empresa> empresas = empresaRepository.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
	@PostMapping("/save")
	public ResponseEntity<?> salvarEmpresa(Empresa empresa){
		empresaRepository.save(empresa);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/saveobs")
	public String salvarObservacaoEmpresa(@Valid ObservacoesEmpresa obsEmpresa, @AuthenticationPrincipal User u){
		
		obsEmpresa.setDataObs(LocalDate.now());
		obsEmpresa.setUsuario(usuarioRepository.findByNome(u.getUsername()).get());
		observacaoEmpresaRepository.save(obsEmpresa);
		
		return "redirect:/empresas/"+obsEmpresa.getEmpresa().getIdEmpresa();
	}
	@GetMapping("/{id}")
	public ModelAndView detalheEmpresa(@PathVariable("id") int id, ObservacoesEmpresa observacao) {
		observacao.setEmpresa(empresaRepository.findById(id).get());
		double valorTotal = 0;
		if(empresaRepository.getValorTotalPorEmpresa(id) != null) {
			valorTotal = empresaRepository.getValorTotalPorEmpresa(id);
		}
		int tempoMedio = 0;
		if(empresaRepository.getTempoMedioPorEmpresa(id) != null){
			tempoMedio = empresaRepository.getTempoMedioPorEmpresa(id);
		}
		DecimalFormat formatador = new DecimalFormat("0.00");
		Empresa empresa = empresaRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("detalheEmpresa");
		List<Empenho> empenhos = empenhoRepository.findByEmpresa(id);
		List<ObservacoesEmpresa> ObsEmpresa = empresaRepository.findById(id).get().getObservacoesEmpresa();
		mv.addObject("valorTotal", formatador.format(valorTotal));
		mv.addObject("tempoMedio", tempoMedio);
		mv.addObject("empresa", empresa);
		mv.addObject("obsEmpresa", ObsEmpresa);
		mv.addObject("empenhos", empenhos);
		mv.addObject("observacao", observacao);
		return mv;
	}
}
