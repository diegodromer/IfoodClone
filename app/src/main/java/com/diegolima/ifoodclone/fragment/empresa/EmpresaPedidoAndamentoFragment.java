package com.diegolima.ifoodclone.fragment.empresa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.EmpresaPedidoAdapter;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Pedido;
import com.diegolima.ifoodclone.model.StatusPedido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmpresaPedidoAndamentoFragment extends Fragment implements EmpresaPedidoAdapter.OnClickListener {

	private final List<Pedido> pedidoList = new ArrayList<>();
	private EmpresaPedidoAdapter empresaPedidoAdapter;

	private RecyclerView rv_pedidos;
	private ProgressBar progressBar;
	private TextView text_info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_empresa_pedido_andamento, container, false);

		iniciaComponentes(view);

		configRv();

		recuperaPedidos();

		return view;
	}

	private void recuperaPedidos(){
		DatabaseReference pedidoRef = FirebaseHelper.getDatabaseReference()
				.child("empresaPedidos")
				.child(FirebaseHelper.getIdFirebase());
		pedidoRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if(snapshot.exists()){
					pedidoList.clear();
					for(DataSnapshot ds : snapshot.getChildren()){
						Pedido pedido = ds.getValue(Pedido.class);
						if(pedido != null){
							addPedidoList(pedido);
						}
					}
					text_info.setText("");
				}else {
					text_info.setText("Nenhum pedido recebido.");
				}

				progressBar.setVisibility(View.GONE);
				empresaPedidoAdapter.notifyDataSetChanged();

			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void addPedidoList(Pedido pedido){
		if(pedido.getStatusPedido() != StatusPedido.CANCELADO_EMPRESA
				|| pedido.getStatusPedido() != StatusPedido.CANCELADO_USUARIO
				|| pedido.getStatusPedido() != StatusPedido.ENTREGUE){
			pedidoList.add(pedido);
		}
	}

	private void configRv(){
		rv_pedidos.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_pedidos.setHasFixedSize(true);
		empresaPedidoAdapter = new EmpresaPedidoAdapter(pedidoList, getContext(), this);
		rv_pedidos.setAdapter(empresaPedidoAdapter);
	}

	private void iniciaComponentes(View view){
		rv_pedidos = view.findViewById(R.id.rv_pedidos);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
	}

	@Override
	public void OnClick(Pedido pedido, int rota) {
		if(rota == 0){
			Toast.makeText(getContext(), "Status.", Toast.LENGTH_SHORT).show();
		}else if(rota == 1){
			Toast.makeText(getContext(), "Detalhes Pedido.", Toast.LENGTH_SHORT).show();
		}
	}
}