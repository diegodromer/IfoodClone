package com.diegolima.ifoodclone.activities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.model.Endereco;

import java.util.List;

public class SelecionaPagamentoAdapter extends RecyclerView.Adapter<SelecionaPagamentoAdapter.MyViewHolder> {

	private final List<Endereco> enderecoList;
	private final OnClickListener onClickListener;

	private int lastSelectPosition = -1;

	public SelecionaPagamentoAdapter(List<Endereco> enderecoList, OnClickListener onClickListener) {
		this.enderecoList = enderecoList;
		this.onClickListener = onClickListener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.endereco_select, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Endereco endereco = enderecoList.get(position);
		holder.text_logradouro.setText(endereco.getLogradouro());
		StringBuffer enderecoCompleto = new StringBuffer()
				.append(endereco.getReferencia())
				.append(", ")
				.append(endereco.getBairro())
				.append(", ")
				.append(endereco.getMunicipio());

		holder.text_endereco.setText(enderecoCompleto);

		if (lastSelectPosition == position){
			holder.radioButton.setChecked(true);
		}

		holder.radioButton.setOnClickListener(v -> {
			lastSelectPosition = holder.getAdapterPosition();
			notifyDataSetChanged();
			onClickListener.OnClick(endereco);
		});
	}

	@Override
	public int getItemCount() {
		return enderecoList.size();
	}

	public interface OnClickListener {
		void OnClick(Endereco endereco);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		RadioButton radioButton;
		TextView text_logradouro, text_endereco;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			radioButton = itemView.findViewById(R.id.radioButton);
			text_logradouro = itemView.findViewById(R.id.text_logradouro);
			text_endereco = itemView.findViewById(R.id.text_endereco);
		}
	}
}
