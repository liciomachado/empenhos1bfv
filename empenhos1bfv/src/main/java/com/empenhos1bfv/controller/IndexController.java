package com.empenhos1bfv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Empresa;
import com.empenhos1bfv.model.Notafiscal;
import com.empenhos1bfv.model.Protocolo;
import com.empenhos1bfv.model.Secao;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoRepository;
import com.empenhos1bfv.repository.EmpresaRepository;
import com.empenhos1bfv.repository.NotaFiscalRepository;
import com.empenhos1bfv.repository.ProtocoloRepository;
import com.empenhos1bfv.repository.SecaoRepository;
import com.empenhos1bfv.repository.UsuarioRepository;

@Controller
public class IndexController implements ErrorController {

	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	EmpenhoRepository empenhoRepository;
	@Autowired
	NotaFiscalRepository notaFiscalRepository;
	@Autowired
	SecaoRepository secaoRepository;
	@Autowired
	ProtocoloRepository protocoloRepository;
	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/")
	public ModelAndView home(Empenho empenho) throws IOException {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	// abrir pagina login
	@GetMapping({"/login"})
	public String login() {
		
		return "login";
	}
	// login invalido
	@GetMapping({"/login-error"})
	public String loginError(ModelMap model) {
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Crendenciais inválidas!");
		model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
		model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativados.");
		return "login";
	}	
	
	@ModelAttribute("empenhosNavbar")
	public List<Empenho> getEmpenhos() {
		return empenhoRepository.findAll();
	}

	@ModelAttribute("empresas")
	public List<Empresa> getEmpresas() {
		return empresaRepository.findAll();
	}

	@ModelAttribute("secoes")
	public List<Secao> getSecoes() {
		return secaoRepository.findAll();
	}

	@RequestMapping("/empenhos")
	public ModelAndView pendentes() {
		ModelAndView mv = new ModelAndView("listaEmpenhos");
		List<Empenho> empenhos = empenhoRepository.findAll();
		mv.addObject("empenhosPendentes", empenhos);
		return mv;
	}
	@PostMapping("/empenhos")
	public ModelAndView filtroEmpenho(@RequestParam String action) {
		ModelAndView mv = new ModelAndView("listaEmpenhos");
		List<Empenho> empenhos = empenhoRepository.findAll() ;
		switch (action) {
		case "todos":
			empenhos = empenhoRepository.findAll();
			break;
		case "quitados":
			empenhos = empenhoRepository.findQuitados();
			break;
		case "pendentes":
			empenhos = empenhoRepository.findEmpenhosPendentes();
			break;
		case "vencidos":
			empenhos = empenhoRepository.findVencidos();
			break;
		case "rp":
			empenhos = empenhoRepository.findRestosAPagar();
			break;
		}
		mv.addObject("empenhosPendentes", empenhos);
		return mv;
	}
	
	@RequestMapping("/notasfiscais")
	public ModelAndView recebidos() {
		ModelAndView mv = new ModelAndView("listaNotasFiscais");
		List<Notafiscal> notas = notaFiscalRepository.findEmpenhosRecebidos();
		mv.addObject("notasRecebidos", notas);
		return mv;
	}

	@RequestMapping("/protocolo")
	public ModelAndView protocolo(@AuthenticationPrincipal User u) {
		ModelAndView mv = new ModelAndView("listaProtocolo");
		Usuario user = usuarioRepository.findByNome(u.getUsername()).get();
		List<Notafiscal> notas = notaFiscalRepository.notasProtocolar(user.getSecao().getIdSecao());
		mv.addObject("notasProtocolar", notas);
		return mv;
	}

	@RequestMapping("/protocolorecebido")
	public ModelAndView protocoloRecebido(@AuthenticationPrincipal User u) {
		ModelAndView mv = new ModelAndView("listaProtocoloRecebido");
		Usuario usuario = usuarioRepository.findByNome(u.getUsername()).get();
		List<Protocolo> notas = protocoloRepository.notasProtocoRecebido(1, usuario.getSecao().getIdSecao());
		mv.addObject("notasProtocolo", notas);
		return mv;
	}

	@RequestMapping("/minhasnotas")
	public ModelAndView minhasNotas(@AuthenticationPrincipal User u) {
		ModelAndView mv = new ModelAndView("minhasNotasFiscais");
		Usuario usuario = usuarioRepository.findByNome(u.getUsername()).get();
		List<Notafiscal> notas = notaFiscalRepository.findNotasPorSecao(usuario.getSecao().getIdSecao());
		mv.addObject("minhasNotas", notas);
		return mv;
	}
	
	@RequestMapping(value = "/novoempenho", method = RequestMethod.GET)
	public ModelAndView adicionaEmpenho(Empenho empenho) {
		ModelAndView mv = new ModelAndView("adicionaEmpenho");
		List<Empresa> empresas = empresaRepository.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}

	@RequestMapping(value = "/novanota", method = RequestMethod.GET)
	public ModelAndView adicionaNota(Notafiscal notafiscal) {
		ModelAndView mv = new ModelAndView("adicionaNotaFiscal");
		List<Empenho> empenhos = empenhoRepository.findAll();
		mv.addObject("empenhos", empenhos);
		return mv;
	}

	@RequestMapping("/error")
	public Object error(HttpServletRequest request, HttpServletResponse response) {
		// place your additional code here (such as error logging...)
		if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())) {
			response.setStatus(HttpStatus.OK.value()); // optional.
			return "forward:/error.html"; // forward to static SPA html resource.
		} else {
			return ResponseEntity.notFound().build(); // or your REST 404 blabla...
		}
	}

	@Override
	public String getErrorPath() {
		return "error";
	}
	
	// acesso negado
	@GetMapping({"/acesso-negado"})
	public String acessoNegado(ModelMap model, HttpServletResponse resp) {
		model.addAttribute("status", resp.getStatus());
		model.addAttribute("error", "Acesso Negado");
		model.addAttribute("message", "Você não tem permissão para acesso a esta área ou ação.");
		return "error";
	}
}
