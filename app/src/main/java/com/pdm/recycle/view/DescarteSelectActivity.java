package com.pdm.recycle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.pdm.recycle.R;

import java.util.ArrayList;

public class DescarteSelectActivity extends AppCompatActivity {

    ArrayList<String> listaResiduos = new ArrayList<>();
    private CheckBox checkMetal,
            checkPapelPapelao,
            checkPlastico,
            checkPilhaBateria,
            checkOleoCozinha,
            checkResiduosEletronicos,
            checkResiduosOrganicos,
            checkOutros;
    private String discardText;
    //private String emailUserAutenticado;
    //private TextView descarteSelecionados;
    //private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_descarte);

        //String valor = checkbox.getText().toString();

        this.checkMetal = findViewById(R.id.metal);
        checkPapelPapelao = findViewById(R.id.papelPapelao);
        checkPlastico = findViewById(R.id.plastico);
        checkPilhaBateria = findViewById(R.id.pilhaBateria);
        checkOleoCozinha = findViewById(R.id.oleoCozinha);
        checkResiduosEletronicos = findViewById(R.id.residuoEletronico);
        checkResiduosOrganicos = findViewById(R.id.residuoOrganio);
        checkOutros = findViewById(R.id.outros);

    }

    public void checkbox(){

        discardText ="";

        if (checkMetal.isChecked()){
            String metalSelecionado =  checkMetal.getText().toString();
            //discardText = metalSelecionado;
            listaResiduos.add(metalSelecionado);
        }
        if (checkPapelPapelao.isChecked()){
            String papelPapelaoSelecionado =  checkPapelPapelao.getText().toString();
            //discardText = discardText + " " +  papelPapelaoSelecionado;
            listaResiduos.add(papelPapelaoSelecionado);
        }
        if (checkPlastico.isChecked()){
            String plasticoSelecionado =  checkPlastico.getText().toString();
            //discardText = discardText + " " + plasticoSelecionado;
            listaResiduos.add(plasticoSelecionado);
        }
        if (checkPilhaBateria.isChecked()){
            String pilhaBateriaSelecionado =  checkPilhaBateria.getText().toString();
            //discardText = discardText + " " + pilhaBateriaSelecionado;
            listaResiduos.add(pilhaBateriaSelecionado);
        }
        if (checkOleoCozinha.isChecked()){
            String oleoCozinhaSelecionado =  checkOleoCozinha.getText().toString();
            //discardText = discardText + " " + oleoCozinhaSelecionado;
            listaResiduos.add(oleoCozinhaSelecionado);
        }
        if (checkResiduosEletronicos.isChecked()){
            String residuosEletronicosSelecionado =  checkResiduosEletronicos.getText().toString();
            //discardText = discardText + " " +  residuosEletronicosSelecionado;
            listaResiduos.add(residuosEletronicosSelecionado);
        }
        if (checkResiduosOrganicos.isChecked()){
            String residuosOrganicosSelecionado =  checkResiduosOrganicos.getText().toString();
            //discardText = discardText + " " + residuosOrganicosSelecionado;
            listaResiduos.add(residuosOrganicosSelecionado);
        }
        if (checkOutros.isChecked()){
            String outrosSelecionado =  checkOutros.getText().toString();
            //discardText = discardText + " " + outrosSelecionado;
            listaResiduos.add(outrosSelecionado);
        }

        //redirectMapsDescarte(discardText);

        if (!listaResiduos.isEmpty()) {
            redirectMapsDescarte(listaResiduos);

            //descarteSelecionados.setText(discardText);
            //redirectMapsDescarte(descarteSelecionados);
            Log.i("TIPO_Descarte", discardText);
            Log.i("TIPO_Des_Array", String.valueOf(listaResiduos));
        }else{
            Toast.makeText(DescarteSelectActivity.this,
                    "Selecione ao menos um tipo de resíduo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void avancarMapaDescarte(View view){
        checkbox();
    }

    public void redirectMapsDescarte(ArrayList<String> view) {
        Intent it = new Intent(this, DescarteLocalizacaoActivity.class);
        it.putExtra("chave", discardText);
        it.putStringArrayListExtra("residuosSelecionados", listaResiduos);
        startActivity(it);
    }

    public void abrirTelaPrincipal(View v){
        Intent intent = new Intent(this, MainHomeActivity.class);
        startActivity( intent );
    }

}