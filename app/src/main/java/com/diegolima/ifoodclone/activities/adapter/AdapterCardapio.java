package com.diegolima.ifoodclone.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.model.CategoriaCardapio;

import java.util.List;

public class AdapterCardapio extends RecyclerView.Adapter<AdapterCardapio.MyViewHolder> {

	private final List<CategoriaCardapio> categoriaList;
	private final Context context;

	public AdapterCardapio(List<CategoriaCardapio> categoriaList, Context context) {
		this.categoriaList = categoriaList;
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardapio, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		CategoriaCardapio categoria = categoriaList.get(position);

		holder.textCategoriaNome.setText(categoria.getNome());

		holder.rvProdutos.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		holder.rvProdutos.setHasFixedSize(true);

		AdapterProdutoCardapio adapterProdutoCardapio = new AdapterProdutoCardapio(categoria.getProdutoList(), context);
		holder.rvProdutos.setAdapter(adapterProdutoCardapio);

		adapterProdutoCardapio.notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return categoriaList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView textCategoriaNome;
		RecyclerView rvProdutos;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			textCategoriaNome = itemView.findViewById(R.id.textCategoriaNome);
			rvProdutos = itemView.findViewById(R.id.rvProdutos);
		}
	}
}
