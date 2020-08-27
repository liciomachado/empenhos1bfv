package com.empenhos1bfv.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empenhos1bfv.dto.EmpenhoDTO;
import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Empresa;
import com.empenhos1bfv.model.Notafiscal;
import com.empenhos1bfv.model.Observacoes;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoDTORepository;
import com.empenhos1bfv.repository.EmpenhoRepository;
import com.empenhos1bfv.repository.EmpresaRepository;
import com.empenhos1bfv.repository.NotaFiscalRepository;
import com.empenhos1bfv.repository.ObservacaoRepository;
import com.empenhos1bfv.repository.UsuarioRepository;
import com.empenhos1bfv.service.Mailer;


@Controller
@RequestMapping("/empenho")
public class EmpenhoController {

	private static Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	EmpenhoRepository empenhoRepository;
	@Autowired
	EmpresaRepository empresaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	NotaFiscalRepository notaFiscalRepository;
	@Autowired
	ObservacaoRepository obsRepository;
	@Autowired
	Mailer mailer;
	@Autowired
	EmpenhoDTORepository dtoRepository;

	
	@ModelAttribute("empenhosNavbar")
	public List<EmpenhoDTO> getEmpenhos() {
		return dtoRepository.findAllWithoutFIle();
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveEmpenhoComEmail(@Valid Empenho empenho,MultipartFile file, BindingResult result,
			@RequestParam String numeroEmpenho,
			@RequestParam Double valorTotal,
			@RequestParam String destino,
			@RequestParam String msgComplementar,
			@RequestParam int empresa, @AuthenticationPrincipal User u) throws IOException, MessagingException {
		
		Empresa emp = new Empresa();
		emp = empresaRepository.findById(empresa).get();
		empenho.setEmpresa(emp);
		
		empenho.setNumeroEmpenho(numeroEmpenho);
		empenho.setValorTotal(valorTotal);
		empenho.setSaldo(valorTotal);
		empenho.setDestino(destino);
		empenho.setEmpenhoDigitalizado(file.getBytes());
		empenho.setEtapa(3);
		
		Usuario user = new Usuario();
		user = usuarioRepository.findByNome(u.getUsername()).get();
		empenho.setUsuario(user);
		
		empenho.setDataEmpenho(LocalDate.now());
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		log.info("Empenho {}",empenho.toString());
		empenhoRepository.save(empenho);
		
		//---------------------------------------ENVIO DO EMAIL----------------------------------------------------------------
		long tempoInicio = System.currentTimeMillis();
		mailer.enviarComAnexo(empenho, msgComplementar, file);
		mailer.enviarNotificacaoAtraso(empenho.getEmpresa().getEmail(), empenho);
		System.out.println("Tempo Total de envio: "+((System.currentTimeMillis()-tempoInicio)/1000)+" segundos....");
		//---------------------------------------FIM ENVIO DO EMAIL------------------------------------------------------------
		return ResponseEntity.ok().build();
	}
	@PostMapping("/savesememail")
	public ResponseEntity<?> saveEmpenhoSemEmail(@Valid Empenho empenho,MultipartFile file, BindingResult result,
			@RequestParam String numeroEmpenho,
			@RequestParam Double valorTotal,
			@RequestParam String destino,
			@RequestParam int empresa, @AuthenticationPrincipal User u) throws IOException, MessagingException {
		
		Empresa emp = new Empresa();
		emp = empresaRepository.findById(empresa).get();
		empenho.setEmpresa(emp);
		
		empenho.setNumeroEmpenho(numeroEmpenho);
		empenho.setValorTotal(valorTotal);
		empenho.setSaldo(valorTotal);
		empenho.setDestino(destino);
		empenho.setEmpenhoDigitalizado(file.getBytes());
		empenho.setEtapa(3);
		
		Usuario user = new Usuario();
		user = usuarioRepository.findByNome(u.getUsername()).get();
		empenho.setUsuario(user);
		
		empenho.setDataEmpenho(LocalDate.now());
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		log.info("Empenho {}",empenho.toString());
		empenhoRepository.save(empenho);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/atualizaempenho")
	public String atualizaEmpenho(	@RequestParam int idEmpenho,
									@RequestParam String numeroEmpenho,
									@RequestParam Double valorTotal,
									@RequestParam String destino) {
		Empenho empenho = empenhoRepository.findById(idEmpenho).get();
		empenho.setNumeroEmpenho(numeroEmpenho);
		empenho.setValorTotal(valorTotal);
		empenho.setDestino(destino);
		empenhoRepository.save(empenho);
		return "redirect:/empenho/"+idEmpenho;
	}
	
	@PostMapping("/functionjson")
	public void functionJSON(@RequestParam String id, HttpServletResponse response) throws IOException {
		if (id != null || id != "") {
			int id2 = Integer.parseInt(id);
			Empenho empenho = new Empenho();
			empenho = empenhoRepository.findById(id2).get();
			double valor = empenho.getValorTotal();
			String empresa = empenho.getEmpresa().getNome();
			response.getWriter().println(empresa + "/" + valor);
		}
	}
	@PostMapping("/functionempresa")
	public void functionJSONEmpresa(@RequestParam String id, HttpServletResponse response) throws IOException {
		if (id != null || id != "") {
			int id2 = Integer.parseInt(id);
			response.getWriter().println(empresaRepository.findById(id2).get().getEmail());
		}
	}
	@GetMapping("/{id}")
	public ModelAndView detalheEmpenho(@PathVariable("id") int id, Empenho empenho,Observacoes observacao, Notafiscal notaFiscal) {
		empenho = empenhoRepository.findById(id).get();
		observacao.setEmpenho(empenho);
		notaFiscal.setEmpenho(empenho);
		ModelAndView mv = new ModelAndView("detalheEmpenho");
		mv.addObject("empenho", empenho);
		mv.addObject("observacao", observacao);  
		mv.addObject("notafiscal", notaFiscal);  
		return mv;
	}
	@PostMapping("/excluir")
	public String excluirEmpenho(@RequestParam int idEmpenho,RedirectAttributes attr) {
		Empenho empenho = empenhoRepository.findById(idEmpenho).get();
		empenhoRepository.delete(empenho);
		
		attr.addFlashAttribute("exclusao", "Empenho "+empenho.getNumeroEmpenho() +" excluido com sucesso");
		return "redirect:/";
	}
	
	@PostMapping("/saveobs")
	public String salvarObservacaoEmpenho(@Valid Observacoes obs, @AuthenticationPrincipal User u){
		
		obs.setDataObs(LocalDate.now());
		obs.setUsuario(usuarioRepository.findByNome(u.getUsername()).get());
		obsRepository.save(obs);
		
		return "redirect:/empenho/"+obs.getEmpenho().getIdEmpenho()+"#observacoes";
	}
	@RequestMapping(value="/pdf/{id}", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getPDF1(@PathVariable("id") int id) {

		HttpHeaders headers = new HttpHeaders();
	    //headers.setContentType(MediaType.parseMediaType("image/jpeg"));
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "pdf1.pdf";
	    headers.add("content-disposition", "inline;filename=" + filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(empenhoRepository.findById(id).get().getEmpenhoDigitalizado(), headers, HttpStatus.OK);
	    return response;
	}
	@PostMapping("/emailpendencia")
	public ResponseEntity<?> emailCobrancaAuto(@RequestParam int idEmpenho,@AuthenticationPrincipal User u) throws MessagingException{
		Empenho empenho = empenhoRepository.findById(idEmpenho).get();
		mailer.enviarNotificacaoAtraso(empenho.getEmpresa().getEmail(), empenho);
		Observacoes obs = new Observacoes(empenho,"Mensagem de cobrança automatica enviada!",LocalDate.now()
													,usuarioRepository.findByNome(u.getUsername()).get());
		obsRepository.save(obs);
		return ResponseEntity.ok().build();	
	}
	@PostMapping("/emailprocessoadm")
	public ResponseEntity<?> emailProcessoADM(@RequestParam int idEmpenho,String msgPedidoPA,@AuthenticationPrincipal User u) throws MessagingException, IOException{
		Empenho empenho = empenhoRepository.findById(idEmpenho).get();
		mailer.enviarPedidoPA(idEmpenho, msgPedidoPA, u);
		Observacoes obs = new Observacoes(empenho,"Solicitou abertura de processo administrativo.",LocalDate.now()
													,usuarioRepository.findByNome(u.getUsername()).get());
		obsRepository.save(obs);
		return ResponseEntity.ok().build();	
	}
	
	@PostMapping("/redirect")
	public String redirect(@RequestParam String idEmpenho) {
		return "redirect:/empenho/"+idEmpenho;
	}
	
}
