package com.pdm.recycle.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.pdm.recycle.R;
import com.pdm.recycle.control.ConfiguracaoFirebase;
import com.pdm.recycle.model.Usuario;

public class SignInActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;
    private String emailUserAutenticado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail = findViewById(R.id.LoginEmail);
        campoSenha = findViewById(R.id.LoginPassword);

    }

    public void logarUsuario(Usuario usuario){

        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){
                    abrirTelaPrincipal();
                    emailUserAutenticado = usuario.getEmail();
                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (Exception e){
                        excecao = "Erro! tente Novamente! código do erro: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(SignInActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void validarAutenticacaoUsuario(View view){

        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se e-mail e senha foram digitados
        if( !textoEmail.isEmpty() ){
            if( !textoSenha.isEmpty() ){

                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setSenha( textoSenha );

                logarUsuario( usuario );

            }else {
                Toast.makeText(SignInActivity.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(SignInActivity.this,
                    "Preencha o email!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual =  autenticacao.getCurrentUser();
        if(usuarioAtual != null){
            emailUserAutenticado = usuarioAtual.getEmail();
            Toast.makeText(SignInActivity.this,
                    "Bem Vindo, " + usuarioAtual.getEmail(),
                    Toast.LENGTH_LONG).show();
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaCadastro(View view){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity( intent );
    }
    
    public void abrirTelaPrincipal(){
        Intent intent = new Intent(SignInActivity.this, MainHomeActivity.class);
        intent.putExtra("chaveEmail", String.valueOf(emailUserAutenticado));
        startActivity( intent );
    }
}