package com.diegolima.ifoodclone.model;

import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Entrega {
	private Boolean status = false;
	private String descricao;
	private double taxa;

	public Entrega() {
	}

	public static void salvar(List<Entrega> entregaList){
		DatabaseReference entregasRef = FirebaseHelper.getDatabaseReference()
				.child("entregas")
				.child(FirebaseHelper.getIdFirebase());
		entregasRef.setValue(entregaList);
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}
}
