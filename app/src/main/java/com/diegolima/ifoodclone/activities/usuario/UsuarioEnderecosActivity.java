package com.diegolima.ifoodclone.activities.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.EnderecoAdapter;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Endereco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioEnderecosActivity extends AppCompatActivity implements EnderecoAdapter.OnClickListener {

	private EnderecoAdapter enderecoAdapter;
	private List<Endereco> enderecoList = new ArrayList<>();

	private SwipeableRecyclerView rv_enderecos;
	private ProgressBar progressBar;
	private TextView text_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_enderecos);
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
		enderecoAdapter = new EnderecoAdapter(enderecoList, this);
		rv_enderecos.setAdapter(enderecoAdapter);

		rv_enderecos.setListener(new SwipeLeftRightCallback.Listener() {
			@Override
			public void onSwipedLeft(int position) {
			}

			@Override
			public void onSwipedRight(int position) {
				dialogRemoverEndereco(enderecoList.get(position));
			}
		});
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
				enderecoAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void dialogRemoverEndereco(Endereco endereco){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Remover Endereço");
		builder.setMessage("Deseja remover o endereço selecionado?");
		builder.setNegativeButton("Não", (dialog, which) -> {
			dialog.dismiss();
			enderecoAdapter.notifyDataSetChanged();
		});
		builder.setPositiveButton("Sim", ((dialog, which) -> {
			endereco.remover();
			enderecoList.remove(endereco);
			if (enderecoList.isEmpty()){
				text_info.setText("Nenhuma endereço cadastrado.");
			}
			enderecoAdapter.notifyDataSetChanged();
			dialog.dismiss();
		}));

		AlertDialog dialog = builder.create();
		dialog.show();

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

	private void iniciaComponentes() {
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Endereços");

		rv_enderecos = findViewById(R.id.rv_enderecos);
		progressBar = findViewById(R.id.progressBar);
		text_info = findViewById(R.id.text_info);
	}

	@Override
	public void OnClick(Endereco endereco, int position) {
		Intent intent = new Intent(this, UsuarioFormEnderecoActivity.class);
		intent.putExtra("enderecoSelecionado", endereco);
		startActivity(intent);
	}
}