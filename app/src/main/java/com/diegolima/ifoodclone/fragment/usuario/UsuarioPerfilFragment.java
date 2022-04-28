package com.diegolima.ifoodclone.fragment.usuario;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.diegolima.ifoodclone.R;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;

public class UsuarioPerfilFragment extends Fragment {

	private ConstraintLayout l_logado;
	private ConstraintLayout l_deslogado;
	private LinearLayout menu_perfil;
	private LinearLayout menu_favoritos;
	private LinearLayout menu_enderecos;
	private LinearLayout menu_deslogar;
	private Button btn_cadastrar;
	private Button btn_entrar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_usuario_perfil, container, false);
		iniciaComponentes(view);
		return view;
	}

	private void iniciaComponentes(View view){
		l_logado = view.findViewById(R.id.l_logado);
		l_deslogado = view.findViewById(R.id.l_deslogado);
		menu_perfil = view.findViewById(R.id.menu_perfil);
		menu_favoritos = view.findViewById(R.id.menu_favoritos);
		menu_enderecos = view.findViewById(R.id.menu_enderecos);
		menu_deslogar = view.findViewById(R.id.menu_deslogar);
		btn_entrar = view.findViewById(R.id.btn_entrar);
		btn_cadastrar = view.findViewById(R.id.btn_cadastrar);
	}
}