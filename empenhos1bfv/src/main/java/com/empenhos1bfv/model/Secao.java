package com.empenhos1bfv.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="SECAO")
public class Secao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idSecao;
	
	@NotBlank
	private String nomeSecao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "secao")
	private List<Protocolo> protocolo;

	public List<Protocolo> getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(List<Protocolo> protocolo) {
		this.protocolo = protocolo;
	}

	public int getIdSecao() {
		return idSecao;
	}

	public void setIdSecao(int idSecao) {
		this.idSecao = idSecao;
	}

	public String getNomeSecao() {
		return nomeSecao;
	}

	public void setNomeSecao(String nomeSecao) {
		this.nomeSecao = nomeSecao;
	}
	
	
}
