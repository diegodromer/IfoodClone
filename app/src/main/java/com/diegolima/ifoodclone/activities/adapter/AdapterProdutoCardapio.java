package com.diegolima.ifoodclone.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.helper.GetMask;
import com.diegolima.ifoodclone.model.Produto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProdutoCardapio extends RecyclerView.Adapter<AdapterProdutoCardapio.MyViewHolder> {

	private final List<Produto> produtoList;
	private final Context context;

	public AdapterProdutoCardapio(List<Produto> produtoList, Context context) {
		this.produtoList = produtoList;
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produto_cardapio, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Produto produto = produtoList.get(position);

		Picasso.get().load(produto.getUrlImagem()).into(holder.imagemProduto);
		holder.textProdutoNome.setText(produto.getNome());
		holder.textProdutoValor.setText(context.getString(R.string.text_valor, GetMask.getValor(produto.getValor())));
	}

	@Override
	public int getItemCount() {
		return produtoList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		ImageView imagemProduto;
		TextView textProdutoNome,textProdutoValor;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			imagemProduto = itemView.findViewById(R.id.imagemProduto);
			textProdutoNome = itemView.findViewById(R.id.textProdutoNome);
			textProdutoValor = itemView.findViewById(R.id.textProdutoValor);
		}
	}
}
