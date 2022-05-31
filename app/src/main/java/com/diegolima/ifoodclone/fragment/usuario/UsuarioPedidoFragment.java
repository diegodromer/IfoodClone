package com.diegolima.ifoodclone.fragment.usuario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegolima.ifoodclone.R;

public class UsuarioPedidoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_usuario_pedido, container, false);
	}
}