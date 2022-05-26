package com.diegolima.ifoodclone.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.SelecionaEnderecoAdapter;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioSelecionaEnderecoActivity extends AppCompatActivity implements SelecionaEnderecoAdapter.OnClickListener {

	private SelecionaEnderecoAdapter selecionaEnderecoAdapter;
	private final List<Endereco> enderecoList = new ArrayList<>();

	private RecyclerView rv_enderecos;
	private ProgressBar progressBar;
	private TextView text_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_seleciona_endereco);
		iniciaComponentes();
		configCliques();
		configRv();
	}

	@Override
	protected void onStart() {
		super.onStart();
		recuperaEndereco();
	}

	private void configRv(){
		rv_enderecos.setLayoutManager(new LinearLayoutManager(this));
		rv_enderecos.setHasFixedSize(true);
		selecionaEnderecoAdapter = new SelecionaEnderecoAdapter(enderecoList, this);
		rv_enderecos.setAdapter(selecionaEnderecoAdapter);
	}

	private void recuperaEndereco(){
		DatabaseReference enderecosRef = FirebaseHelper.getDatabaseReference()
				.child("enderecos")
				.child(FirebaseHelper.getIdFirebase());
		enderecosRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					enderecoList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						Endereco endereco = ds.getValue(Endereco.class);
						enderecoList.add(endereco);
					}
					text_info.setText("");
				}else{
					text_info.setText("Nenhuma endereco cadastrado.");
				}
				progressBar.setVisibility(View.GONE);
				Collections.reverse(enderecoList);
				selecionaEnderecoAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {
			}
		});
	}

	private void configCliques() {
		findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
		findViewById(R.id.ib_add)
				.setOnClickListener(view ->
						startActivity(
								new Intent(
										this,
										UsuarioFormEnderecoActivity.class)
						)
				);
	}


	private void iniciaComponentes(){
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Meus endereços");

		rv_enderecos = findViewById(R.id.rv_enderecos);
		progressBar = findViewById(R.id.progressBar);
		text_info = findViewById(R.id.text_info);
	}

	@Override
	public void OnClick(Endereco endereco) {
	}
}