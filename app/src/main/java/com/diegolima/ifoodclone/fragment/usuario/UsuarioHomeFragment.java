package com.diegolima.ifoodclone.fragment.usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.EmpresasAdapter;
import com.diegolima.ifoodclone.activities.empresa.EmpresaCardapioActivity;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Empresa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioHomeFragment extends Fragment implements EmpresasAdapter.OnClickListener {

	private EmpresasAdapter empresasAdapter;
	private List<Empresa> empresaList = new ArrayList<>();

	private RecyclerView rv_empresas;
	private ProgressBar progressBar;
	private TextView text_info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_usuario_home, container, false);
		iniciaComponentes(view);
		configRv();
		recuperaEmpresas();
		return view;
	}

	private void recuperaEmpresas() {
		DatabaseReference empresasRef = FirebaseHelper.getDatabaseReference()
				.child("empresas");
		empresasRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					empresaList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						empresaList.add(ds.getValue(Empresa.class));
					}
					text_info.setText("");
				} else {
					text_info.setText("Nenhuma empresa cadastrada");
				}

				progressBar.setVisibility(View.GONE);
				Collections.reverse(empresaList);
				empresasAdapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configRv() {
		rv_empresas.setLayoutManager(new LinearLayoutManager(requireContext()));
		rv_empresas.setHasFixedSize(true);
		empresasAdapter = new EmpresasAdapter(empresaList, this, requireContext());
		rv_empresas.setAdapter(empresasAdapter);
	}

	private void iniciaComponentes(View view) {
		rv_empresas = view.findViewById(R.id.rv_empresas);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
	}

	@Override
	public void OnClick(Empresa empresa) {
		Intent intent = new Intent(requireActivity(), EmpresaCardapioActivity.class);
		intent.putExtra("empresaSelecionada", empresa);
		startActivity(intent);
	}
}