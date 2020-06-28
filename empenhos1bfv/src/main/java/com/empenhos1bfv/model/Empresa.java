package com.empenhos1bfv.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name="EMPRESA")
public class Empresa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpresa;

	private String nome;
	
	@NotBlank(message = "email é obrigatório")
	private String email;
	
	@NotBlank(message = "Tel de contato obrigatório")
	private String contato;
	
	private String cnpj;
	
	@JsonIgnore
	@OneToMany(mappedBy = "empresa")
	private List<Empenho> empenhos;
	
	@JsonIgnore
	@OneToMany(mappedBy = "empresa")
	private List<ObservacoesEmpresa> observacoesEmpresa;
	
	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public List<Empenho> getEmpenhos() {
		return empenhos;
	}

	public void setEmpenhos(List<Empenho> empenhos) {
		this.empenhos = empenhos;
	}

	public List<ObservacoesEmpresa> getObservacoesEmpresa() {
		return observacoesEmpresa;
	}

	public void setObservacoesEmpresa(List<ObservacoesEmpresa> observacoesEmpresa) {
		this.observacoesEmpresa = observacoesEmpresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
