package com.empenhos1bfv.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name="EMPENHO")
public class Empenho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpenho;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate dataEmpenho;
	
	@NotBlank
	private String numeroEmpenho;
	
	@OneToOne
	private Empresa empresa;
	
	@OneToMany(mappedBy = "empenho")
	private List<Notafiscal> notasFiscais;
	
	@OneToMany(mappedBy = "empenho")
	private List<Observacoes> observacoes;
	
	@NotNull
	@OneToOne
	private Usuario usuario;
	
	@NotBlank
	private String destino;
	
	@NotNull
	private double valorTotal;
	
	@NotNull
	@Lob
	private byte[] empenhoDigitalizado;
	
	private int etapa;
	
	@Column(name = "saldo")
	private double saldo;
	
	private double saldoUtilizado;
	
	
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getSaldoUtilizado() {
		return saldoUtilizado;
	}
	public void setSaldoUtilizado(double saldoUtilizado) {
		this.saldoUtilizado = saldoUtilizado;
	}
	public List<Observacoes> getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(List<Observacoes> observacoes) {
		this.observacoes = observacoes;
	}
	public int getIdEmpenho() {
		return idEmpenho;
	}
	public void setIdEmpenho(int idEmpenho) {
		this.idEmpenho = idEmpenho;
	}
	public LocalDate getDataEmpenho() {
		return dataEmpenho;
	}
	public void setDataEmpenho(LocalDate dataEmpenho) {
		this.dataEmpenho = dataEmpenho;
	}
	public String getNumeroEmpenho() {
		return numeroEmpenho;
	}
	public void setNumeroEmpenho(String numeroEmpenho) {
		this.numeroEmpenho = numeroEmpenho;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public List<Notafiscal> getNotasFiscais() {
		return notasFiscais;
	}
	public void setNotasFiscais(List<Notafiscal> notasFiscais) {
		this.notasFiscais = notasFiscais;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public byte[] getEmpenhoDigitalizado() {
		return empenhoDigitalizado;
	}
	public void setEmpenhoDigitalizado(byte[] empenhoDigitalizado) {
		this.empenhoDigitalizado = empenhoDigitalizado;
	}
	public int getEtapa() {
		return etapa;
	}
	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}
	
	
}
