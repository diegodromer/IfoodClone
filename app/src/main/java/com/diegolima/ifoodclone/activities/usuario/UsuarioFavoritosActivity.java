package com.diegolima.ifoodclone.activities.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;

public class UsuarioFavoritosActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario_favoritos);
		iniciaComponentes();
		configCliques();
	}

	private void configCliques(){
		findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
	}

	private void iniciaComponentes(){
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Favoritos");
	}
}