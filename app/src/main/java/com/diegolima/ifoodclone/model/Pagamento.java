package com.diegolima.ifoodclone.model;

import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Pagamento {

	private String descricao;
	private Boolean status = false;

	public Pagamento() {
	}

	public static void salvar(List<Pagamento> pagamentoList){
		DatabaseReference pagamentosRef = FirebaseHelper.getDatabaseReference()
				.child("recebimentos")
				.child(FirebaseHelper.getIdFirebase());
		pagamentosRef.setValue(pagamentoList);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
