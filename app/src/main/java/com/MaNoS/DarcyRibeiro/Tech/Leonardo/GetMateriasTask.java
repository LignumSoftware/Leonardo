package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class GetMateriasTask extends AsyncTask<Void, Void, String> {
    private final String url = "http://fetarco-df.esp.br/leonardo/lermaterias.php";
    private ProgressDialog progress;
    private Context context;
    private DataBaseHelper helper;
    private String resultado;

    public GetMateriasTask(Context context, DataBaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde...", "Lendo Matérias no servidor web", true, true);
    }

    protected String doInBackground(Void... params) {
        try {
            WebClient client = new WebClient(url);
            String resposta = client.post("");
            Materia materia = new Materia();
            MateriasDao md = new MateriasDao(helper);
            ArrayList<Materia> materias = materia.jSONToMateria(resposta);
            md.gravaMaterias(materias);
            resultado = "Matérias recuperadas";
        } catch (Exception e) {
            resultado = "Erro ao recuperar matérias " + e.getMessage();
        }
        progress.dismiss();
        return resultado;
    }

    protected void onPostExecute(String resultado) {
        Toast.makeText(context, "Matérias atualizadas", Toast.LENGTH_LONG).show();


    }
}
