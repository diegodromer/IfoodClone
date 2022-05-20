package com.diegolima.ifoodclone.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.autenticacao.CriarContaActivity;
import com.diegolima.ifoodclone.activities.autenticacao.LoginActivity;
import com.diegolima.ifoodclone.activities.usuario.UsuarioEnderecosActivity;
import com.diegolima.ifoodclone.activities.usuario.UsuarioFavoritosActivity;
import com.diegolima.ifoodclone.activities.usuario.UsuarioPerfilActivity;
import com.diegolima.ifoodclone.helper.FirebaseHelper;

public class UsuarioPerfilFragment extends Fragment {

	private ConstraintLayout l_logado;
	private ConstraintLayout l_deslogado;
	private LinearLayout menu_perfil;
	private LinearLayout menu_favoritos;
	private LinearLayout menu_enderecos;
	private LinearLayout menu_deslogar;
	private Button btn_cadastrar;
	private Button btn_entrar;
	private TextView text_usuario;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_usuario_perfil, container, false);
		iniciaComponentes(view);
		configCliques();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		verificaAcesso();
	}

	private void verificaAcesso(){
		if (FirebaseHelper.getAutenticado()){
			l_deslogado.setVisibility(View.GONE);
			l_logado.setVisibility(View.VISIBLE);
			text_usuario.setText(FirebaseHelper.getAuth().getCurrentUser().getDisplayName());
			menu_deslogar.setVisibility(View.VISIBLE);
		}else{
			l_deslogado.setVisibility(View.VISIBLE);
			l_logado.setVisibility(View.GONE);
			menu_deslogar.setVisibility(View.GONE);
		}
	}

	private void configCliques(){
		btn_entrar.setOnClickListener(view -> startActivity(new Intent(requireActivity(), LoginActivity.class)));
		btn_cadastrar.setOnClickListener(view -> startActivity(new Intent(requireActivity(), CriarContaActivity.class)));
		menu_deslogar.setOnClickListener(view -> deslogar());

		menu_perfil.setOnClickListener(view -> startActivity(new Intent(requireActivity(), UsuarioPerfilActivity.class)));
		menu_favoritos.setOnClickListener(view -> startActivity(new Intent(requireActivity(), UsuarioFavoritosActivity.class)));
		menu_enderecos.setOnClickListener(view -> startActivity(new Intent(requireActivity(), UsuarioEnderecosActivity.class)));
	}

	private void deslogar(){
		FirebaseHelper.getAuth().signOut();
		Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.menu_home);
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
		text_usuario = view.findViewById(R.id.text_empresa);
	}
}