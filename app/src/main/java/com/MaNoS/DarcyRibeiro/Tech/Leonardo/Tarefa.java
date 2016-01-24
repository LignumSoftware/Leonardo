package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tarefa {
    private Date data;
    private String desc;
    private String mat;
    private int status;
    private int tipo;
    private long idTarefa;
    private String Turma;

    public Tarefa(Date data, String desc, String mat, int status, int tipo, long idTarefa, String Turma) {
        super();
        Log.d("TarefaDao - Construtor(Date data, String desc, String mat, int Status, int tipo, long idTarefa,String Turma)", "Inicio do metodo");
        this.data = data;
        this.setDesc(desc);
        this.setMat(mat);
        this.setStatus(status);
        this.setTipo(tipo);
        this.setIdTarefa(idTarefa);
        this.setTurma(Turma);

    }

    public Tarefa(Date data, String desc, String mat, int tipo, long idTarefa, String Turma) {
        super();
        Log.d("TarefaDao - Construtor(Date data, String desc, String mat, int tipo, long idTarefa, String Turma)", "Inicio do metodo");
        this.data = data;
        this.setDesc(desc);
        this.setMat(mat);
        this.setTipo(tipo);
        this.setIdTarefa(idTarefa);
        this.setTurma(Turma);

    }


    public Tarefa() {
        Log.d("TarefaDao - Construtor(vazio)", "In�cio do m�todo");
    }

    public ArrayList<Tarefa> jSONToTarefa(String json) {
        Log.i("JsontoTarefa", json);
        ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();
        Log.i("JSONToTarefa", json);
        try {
            JSONArray tarefasLists = new JSONArray(json);
            for (int j = 0; j < tarefasLists.length(); j++) {
                JSONObject tarefasList = tarefasLists.getJSONObject(j);
                Tarefa oe = new Tarefa();
                oe.setIdTarefa(Long.parseLong(tarefasList.getString("idtarefas")));
                Log.i("Tarefa", String.valueOf(oe.getIdTarefa()));
                oe.setData(tarefasList.getString("Data"));
                Log.i("Tarefa", String.valueOf(oe.getData()));
                oe.setDesc(Html.fromHtml(tarefasList.getString("Descricao")).toString());
                Log.i("Tarefa", oe.getDesc());
                oe.setMat(Html.fromHtml(tarefasList.getString("Materia")).toString());
                Log.i("Tarefa", oe.getMat());
                oe.setTurma(tarefasList.getString("Turma"));
                oe.setTipo(Integer.parseInt(tarefasList.getString("tipo")));
                Log.i("tipo", String.valueOf(oe.getTipo()));
                tarefas.add(oe);
            }
        } catch (JSONException e) {
            Log.e("JsontoTarefa", "Erro no parsing do JSON", e);
            return null;
        }
        return tarefas;

    }

    void setTurma(String string) {
        this.Turma = string;

    }

    public String getData() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        return sdf2.format(data);
    }

    public void setData(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data = formatter.parse(date);
        } catch (ParseException e) {
            Log.e("erro", e.getMessage());
            e.printStackTrace();
        }

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setDate(Date data) {
        this.data = data;
    }

    public long getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(long idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(data);
    }

    public String getTurma() {
        return this.Turma;
    }


}
