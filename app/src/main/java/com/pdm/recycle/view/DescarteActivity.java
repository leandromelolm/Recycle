package com.pdm.recycle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.pdm.recycle.R;

public class DescarteActivity extends AppCompatActivity {

    //private CheckBox checkbox;

    private CheckBox checkMetal,
            checkPapelPapelao,
            checkPlastico,
            checkPilhaBateria,
            checkOleoCozinha,
            checkResiduosEletronicos,
            checkResiduosOrganicos,
            checkOutros;
    private String discardText;
    //private TextView descarteSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_descarte);

        //String valor = checkbox.getText().toString();

        this.checkMetal = findViewById(R.id.metais);
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
            discardText = metalSelecionado;
        }
        if (checkPapelPapelao.isChecked()){
            String papelPapelaoSelecionado =  checkPapelPapelao.getText().toString();
            discardText = discardText + " " +  papelPapelaoSelecionado;
        }
        if (checkPlastico.isChecked()){
            String plasticoSelecionado =  checkPlastico.getText().toString();
            discardText = discardText + " " + plasticoSelecionado;
        }
        if (checkPilhaBateria.isChecked()){
            String pilhaBateriaSelecionado =  checkPilhaBateria.getText().toString();
            discardText = discardText + " " + pilhaBateriaSelecionado;
        }
        if (checkOleoCozinha.isChecked()){
            String oleoCozinhaSelecionado =  checkOleoCozinha.getText().toString();
            discardText = discardText + " " + oleoCozinhaSelecionado;
        }
        if (checkResiduosEletronicos.isChecked()){
            String residuosEletronicosSelecionado =  checkResiduosEletronicos.getText().toString();
            discardText = discardText + " " +  residuosEletronicosSelecionado;
        }
        if (checkResiduosOrganicos.isChecked()){
            String residuosOrganicosSelecionado =  checkResiduosOrganicos.getText().toString();
            discardText = discardText + " " + residuosOrganicosSelecionado;
        }
        if (checkOutros.isChecked()){
            String outrosSelecionado =  checkOutros.getText().toString();
            discardText = discardText + " " + outrosSelecionado;
        }

        redirectMapsDescarte(discardText);

        //descarteSelecionados.setText(discardText);
        //redirectMapsDescarte(descarteSelecionados);

    }



    public void avancarMapaDescarte(View view){

        checkbox();
    }

    public void redirectMapsDescarte(String view) {
        /*
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        */

        Intent it = new Intent(this, HomeActivity.class);
        it.putExtra("chave", discardText);
        //it.putExtra("chave", it.putExtra("chave", String.valueOf(descarteSelecionados)));
        startActivity(it);

    }
    public void abrirTelaPrincipal(View v){
        //Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        Intent intent = new Intent(this, MainHomeActivity.class);
        startActivity( intent );
    }

}