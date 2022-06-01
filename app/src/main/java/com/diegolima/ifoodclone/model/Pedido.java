package com.diegolima.ifoodclone.model;

import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {

	private String id;
	private String idCliente;
	private String idEmpresa;
	private String formaPagamento;
	private Long dataPedido;
	private Long dataStatusPedido;
	private StatusPedido statusPedido;
	private String motivoCancelamento;
	private List<ItemPedido> itemPedidoList = new ArrayList<>();
	private Double taxaEntrega;
	private Double subTotal;
	private Double totalPedido;
	private Endereco enderecoEntrega;

	public Pedido() {
		DatabaseReference pedidoRef = FirebaseHelper.getDatabaseReference();
		this.setId(pedidoRef.push().getKey());
	}

	public void salvar(){

		DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
				.child("empresaPedidos")
				.child(getIdEmpresa())
				.child(getId());
		empresaRef.setValue(this);

		DatabaseReference dataPedidoEmpresaRef = empresaRef
				.child("dataPedido");
		dataPedidoEmpresaRef.setValue(ServerValue.TIMESTAMP);

		DatabaseReference dataStatusPedidoEmpresaRef = empresaRef
				.child("dataStatusPedido");
		dataStatusPedidoEmpresaRef.setValue(ServerValue.TIMESTAMP);





		DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
				.child("usuarioPedidos")
				.child(getIdCliente())
				.child(getId());
		usuarioRef.setValue(this);

		DatabaseReference dataPedidousuarioRef = usuarioRef
				.child("dataPedido");
		dataPedidousuarioRef.setValue(ServerValue.TIMESTAMP);

		DatabaseReference dataStatusPedidoUsuarioRef = usuarioRef
				.child("dataStatusPedido");
		dataStatusPedidoUsuarioRef.setValue(ServerValue.TIMESTAMP);

	}

	public void atualizar(){

		DatabaseReference empresaStatusPedido = FirebaseHelper.getDatabaseReference()
				.child("empresaPedidos")
				.child(getIdEmpresa())
				.child(getId())
				.child("statusPedido");
		empresaStatusPedido.setValue(getStatusPedido());

		DatabaseReference empresaDataStatusPedido = FirebaseHelper.getDatabaseReference()
				.child("empresaPedidos")
				.child(getIdEmpresa())
				.child(getId())
				.child("dataStatusPedido");
		empresaDataStatusPedido.setValue(ServerValue.TIMESTAMP);





		DatabaseReference usuarioStatusPedido = FirebaseHelper.getDatabaseReference()
				.child("usuarioPedidos")
				.child(getIdCliente())
				.child(getId())
				.child("statusPedido");
		usuarioStatusPedido.setValue(getStatusPedido());

		DatabaseReference usuarioDataStatusPedido = FirebaseHelper.getDatabaseReference()
				.child("usuarioPedidos")
				.child(getIdCliente())
				.child(getId())
				.child("dataStatusPedido");
		usuarioDataStatusPedido.setValue(ServerValue.TIMESTAMP);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Long getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Long dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Long getDataStatusPedido() {
		return dataStatusPedido;
	}

	public void setDataStatusPedido(Long dataStatusPedido) {
		this.dataStatusPedido = dataStatusPedido;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public List<ItemPedido> getItemPedidoList() {
		return itemPedidoList;
	}

	public void setItemPedidoList(List<ItemPedido> itemPedidoList) {
		this.itemPedidoList = itemPedidoList;
	}

	public Double getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(Double taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(Double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
}
