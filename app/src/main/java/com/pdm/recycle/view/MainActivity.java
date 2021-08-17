package com.pdm.recycle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pdm.recycle.R;
import com.pdm.recycle.control.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private String emailUserAutenticado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    public void redirectLogin(View v) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void redirectRegister(View v) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual != null) {
            emailUserAutenticado = usuarioAtual.getEmail();
            Toast.makeText(MainActivity.this,
                    "Bem Vindo, " + usuarioAtual.getEmail(),
                    Toast.LENGTH_LONG).show();
            abrirTelaPrincipal();
        }
    }

        public void abrirTelaPrincipal(){
            Intent intent = new Intent(MainActivity.this, MainHomeActivity.class);
            intent.putExtra("chaveEmail", String.valueOf(emailUserAutenticado));
            startActivity( intent );
        }
}