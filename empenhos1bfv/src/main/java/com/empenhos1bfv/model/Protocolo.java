package com.empenhos1bfv.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PROTOCOLO")
public class Protocolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@OneToOne(cascade={ CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	private Notafiscal notaFiscal;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "secao_id_secao")
	private Secao secao;
	
	private int etapaProtocolo;
	
	@NotNull
	@OneToOne
	private Usuario usuario;
	
	@OneToOne
	private Usuario usuarioRecebedor;
	
	public Protocolo() {
		super();
	}

	public Protocolo(@NotNull Notafiscal notaFiscal, @NotNull Secao secao, int etapaProtocolo,Usuario usuario) {
		this.notaFiscal = notaFiscal;
		this.secao = secao;
		this.etapaProtocolo = etapaProtocolo;
		this.usuario = usuario;
	}

	public Protocolo(Protocolo protocolo) {

	}

	public Usuario getUsuarioRecebedor() {
		return usuarioRecebedor;
	}

	public void setUsuarioRecebedor(Usuario usuarioRecebedor) {
		this.usuarioRecebedor = usuarioRecebedor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Notafiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(Notafiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public int getEtapaProtocolo() {
		return etapaProtocolo;
	}

	public void setEtapaProtocolo(int etapaProtocolo) {
		this.etapaProtocolo = etapaProtocolo;
	}
	
	
}
