package com.empenhos1bfv.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.MaskFormatter;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Notafiscal;
import com.empenhos1bfv.model.Observacoes;
import com.empenhos1bfv.model.Protocolo;
import com.empenhos1bfv.model.Secao;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoRepository;
import com.empenhos1bfv.repository.NotaFiscalRepository;
import com.empenhos1bfv.repository.ObservacaoRepository;
import com.empenhos1bfv.repository.ProtocoloRepository;
import com.empenhos1bfv.repository.SecaoRepository;
import com.empenhos1bfv.repository.UsuarioRepository;

@Controller
@RequestMapping("/notafiscal")
public class NotaFiscalController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	NotaFiscalRepository notaFiscalRepository;
	@Autowired
	EmpenhoRepository empenhoRepository;
	@Autowired
	ProtocoloRepository protocoloRepository;
	@Autowired
	SecaoRepository secaoRepository;
	@Autowired
	ObservacaoRepository obsRepository;
	
	@ModelAttribute("empenhosNavbar")
	public List<Empenho> getEmpenhos(){
		return empenhoRepository.findAll();
	}
	@PostMapping("/savenota")
	public String saveNota(@Valid Notafiscal nota, BindingResult result, @AuthenticationPrincipal User u) throws IOException, ParseException {
		if (result.hasErrors()) { return "redirect:/novanota"; }
		nota.setSecao(usuarioRepository.findByNome(u.getUsername()).get().getSecao());
		nota.getEmpenho().setEtapa(4);
		//regra de abater saldo
		nota.getEmpenho().setSaldo(nota.getEmpenho().getSaldo() - nota.getValorTotal());
		nota.getEmpenho().setSaldoUtilizado(nota.getEmpenho().getSaldoUtilizado() + nota.getValorTotal());
		nota.setDataRecebido(LocalDate.now());
		nota.setUsuario(usuarioRepository.findByNome(u.getUsername()).get());
		
		MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
        mask.setValueContainsLiteralCharacters(false);
		nota.getEmpenho().getEmpresa().setCnpj(mask.valueToString(nota.getChaveAcesso().substring(6, 20)));
		
		notaFiscalRepository.save(nota);
		
		return "redirect:/empenho/"+nota.getEmpenho().getIdEmpenho();
	}
	
	@PostMapping("/protocolar")
	public ResponseEntity<?> doProtocolar(HttpServletRequest request,@Valid Secao secao, @AuthenticationPrincipal User u){
		
		List<Integer> lista = new ArrayList<Integer>();
		
		for(String name: request.getParameterMap().keySet()) {
			for(String value: request.getParameterValues(name)) {
				//System.out.println(name+ ": "+ value);
				//String[] checked = request.getParameterValues("opcoes[]");
				lista.add(Integer.parseInt(value));
			}
		}
		Usuario usuario = usuarioRepository.findByNome(u.getUsername()).get();
		for (int valores : lista) {
			try {
				notaFiscalRepository.alteraStatus(valores, LocalDate.now());
				protocoloRepository.save(new Protocolo(notaFiscalRepository.findById(valores).get(),secao, 1,usuario));	
			}catch (Exception e) {

			}
		}
		return ResponseEntity.ok().build();
	}
	@PostMapping("/protocolarecebido")
	public ResponseEntity<?> doProtocoloRecebido(HttpServletRequest request, @AuthenticationPrincipal User u){
		
		List<Integer> lista = new ArrayList<Integer>();
		
		for(String name: request.getParameterMap().keySet()) {
			for(String value: request.getParameterValues(name)) {
				lista.add(Integer.parseInt(value));
			}
		}
		Protocolo protocolo;
		for (int valores : lista) {
			try {
				protocolo = protocoloRepository.findById(valores).get();
				protocolo.setEtapaProtocolo(2);
				Observacoes obs = new Observacoes(protocolo.getNotaFiscal().getEmpenho()
												,"NF "+protocolo.getNotaFiscal().getNumNota()+
												" Recebida por: "+protocolo.getSecao().getNomeSecao() +" / "+usuarioRepository.findByNome(u.getUsername()).get().getNome()
												+ " em " + LocalDate.now()
												,LocalDate.now()
												,usuarioRepository.findByNome(u.getUsername()).get()); 
				obsRepository.save(obs);
				protocolo.getNotaFiscal().setDataProtocolado(null);
				protocolo.getNotaFiscal().setSecao(usuarioRepository.findByNome(u.getUsername()).get().getSecao());
				protocolo.setUsuarioRecebedor(usuarioRepository.findByNome(u.getUsername()).get());
				protocoloRepository.save(protocolo);
			}catch (Exception e) {
				System.out.println(e);
			}	
		}
		return ResponseEntity.ok().build();	
	}
	@PostMapping("/excluir")
	public ResponseEntity<?> apagar(@RequestParam String idNotaFiscal) {
		int id = Integer.parseInt(idNotaFiscal);
		protocoloRepository.deletePorIdNf(idNotaFiscal);
		try {
			Notafiscal nota = notaFiscalRepository.findById(id).get();
			nota.getEmpenho().setSaldo(nota.getEmpenho().getSaldo() + nota.getValorTotal());
			nota.getEmpenho().setSaldoUtilizado(nota.getEmpenho().getSaldoUtilizado() - nota.getValorTotal());
			notaFiscalRepository.deleteById(id);	
		} catch (Exception e) {
			System.out.println("Erro ao excluir nota");
			return ResponseEntity.unprocessableEntity().build();
		}
		
		return ResponseEntity.ok().build();
	}
}
