package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class EnviaJsonTask extends AsyncTask<Aluno, Void, Aluno> {
    private final String url = "http://fetarco-df.esp.br/leonardo/logarandroid.php";
    private String json;
    private Context context;
    private ProgressDialog progress;

    public EnviaJsonTask(Context ctx, Aluno a) {
        this.context = ctx;
    }

    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde", "Fazendo login do aluno...", true, true);
    }

    protected Aluno doInBackground(Aluno... params) {
        String resposta;
        Aluno Alogar = params[0];
        try {
            WebClient client = new WebClient(url);
            json = Alogar.alunoToJSONLogin();
            Log.i("json", json.toString());
            resposta = client.post(json);
            Log.i("resposta do login", resposta);
            Alogar.jSONToAluno(resposta);
        } catch (Exception e) {
            Log.e("Erro no EnviaJsonTask", e.getMessage());
            Alogar.setMatricula("000000");
        }
        if (Alogar.getMatricula() == "000000") {
            Alogar.setLogado(false);
            try {
                throw new Exception("falha no login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alogar.setLogado(true);
        }
        progress.dismiss();
        return Alogar;
    }

    protected void onPostExecute(Aluno a) {
    }
}
