package com.diegolima.ifoodclone.activities.empresa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.diegolima.ifoodclone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmpresaHomeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empresa_home);


		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupWithNavController(bottomNavigationView, navController);
	}
}