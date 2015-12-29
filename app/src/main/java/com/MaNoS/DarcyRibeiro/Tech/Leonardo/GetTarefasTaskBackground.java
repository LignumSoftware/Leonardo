package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

public class GetTarefasTaskBackground extends AsyncTask<Aluno, Void, String> {
    private final String url = "http://fetarco-df.esp.br/leonardo/lertarefas.php";
    private Context context;
    private String json;
    private DataBaseHelper helper;
    private String resultado;

    public GetTarefasTaskBackground(Context context, DataBaseHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    protected void onPreExecute() {

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
//			progress.dismiss();
        } catch (Exception e) {
            resultado = "Erro ao recuperar tarefas " + e.getMessage();
        }

        return resultado;
    }

    protected void onPostExecute(String resultado) {
        Toast.makeText(context, "Tarefas atualizadas", Toast.LENGTH_LONG).show();
    }
}