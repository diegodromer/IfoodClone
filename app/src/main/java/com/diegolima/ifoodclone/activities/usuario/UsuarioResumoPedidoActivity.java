package com.diegolima.ifoodclone.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.CarrinhoAdapter;
import com.diegolima.ifoodclone.model.ItemPedido;

import java.util.ArrayList;
import java.util.List;

public class UsuarioResumoPedidoActivity extends AppCompatActivity {

	private List<ItemPedido> itemPedidoList = new ArrayList<>();
	private RecyclerView rv_produtos;
	private CarrinhoAdapter carrinhoAdapter;

	private TextView text_endereco;
	private TextView text_forma_pagamento;
	private TextView text_subtotal;
	private TextView text_taxa_entrega;
	private TextView text_total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_resumo_pedido);

		iniciaComponentes();
	}

	public

	private void iniciaComponentes(){
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Resumo pedido");

		text_endereco = findViewById(R.id.text_endereco);
		text_forma_pagamento = findViewById(R.id.text_forma_pagamento);
		text_subtotal = findViewById(R.id.text_subtotal);
		text_taxa_entrega = findViewById(R.id.text_taxa_entrega);
		text_total = findViewById(R.id.text_total);
		rv_produtos = findViewById(R.id.rv_produtos);
	}
}