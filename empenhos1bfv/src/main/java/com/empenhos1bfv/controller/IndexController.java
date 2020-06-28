package com.empenhos1bfv.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.empenhos1bfv.dto.EmpenhoDTO;
import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Empresa;
import com.empenhos1bfv.model.Graduacao;
import com.empenhos1bfv.model.Notafiscal;
import com.empenhos1bfv.model.Protocolo;
import com.empenhos1bfv.model.Secao;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoDTORepository;
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
	@Autowired
	EmpenhoDTORepository dtoRepository;
	@Autowired
	Optional<Empenho> empenho;

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
	public List<EmpenhoDTO> getEmpenhos() {
		return dtoRepository.findAllWithoutFIle();
	}

	@ModelAttribute("empresas")
	public List<Empresa> getEmpresas() {
		return empresaRepository.findAll();
	}

	@ModelAttribute("secoes")
	public List<Secao> getSecoes() {
		return secaoRepository.findAll();
	}
	@ModelAttribute("graduacoes")
	public Graduacao[] getGraduacoes() {
		return Graduacao.values();
	}
	@RequestMapping("/empenhos")
	public ModelAndView pendentes() {
		ModelAndView mv = new ModelAndView("listaEmpenhos");
		List<EmpenhoDTO> empenhos = dtoRepository.findAllWithoutFIle();
		mv.addObject("empenhosPendentes", empenhos);
		return mv;
	}
	@PostMapping("/empenhos")
	public ModelAndView filtroEmpenho(@RequestParam String action) {
		ModelAndView mv = new ModelAndView("listaEmpenhos");
		List<EmpenhoDTO> empenhos = dtoRepository.findAllWithoutFIle();
		switch (action) {
		case "todos":
			empenhos = dtoRepository.findAllWithoutFIle();
			break;
		case "quitados":
			empenhos = dtoRepository.findQuitados();
			break;
		case "pendentes":
			empenhos = dtoRepository.findEmpenhosPendentes();
			break;
		case "vencidos":
			empenhos = dtoRepository.findVencidos();
			break;
		case "rp":
			empenhos = dtoRepository.findRestosAPagar();
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
		notafiscal.setIdNotaFiscal(0);
		List<EmpenhoDTO> empenhos = dtoRepository.findAllWithoutFIle();
		mv.addObject("empenhos", empenhos);
		return mv;
	}
	
	@RequestMapping(value = "/novanota/{id}", method = RequestMethod.GET)
	public ModelAndView alteraNota(@PathVariable("id") int id,Notafiscal notafiscal) {
		ModelAndView mv = new ModelAndView("adicionaNotaFiscal");
		notafiscal =  notaFiscalRepository.findById(id).get();
		List<Empenho> empenhos = empenhoRepository.findAll();
		mv.addObject("notafiscal", notafiscal);
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
	@GetMapping("/conta")
	public ModelAndView alteraConta(Usuario usuario,@AuthenticationPrincipal User u) {
		usuario = usuarioRepository.findByNome(u.getUsername()).get();
		ModelAndView mv = new ModelAndView("conta");
		mv.addObject("secoes", getSecoes());
		mv.addObject("usuario", usuario);
		return mv;
	}
	@GetMapping("/atualizavalores")
	public String atualizaValores(){
		List<Object[]> emps = dtoRepository.atualizaPendentes();
		for (Object[] at : emps) {
			empenho = empenhoRepository.findById(Integer.parseInt(at[0].toString()));
			Empenho emp = empenho.get();
			
			if (emp.getSaldoUtilizado() == 0 && at[2] != null) {
				emp.setSaldoUtilizado(Double.parseDouble(at[2].toString()));
			}
			if (emp.getSaldo() == 0 && at[3] != null) {
				emp.setSaldo(Double.parseDouble(at[3].toString()));
			}
			if(emp.getSaldoUtilizado() == 0) {
				emp.setSaldo(emp.getValorTotal());
			}
			empenhoRepository.save(emp);
			System.out.println(at[0]);
			System.out.println(at[1]);
			System.out.println(at[2]);
			System.out.println(at[3]);
			System.out.println("-------------------------------");
		}
		List<Object[]> emps2 = dtoRepository.atualizaPendentes();
		for (Object[] at : emps2) {
			empenho = empenhoRepository.findById(Integer.parseInt(at[0].toString()));
			Empenho emp = empenho.get();
			
			if (emp.getSaldoUtilizado() == 0 && at[2] != null) {
				emp.setSaldoUtilizado(Double.parseDouble(at[2].toString()));
			}
			if (emp.getSaldo() == 0 && at[3] != null) {
				emp.setSaldo(Double.parseDouble(at[3].toString()));
			}
			if(emp.getSaldoUtilizado() == 0) {
				emp.setSaldo(emp.getValorTotal());
			}
			empenhoRepository.save(emp);
			System.out.println(at[0]);
			System.out.println(at[1]);
			System.out.println(at[2]);
			System.out.println(at[3]);
			System.out.println("-------------------------------");
		}
		return "redirect:/";
	}
}
