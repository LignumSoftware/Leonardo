package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class GetTarefasTask extends AsyncTask<Aluno, Void, String> {
    private final String url = "http://fetarco-df.esp.br/leonardo/lertarefas.php";
    private ProgressDialog progress;
    private Context context;
    private String json;
    private DataBaseHelper helper;
    private String resultado;

    public GetTarefasTask(Context context, DataBaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    protected void onPreExecute() {
        try {
            progress = ProgressDialog.show(context, "Aguarde...", "Lendo e Atualizando tarefas do servidor web", true, true);
        } catch (Exception e) {

        }
    }

    protected String doInBackground(Aluno... params) {
        try {
            Aluno[] aluno = params;
            WebClient client = new WebClient(url);
            json = aluno[0].alunoToJSON();
            String resposta = client.post(json);
            Tarefa tarefa = new Tarefa();
            TarefasDao td = new TarefasDao(helper);
            ArrayList<Tarefa> tar = tarefa.jSONToTarefa(resposta);
            td.gravaTarefas(tar);
            resultado = "tarefas recuperadas";

        } catch (Exception e) {
            resultado = "Erro ao recuperar tarefas " + e.getMessage();
        }
        progress.dismiss();
        return resultado;
    }

    protected void onPostExecute(String resultado) {
        Toast.makeText(context, "Tarefas atualizadas", Toast.LENGTH_LONG).show();
    }
}