package com.empenhos1bfv.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

@Entity
@Table(name="OBSERVACOES_EMPRESA")
public class ObservacoesEmpresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idObs;
	
	@ManyToOne
	private Empresa empresa;
	
	@NotBlank
	@Lob
	private String observacao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private LocalDate dataObs;
	
	@NotNull
	@OneToOne
	private Usuario usuario;
	
	public int getIdObs() {
		return idObs;
	}
	public void setIdObs(int idObs) {
		this.idObs = idObs;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public LocalDate getDataObs() {
		return dataObs;
	}
	public void setDataObs(LocalDate dataObs) {
		this.dataObs = dataObs;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
