package com.diegolima.ifoodclone.activities.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.helper.GetMask;
import com.diegolima.ifoodclone.model.Pedido;

import java.security.spec.MGF1ParameterSpec;
import java.util.List;

public class FinanceiroAdapter extends RecyclerView.Adapter<FinanceiroAdapter.MyViewHolder> {

	private List<Pedido> pedidoList;
	private Context context;

	public FinanceiroAdapter(List<Pedido> pedidoList, Context context) {
		this.pedidoList = pedidoList;
		this.context = context;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financeiro, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Pedido pedido = pedidoList.get(position);

		double totalPedido = pedido.getTotalPedido() + pedido.getTaxaEntrega();
		holder.textValor.setText(context.getString(R.string.text_valor, GetMask.getValor(totalPedido)));
		holder.textHora.setText(GetMask.getDate(pedido.getDataPedido(), 2));
	}

	@Override
	public int getItemCount() {
		return pedidoList.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView textValor, textHora;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			textValor = itemView.findViewById(R.id.textValor);
			textHora = itemView.findViewById(R.id.textHora);
		}
	}
}
