package com.diegolima.ifoodclone.activities.empresa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.diegolima.ifoodclone.R;
import com.diegolima.ifoodclone.activities.empresa.EmpresaCategoriasActivity;
import com.diegolima.ifoodclone.helper.FirebaseHelper;
import com.diegolima.ifoodclone.model.Categoria;
import com.diegolima.ifoodclone.model.Produto;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EmpresaFormProdutoActivity extends AppCompatActivity {

	private final int REQUEST_CATEGORIA = 100;
	private final int REQUEST_GALERIA = 200;

	private ImageView img_produto;
	private EditText edt_nome;
	private CurrencyEditText edt_valor_antigo;
	private CurrencyEditText edt_valor;
	private Button btn_categoria;
	private EditText edt_descricao;
	private Categoria categoriaSelecionada = null;
	private LinearLayout l_edt_descricao;
	private Produto produto;
	private String caminhoImagem;
	TextView text_toolbar;

	private Boolean novoProduto = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empresa_form_produto);

		iniciaComponentes();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			produto = (Produto) bundle.getSerializable("produtoSelecionado");
			configDados();
		}

		configCliques();
	}

	private void configDados() {
		Picasso.get().load(produto.getUrlImagem()).into(img_produto);
		edt_nome.setText(produto.getNome());
		edt_valor.setText(String.valueOf(produto.getValor()));
		edt_valor_antigo.setText(String.valueOf(produto.getValorAntigo()));
		edt_descricao.setText(produto.getDescricao());
		recuperaCategoria();

		novoProduto = false;
		text_toolbar.setText("Edição");

	}

	private void recuperaCategoria() {
		DatabaseReference categoriasRef = FirebaseHelper.getDatabaseReference()
				.child("categorias")
				.child(FirebaseHelper.getIdFirebase())
				.child(produto.getIdCategoria());
		categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Categoria categoria = snapshot.getValue(Categoria.class);
				if (categoria != null) {
					btn_categoria.setText(categoria.getNome());
					categoriaSelecionada = categoria;
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}


	private void configCliques() {
		findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
		btn_categoria.setOnClickListener(view -> {
			Intent intent = new Intent(this, EmpresaCategoriasActivity.class);
			intent.putExtra("acesso", 1);
			startActivityForResult(intent, REQUEST_CATEGORIA);
		});

		l_edt_descricao.setOnClickListener(view -> {
			mostrarTeclado();
			edt_descricao.requestFocus();
		});

		img_produto.setOnClickListener(v -> verificaPermissaoGaleria());
	}

	public void validaDados(View view) {
		String nome = edt_nome.getText().toString().trim();
		double valor = (double) edt_valor.getRawValue() / 100;
		double valorAntigo = (double) edt_valor_antigo.getRawValue() / 100;
		String descricao = edt_descricao.getText().toString().trim();

		if (!nome.isEmpty()) {
			if (valor > 0) {
				if (categoriaSelecionada != null) {
					if (!descricao.isEmpty()) {
						ocultarTeclado();

						if (produto == null) {
							produto = new Produto();
						}
						produto.setIdEmpresa(FirebaseHelper.getIdFirebase());
						produto.setNome(nome);
						produto.setValor(valor);
						produto.setValorAntigo(valorAntigo);
						produto.setIdCategoria(categoriaSelecionada.getId());
						produto.setDescricao(descricao);

						if (novoProduto) {
							if (caminhoImagem != null) {
								salvarImagemFirebase();
							} else {
								ocultarTeclado();
								Snackbar.make(
										img_produto,
										"Selecione a imagem",
										Snackbar.LENGTH_SHORT
								).show();
							}
						}else{
							if (caminhoImagem != null){
								salvarImagemFirebase();
							}else{
								produto.salvar();
							}
						}
					} else {
						edt_descricao.requestFocus();
						edt_descricao.setError("Informe uma descrição");
						;
					}
				} else {
					ocultarTeclado();
					erroSalvarProduto("Selecione uma categoria para o produto.");
				}
			} else {
				edt_valor.requestFocus();
				edt_valor.setError("Informe um valor válido");
			}
		} else {
			edt_nome.requestFocus();
			edt_nome.setError("Informe um nome.");
		}
	}

	private void verificaPermissaoGaleria() {
		PermissionListener permissionlistener = new PermissionListener() {
			@Override
			public void onPermissionGranted() {
				abrirGaleria();
			}

			@Override
			public void onPermissionDenied(List<String> deniedPermissions) {
				Toast.makeText(getBaseContext(), "Permissão negada.", Toast.LENGTH_SHORT).show();
				;
			}
		};

		TedPermission.create()
				.setPermissionListener(permissionlistener)
				.setDeniedTitle("Permissão negada.")
				.setDeniedMessage("Para acessar a galeria precisa conceder permissão, deseja conceder agora?")
				.setDeniedCloseButtonText("Não")
				.setGotoSettingButtonText("Sim")
				.setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
				.check();
	}

	private void abrirGaleria() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, REQUEST_GALERIA);
	}

	private void salvarImagemFirebase() {
		StorageReference storageReference = FirebaseHelper.getStorageReference()
				.child("imagens")
				.child("produtos")
				.child(FirebaseHelper.getIdFirebase())
				.child(produto.getId() + ".JPEG");

		UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
		uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

			produto.setUrlImagem(task.getResult().toString());
			produto.salvar();

			if (novoProduto) {
				finish();
			}
		})).addOnFailureListener(e -> erroSalvarProduto(e.getMessage()));
	}

	private void iniciaComponentes() {
		text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Novo produto");

		img_produto = findViewById(R.id.img_produto);
		edt_nome = findViewById(R.id.edt_nome);

		edt_valor = findViewById(R.id.edt_valor);
		edt_valor.setLocale(new Locale("PT", "br"));

		edt_valor_antigo = findViewById(R.id.edt_valor_antigo);
		edt_valor_antigo.setLocale(new Locale("PT", "br"));

		btn_categoria = findViewById(R.id.btn_categoria);
		edt_descricao = findViewById(R.id.edt_descricao);
		l_edt_descricao = findViewById(R.id.l_edt_descricao);
	}

	private void erroSalvarProduto(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Atenção");
		builder.setMessage(msg);
		builder.setPositiveButton("OK", ((dialog, which) -> {
			dialog.dismiss();
		}));

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void ocultarTeclado() {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				edt_nome.getWindowToken(), 0
		);
	}

	private void mostrarTeclado() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CATEGORIA) {
				categoriaSelecionada = (Categoria) data.getSerializableExtra("categoriaSelecionada");
				btn_categoria.setText(categoriaSelecionada.getNome());
			} else if (requestCode == REQUEST_GALERIA) {
				Bitmap bitmap;

				Uri imagemSelecionada = data.getData();
				caminhoImagem = data.getData().toString();

				if (Build.VERSION.SDK_INT < 28) {
					try {
						bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemSelecionada);
						img_produto.setImageBitmap(bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imagemSelecionada);
					try {
						bitmap = ImageDecoder.decodeBitmap(source);
						img_produto.setImageBitmap(bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}