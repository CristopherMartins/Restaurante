package com.c.cristopher.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaRestauranteActivity extends AppCompatActivity{
    private static final int ID_MENU_EDITAR_RESTAURANTE = 1;
    private static final int ID_MENU_EXCLUIR_RESTAURANTE = 2;
    private ListView lista_restaurantes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle(getResources().getString(R.string.tela_lista_restaurantes));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista_restaurantes = (ListView) findViewById(R.id.lista_restaurantes);
        //atualizarLista(); // alteração somente para mostrar o ciclo de vida
        // Registra o menu de contexto
        registerForContextMenu(lista_restaurantes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        RestauranteDAO dao = new RestauranteDAO(this);
        List<Restaurante> restaurantes = dao.buscaRestaurante();
        dao.close();
        CustomListAdapter adapter = new CustomListAdapter(this, restaurantes);
        lista_restaurantes.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem menuEditar = menu.add(0, ID_MENU_EDITAR_RESTAURANTE, 10, R.string.editar_restaurante);
        menuEditar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo info
                        = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Restaurante restaurante = (Restaurante) lista_restaurantes.getItemAtPosition(info.position);

                Intent it = new Intent(ListaRestauranteActivity.this, TelaCadastroRestaurante.class);
                it.putExtra("restaurante", restaurante);

                startActivity(it);

                return false;
            }
        });
        MenuItem menuExcluir = menu.add(0, ID_MENU_EXCLUIR_RESTAURANTE, 10, R.string.excluir_restaurante);
        menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info
                        = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Restaurante restaurante = (Restaurante) lista_restaurantes.getItemAtPosition(info.position);
                RestauranteDAO dao = new RestauranteDAO(ListaRestauranteActivity.this);
                dao.excluirRestaurante(restaurante);
                dao.close();
                atualizarLista();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
