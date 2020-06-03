package com.empenhos1bfv.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name="NOTAFISCAL")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Notafiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idNotaFiscal;
	
	private int numNota;
	
	@NotBlank
	private String chaveAcesso;
	
	@NotNull
	private double valorTotal;
	
	@ManyToOne
	@JoinColumn(name = "empenho_id_empenho")
	private Empenho empenho;
	
	@DateTimeFormat(iso = ISO.DATE, pattern = "")
	private LocalDate dataEmissao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate dataRecebido;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate dataProtocolado;
	
	@NotNull
	@OneToOne
	private Usuario usuario;
	
	@NotNull
	@OneToOne
	private Secao secao;
		
	public int getIdNotaFiscal() {
		return idNotaFiscal;
	}
	public void setIdNotaFiscal(int idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}
	public int getNumNota() {
		return numNota;
	}
	public void setNumNota(int numNota) {
		this.numNota = numNota;
	}
	public String getChaveAcesso() {
		return chaveAcesso;
	}
	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Empenho getEmpenho() {
		return empenho;
	}
	public void setEmpenho(Empenho empenho) {
		this.empenho = empenho;
	}
	public LocalDate getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public LocalDate getDataRecebido() {
		return dataRecebido;
	}
	public void setDataRecebido(LocalDate dataRecebido) {
		this.dataRecebido = dataRecebido;
	}
	public LocalDate getDataProtocolado() {
		return dataProtocolado;
	}
	public void setDataProtocolado(LocalDate dataProtocolado) {
		this.dataProtocolado = dataProtocolado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Secao getSecao() {
		return secao;
	}
	public void setSecao(Secao secao) {
		this.secao = secao;
	}
	
	
}
