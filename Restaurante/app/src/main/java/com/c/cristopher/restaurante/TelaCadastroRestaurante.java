package com.c.cristopher.restaurante;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class TelaCadastroRestaurante extends AppCompatActivity {
    private static final String TAG = "_TELA_CAD_RESTAURANTE";
    private EditText nome, endereco;
    private Restaurante restaurante;
    private Spinner sTipo;
    private String[] tipo = new String[]{"Rodízio", "Fast Food", "Buffet"};
    private Button salvar;
    private int[] imgs = {R.drawable.pizza, R.drawable.fast_food, R.drawable.lunch};
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_restaurante);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.tb_title));
        toolbar.setSubtitle(getResources().getString(R.string.tb_cadastro));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nome = findViewById(R.id.etNome);
        endereco = findViewById(R.id.etEndereco);
        sTipo = findViewById(R.id.tipo);
        img = findViewById(R.id.img);

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_item);

        sTipo.setAdapter(adaptador);

        sTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                img.setImageResource(imgs[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getIntent();
        restaurante = (Restaurante) intent.getSerializableExtra("restaurante");

        if (restaurante != null){
            nome.setText(restaurante.getNome());
            endereco.setText(restaurante.getEndereco());
            for(int i = 0; i < tipo.length; i++){
                if (restaurante.getTipo().equals(tipo[i])){
                    sTipo.setSelection(i);
                }
            }
        }

        salvar = findViewById(R.id.bt_salvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean restauranteNovo = false;

                if (restaurante == null){
                    restaurante = new Restaurante();
                    restauranteNovo = true;
                }

                restaurante.setNome(nome.getText().toString());
                restaurante.setEndereco(endereco.getText().toString());
                restaurante.setTipo(sTipo.getSelectedItem().toString());

                try{
                    if (restauranteNovo){
                        RestauranteDAO dao = new RestauranteDAO(TelaCadastroRestaurante.this);
                        dao.inserirRestaurante(restaurante);
                        dao.close();
                    } else {
                        RestauranteDAO dao = new RestauranteDAO(TelaCadastroRestaurante.this);
                        dao.alteraRestaurante(restaurante);
                        dao.close();
                    }
                    Toast.makeText(TelaCadastroRestaurante.this, "Restaurante "+nome.getText().toString()+" cadastrado com sucesso!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e(TAG, "Erro ao inserir o restaurante: "+nome.getText().toString());
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
