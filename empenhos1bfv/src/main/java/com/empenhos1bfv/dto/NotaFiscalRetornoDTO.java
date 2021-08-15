package com.empenhos1bfv.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotaFiscalRetornoDTO {

    private int idNotaFiscal;

    private String chaveAcesso;

    private int numNota;

    private double valorTotal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataEmissao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataRecebido;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dataProtocolado;

    private int idEmpenho;

    private String numeroEmpenho;

    private String nome;

    private String destino;

    public NotaFiscalRetornoDTO(int idNotaFiscal, String chaveAcesso, int numNota, double valorTotal, Date dataEmissao,
            Date dataRecebido, Date dataProtocolado, String numeroEmpenho, String nome, String destino) {
        this.idNotaFiscal = idNotaFiscal;
        this.chaveAcesso = chaveAcesso;
        this.numNota = numNota;
        this.valorTotal = valorTotal;
        this.dataEmissao = dataEmissao;
        this.dataRecebido = dataRecebido;
        this.dataProtocolado = dataProtocolado;
        this.numeroEmpenho = numeroEmpenho;
        this.nome = nome;
        this.destino = destino;
    }

    public NotaFiscalRetornoDTO() {
    }

    public int getIdEmpenho() {
        return idEmpenho;
    }

    public void setIdEmpenho(int idEmpenho) {
        this.idEmpenho = idEmpenho;
    }

    public int getIdNotaFiscal() {
        return idNotaFiscal;
    }

    public void setIdNotaFiscal(int idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public int getNumNota() {
        return numNota;
    }

    public void setNumNota(int numNota) {
        this.numNota = numNota;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataRecebido() {
        return dataRecebido;
    }

    public void setDataRecebido(Date dataRecebido) {
        this.dataRecebido = dataRecebido;
    }

    public Date getDataProtocolado() {
        return dataProtocolado;
    }

    public void setDataProtocolado(Date dataProtocolado) {
        this.dataProtocolado = dataProtocolado;
    }

    public String getNumeroEmpenho() {
        return numeroEmpenho;
    }

    public void setNumeroEmpenho(String numeroEmpenho) {
        this.numeroEmpenho = numeroEmpenho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

}
