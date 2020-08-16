package com.empenhos1bfv.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.empenhos1bfv.model.Empenho;
import com.empenhos1bfv.model.Usuario;
import com.empenhos1bfv.repository.EmpenhoRepository;
import com.empenhos1bfv.repository.UsuarioRepository;

@Component
public class Mailer {
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private SpringTemplateEngine template;
	
	@Autowired
	EmpenhoRepository empenhoResository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public void enviarComAnexo(Empenho empenho,String msgComplementar,MultipartFile file) throws MessagingException, IOException {
				
		MimeMessage  mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
		
		helper.setFrom(empenho.getUsuario().getEmail());
		helper.setTo(empenho.getEmpresa().getEmail());
		helper.setSubject(empenho.getNumeroEmpenho() +" - "+empenho.getEmpresa().getNome());
		helper.setText(msgEmail(empenho, msgComplementar).toString(), true);
		helper.addAttachment(empenho.getNumeroEmpenho()+".pdf", file);
	    javaMailSender.send(mimeMsg);
	}
	public StringBuffer msgEmail(Empenho empenho, String mensagem) {
		//uso StringBuffer para otimizar a concatenação de string
	    StringBuffer texto = new StringBuffer(); 
	    texto.append("<h2 align='center'>Pedido : "+empenho.getNumeroEmpenho()+" - "+ empenho.getEmpresa().getNome()+"</h2>");
	    texto.append("Solicito-vos fornecer o(s) item(s) do empenho em anexo:<br/><br/>");
	    if(mensagem != null || mensagem != "") {
	    	texto.append(mensagem+"<br/><br/>");
	    }
	    texto.append("LOCAL DE ENTREGA: <br/>");
	    texto.append("1º B Fv <br/>");
	    texto.append("Rua: 2º Batalhão Rodoviário s/n  <br/>");
	    texto.append("Bairro: Conta Dinheiro <br/>");
	    texto.append("CEP: 88.520-900 - Lages/SC <br/><br/>");
	    texto.append("Nota Fiscal: <br/>");
	    texto.append("Dados adicionais(Informações complementares) <br/>");
	    texto.append("Informar se é optante do simples nacional: <br/>");
	    texto.append("se não for, informar alíquota e datalhando(cofins, CSLL/IR e PIS/PASEP) <br/><br/>");
	    texto.append("Prazo para entrega: conforme edital. Solicito ainda informar a possível data de entrega. <br/>");
	    texto.append("<h2 align='center' text-color='red' >Solicito-vos acusar o recebimento </h2><br/>");
	    texto.append("Atenciosamente, <br/><br/>");
	    texto.append(empenho.getUsuario().getGraduacao()+" "+empenho.getUsuario().getNome());
	    
	    return texto;
	}
	
	public void enviarPedidoPA(int idEmpenho,String msgComplementar,@AuthenticationPrincipal User u) throws MessagingException, IOException {
		Empenho empenho = empenhoResository.findById(idEmpenho).get();
		Usuario usuario = usuarioRepository.findByNome(u.getUsername()).get();
		MimeMessage  mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
		
		helper.setFrom(empenho.getUsuario().getEmail());
		helper.setTo("licio.machado.mm@gmail.com");
		helper.setSubject("Solicitação de processo administrativo: "+empenho.getNumeroEmpenho() +" - "+empenho.getEmpresa().getNome());
		helper.setText(msgEmailProcessoAdm(empenho, msgComplementar,usuario).toString(), true);
	    javaMailSender.send(mimeMsg);
	}
	public StringBuffer msgEmailProcessoAdm(Empenho empenho, String msgComplementar,Usuario u) {
		//uso StringBuffer para otimizar a concatenação de string
	    StringBuffer texto = new StringBuffer(); 
	    texto.append("<h2 align='center'>Pedido de abertura de processo administrativo: "+empenho.getNumeroEmpenho()+" - "+ empenho.getEmpresa().getNome()+"</h2>");
    	texto.append(msgComplementar+"<br/><br/>");
	    texto.append("Atenciosamente, <br/><br/>");
	    texto.append(u.getGraduacao()+" "+u.getNome());
	    
	    return texto;
	}
	
	public void enviarNotificacaoAtraso(String destino, Empenho empenho) throws MessagingException {
		
		DateFormat dfmt = new SimpleDateFormat("d MMMM yyyy");
        Date hoje = Calendar.getInstance(Locale.getDefault()).getTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        	
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = 
				new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
		Context context = new Context();
		context.setVariable("titulo", "ATENÇÃO");
		context.setVariable("texto", "Versa o presente expediente sobre notificação da empresa por deixar de cumprir com prazos de entrega "
				+ "do material contratado em licitação pública, através de pregão eletrônico. "
				+ "Sobre o assunto vimos por meio deste notificar a empresa "+empenho.getEmpresa().getNome()+" "
				+ "que possui pendências referentes as notas de empenho abaixo: ");
		context.setVariable("empenho", "Empenho: "+empenho.getNumeroEmpenho());
		context.setVariable("valor", "Valor: "+NumberFormat.getCurrencyInstance().format(empenho.getValorTotal()));
		context.setVariable("data", "Data de envio: "+empenho.getDataEmpenho().format(formatter));
		context.setVariable("hoje", "Lages-SC, "+dfmt.format(hoje));
		
		String html = template.process("email/confirmacao", context);
		helper.setTo(destino);
		helper.setText(html, true);
		helper.setSubject("ATENÇÃO - Notificação de atraso");
		helper.setFrom("licio.machado.mm@gmail.com");
		
		helper.addInline("logo", new ClassPathResource("/static/img/iconOM.png"));
		
		javaMailSender.send(message);		
	}
}
