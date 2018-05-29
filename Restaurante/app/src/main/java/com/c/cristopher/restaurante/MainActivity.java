package com.c.cristopher.restaurante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.cadastro_restaurante));
        toolbar.setSubtitle(getResources().getString(R.string.menu_principal));
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //Retorna o inflater de menu //Atribui ao menu(res/menu/menu.xml) a view
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_list :
                startActivity(new Intent(MainActivity.this, ListaRestauranteActivity.class ));
                return true;

            case R.id.menu_save :
                startActivity(new Intent(MainActivity.this, TelaCadastroRestaurante.class));
                return true;

        }

        return true;
    }

}
