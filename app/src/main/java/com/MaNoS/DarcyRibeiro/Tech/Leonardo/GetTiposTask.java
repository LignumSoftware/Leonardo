package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class GetTiposTask extends AsyncTask<Void, Void, String> {
    private final String url = "http://fetarco-df.esp.br/leonardo/lertipos.php";
    private ProgressDialog progress;
    private Context context;
    private DataBaseHelper helper;
    private String resultado;

    public GetTiposTask(Context context, DataBaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde...", "Lendo tipos no servidor web", true, true);
    }

    protected String doInBackground(Void... params) {
        try {
            WebClient client = new WebClient(url);
            String resposta = client.post("");
            TipodeTarefa tipo = new TipodeTarefa();
            TipodeTarefaDao tipodao = new TipodeTarefaDao(helper);
            ArrayList<TipodeTarefa> tipos = tipo.jSONToTipodeTarefa(resposta);
            tipodao.gravaTipodeTarefas(tipos);
            resultado = "Tipos recuperados";
        } catch (Exception e) {
            resultado = "Erro ao recuperar tipos " + e.getMessage();
        }
        progress.dismiss();
        return resultado;
    }

    protected void onPostExecute(String resultado) {
        Toast.makeText(context, "Tipos atualizados", Toast.LENGTH_LONG).show();
    }
}
