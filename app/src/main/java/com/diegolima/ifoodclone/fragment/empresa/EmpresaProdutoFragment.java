package com.diegolima.ifoodclone.fragment.empresa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.adapter.ProdutoAdapterEmpresa;
import com.diegolima.ifoodclone.activities.empresa.EmpresaFormProdutoActivity;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmpresaProdutoFragment extends Fragment implements ProdutoAdapterEmpresa.OnClickListener {

	private ProdutoAdapterEmpresa produtoAdapterEmpresa;
	private List<Produto> produtoList = new ArrayList<>();

	private SwipeableRecyclerView rv_produtos;
	private ProgressBar progressBar;
	private TextView text_info;

	private FloatingActionButton fab_add;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_empresa_produto, container, false);
		iniciaComponentes(view);
		configRv();
		configCliques();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		recuperaProdutos();
	}

	private void configRv(){
		rv_produtos.setLayoutManager(new LinearLayoutManager(requireActivity()));
		rv_produtos.setHasFixedSize(true);
		produtoAdapterEmpresa = new ProdutoAdapterEmpresa(produtoList, getContext(), this);
		rv_produtos.setAdapter(produtoAdapterEmpresa);

		rv_produtos.setListener(new SwipeLeftRightCallback.Listener() {
			@Override
			public void onSwipedLeft(int position) {
			}

			@Override
			public void onSwipedRight(int position) {
				dialogRemoverProduto(produtoList.get(position));
			}
		});
	}

	private void dialogRemoverProduto(Produto produto){
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle("Remover Produto");
		builder.setMessage("Deseja remover o produto selecionado?");
		builder.setNegativeButton("Não", (dialog, which) -> {
			dialog.dismiss();
			produtoAdapterEmpresa.notifyDataSetChanged();
		});
		builder.setPositiveButton("Sim", ((dialog, which) -> {
			produto.remover();
			produtoList.remove(produto);
			produtoAdapterEmpresa.notifyDataSetChanged();
			if (produtoList.isEmpty()){
				text_info.setText("Nenhuma produto cadastrado.");
			}
			dialog.dismiss();
		}));

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private void recuperaProdutos(){
		DatabaseReference produtosRef = FirebaseHelper.getDatabaseReference()
				.child("produtos")
				.child(FirebaseHelper.getIdFirebase());
		produtosRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()) {
					produtoList.clear();
					for (DataSnapshot ds : snapshot.getChildren()) {
						Produto produto = ds.getValue(Produto.class);
						produtoList.add(produto);

					}
					text_info.setText("");
				}else{
					text_info.setText("Nenhuma produto cadastrado.");
				}
				progressBar.setVisibility(View.GONE);
				Collections.reverse(produtoList);
				produtoAdapterEmpresa.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configCliques(){
		fab_add.setOnClickListener(view ->
				startActivity(new Intent(requireActivity(), EmpresaFormProdutoActivity.class)));
	}

	private void iniciaComponentes(View view) {
		fab_add = view.findViewById(R.id.fab_add);

		rv_produtos = view.findViewById(R.id.rv_produtos);
		progressBar = view.findViewById(R.id.progressBar);
		text_info = view.findViewById(R.id.text_info);
	}

	@Override
	public void OnClick(Produto produto) {
		Intent intent = new Intent(requireActivity(), EmpresaFormProdutoActivity.class);
		intent.putExtra("produtoSelecionado", produto);
		startActivity(intent);
	}
}