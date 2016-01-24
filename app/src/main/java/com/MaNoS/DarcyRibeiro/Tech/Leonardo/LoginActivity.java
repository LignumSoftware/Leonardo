package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends Activity {

    private DataBaseHelper helper;
    private Aluno a;
    private AlunoDao ad;
    private Context ctx;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ctx = this;
        setContentView(R.layout.user_credential);
        final EditText edtTxtSenha = (EditText) findViewById(R.id.uc_txt_password);
        final EditText edtTxtMatricula = (EditText) findViewById(R.id.uc_txt_username);
        final Button btnLog = (Button) findViewById(R.id.uc_cmd_ok);
        try {
            helper = new DataBaseHelper(this);
        } catch (IOException e) {
            Log.e("erro", e.getMessage());
            e.printStackTrace();
        }
        a = new Aluno();
        ad = new AlunoDao(helper);

        btnLog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("LoginActivity.onClick()", "Inicio do método");
                Boolean logou = false;
                String senha = edtTxtSenha.getText().toString();
                String matricula = edtTxtMatricula.getText().toString();
                a = new Aluno(matricula, senha);
                ad = new AlunoDao(helper);
                try {
                    logou = ad.login(ctx, a);
                } catch (Exception e) {
                    Toast.makeText(ctx, "Login Falhou", Toast.LENGTH_LONG).show();
                }
                helper.close();
                if (a.getLogado() && logou) {
                    Toast.makeText(ctx, "Aluno logado", Toast.LENGTH_LONG).show();
                    iniciamenu();
                } else {
                    Toast.makeText(ctx, "Login inválido", Toast.LENGTH_LONG).show();
                    recreate();
                }
            }
        });

    }

    protected void onStart() {
        super.onStart();
        ad.readAluno(a);
        if (a.getMatricula() != "000000") {
            helper.close();
            iniciamenu();
        }
    }


    private void iniciamenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
