package com.empenhos1bfv.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.empenhos1bfv.model.Empenho;

@Component
public class Mailer {
	
	
	@Autowired
	private JavaMailSender javaMailSender;

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
}
