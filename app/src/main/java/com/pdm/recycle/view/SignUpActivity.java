package com.pdm.recycle.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.pdm.recycle.R;
import com.pdm.recycle.control.ConfiguracaoFirebase;
import com.pdm.recycle.helper.Base64Custom;
import com.pdm.recycle.model.Usuario;

public class SignUpActivity extends AppCompatActivity {

    private EditText campoNome, campoSobreNome, campoEmail, campoCPF, campoSenha, campoConfirmaSenha;
    private FirebaseAuth autenticacao;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        campoNome       = findViewById(R.id.registerName);
        campoSobreNome  = findViewById(R.id.registerLastName);
        campoEmail      = findViewById(R.id.registerEmail);
        campoCPF        = findViewById(R.id.registerCPF);
        campoSenha      = findViewById(R.id.registerPassword);
        campoConfirmaSenha= findViewById(R.id.repeatPassword);
    }
    public void cadastrarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    Toast.makeText(SignUpActivity.this,
                            "Sucesso ao cadastrar usuário!",
                            Toast.LENGTH_SHORT).show();
                    finish();

                    try{

                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId( identificadorUsuario);
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(SignUpActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarCadastroUsuario(View view){

        //Recuperar textos dos campos
        String textoNome        = campoNome.getText().toString();
        String textoSobreNome   = campoSobreNome.getText().toString();
        String textoEmail       = campoEmail.getText().toString();
        String textoCpf         = campoCPF.getText().toString();
        String textoSenha       = campoSenha.getText().toString();
        String confirmaSenha    = campoConfirmaSenha.getText().toString();

        if (!textoNome.isEmpty()) {
            if (!textoSobreNome.isEmpty()) {
                if (!textoEmail.isEmpty()) {
                    if (!textoCpf.isEmpty()) {
                        if (!textoSenha.isEmpty()) {
                            if (textoSenha.equals(confirmaSenha)) {
                                Usuario usuario = new Usuario();
                                usuario.setNome(textoNome);
                                usuario.setSobrenome(textoSobreNome);
                                usuario.setEmail(textoEmail);
                                usuario.setCpf(textoCpf);
                                usuario.setSenha(textoSenha);

                                cadastrarUsuario(usuario);

                            } else {
                                Toast.makeText(SignUpActivity.this,
                                        "As senhas não conferem!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Preencha o cpf!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this,
                            "Preencha o email!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this,
                        "Preencha o sobrenome!",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUpActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }
    }


/*    public void redirecHome(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    } */
}