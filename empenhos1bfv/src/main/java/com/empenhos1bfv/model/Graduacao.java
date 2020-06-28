package com.empenhos1bfv.model;

public enum Graduacao {

	Soldado(1, "Soldado"),
	Cabo(2, "Cabo"),
	sargento3(3, "3º sargento"),
	Sargent2(4, "2º sargento"),
	Sargento1(5, "1º sargento"),
	SubTen(6, "Sub Tenente"),
	aspOf(7, "Asp of"),
	Ten2(8, "2º Tenente"),
	Ten(9, "1º Tenente"),
	Cap(10, "Capitão"),
	Maj(11, "Major"),
	TenCoronel(12, "Ten Coronel"),
	Coronel(13, "Coronel");
	
	private int id;
	private String posto; 
	
	Graduacao(int id, String posto) {
		this.id = id;
		this.posto = posto;
	}

	public int getId() {
		return id;
	}

	public String getPosto() {
		return posto;
	}
}
