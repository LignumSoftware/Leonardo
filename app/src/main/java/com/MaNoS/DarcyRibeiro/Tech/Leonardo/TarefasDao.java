package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;


public class TarefasDao {
    private DataBaseHelper helper;
    protected SQLiteDatabase db;

    public TarefasDao(DataBaseHelper helper) {
        this.helper = helper;
    }

    public String gravaTarefas(ArrayList<Tarefa> er) {
        Log.i("TarefasDao - gravaTarefas", "inicio");
        try {
            db = helper.opendatabase();
        } catch (SQLException e) {
            Log.e("erro ao abrir o banco de dados", e.getMessage());
            e.printStackTrace();
        }
        for (Tarefa registro : er) {
            ContentValues newValues = new ContentValues();
            newValues.put("idtarefas", registro.getIdTarefa());
            newValues.put("Descricao", registro.getDesc());
            newValues.put("tipo", registro.getTipo());
            newValues.put("Materia", registro.getMat());
            newValues.put("Data", String.valueOf(registro.getDate()));
            newValues.put("Turma", registro.getTurma());
            Log.i("tarefa a ser incluída", registro.getMat() + registro.getData() + registro.getDesc() + registro.getTipo() + registro.getIdTarefa() + registro.getTurma());
            if (registro.getIdTarefa() != 0) {
                String[] args = new String[]{String.valueOf(registro.getIdTarefa())};
                Cursor m = db.rawQuery("SELECT * FROM tarefas WHERE idtarefas=" + String.valueOf(registro.getIdTarefa()), null);
                Log.i("inserindo tarefa - select " + "SELECT * FROM tarefas WHERE idtarefas=" + String.valueOf(registro.getIdTarefa()), String.valueOf(m.getCount()));
                m.moveToFirst();
                if (m.getCount() > 0) {
                    Log.e("tentando alterar registro", String.valueOf(registro.getIdTarefa()) + " " + String.valueOf(m.getInt(7)));
                    newValues.put("Status", m.getInt(7));
                    db.update("tarefas", newValues, "idtarefas=?", args);
                    Log.e("alterado o registro", String.valueOf(registro.getIdTarefa()));
                } else {
                    Log.e("tentando inserir registro", "inserindo...");
                    newValues.put("Status", 0);
                    db.insert("tarefas", null, newValues);
                }
                m.close();
            }
        }
        db.close();
        Log.i("TarefasDao - gravaTarefas", "fim");
        return "atualizado";
    }


    public ArrayList<Tarefa> menu(Aluno a) {
        ArrayList<Tarefa> tarefasdomenu = new ArrayList<Tarefa>();
        String[] args = {a.getTurma()};
        db = helper.opendatabase();
        Log.i("Menu - recuperando as tarefas do aluno " + a.getMatricula(), "turma " + a.getTurma());
        Cursor m = db.rawQuery("SELECT * FROM tarefas WHERE Turma=? and Status=0 ORDER BY Data DESC", args);
        if (m.getCount() > 0) {
            m.moveToFirst();
            while (!m.isAfterLast()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setDesc(m.getString(3));
                tarefa.setIdTarefa(m.getLong(0));
                tarefa.setMat(m.getString(4));
                tarefa.setDate(Date.valueOf(m.getString(2)));
                tarefa.setTipo(m.getInt(5));
                tarefasdomenu.add(tarefa);
                m.moveToNext();
            }
            Log.i("Menu - recuperei " + m.getCount() + " tarefas do aluno " + a.getMatricula(), "turma " + a.getTurma());
            m.close();
        }
        db.close();
        return tarefasdomenu;
    }

    @SuppressWarnings("deprecation")
    public ArrayList<Tarefa> notificacao(Aluno a) {
        ArrayList<Tarefa> tarefasdanotificacao = new ArrayList<Tarefa>();
        String[] args = {a.getTurma()};
        Calendar calendar = Calendar.getInstance();
        Log.i("data", calendar.toString());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Log.i("data 2", calendar.toString());
        Date date = new Date(calendar.getTimeInMillis());
        db = helper.opendatabase();
        Log.i("Menu - recuperando as tarefas do aluno " + a.getMatricula(), "turma " + a.getTurma());
        Cursor m = db.rawQuery("SELECT * FROM tarefas WHERE Turma=? and Status=0 ORDER BY Data DESC", args);
        if (m.getCount() > 0) {
            m.moveToFirst();
            while (!m.isAfterLast()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setDesc(m.getString(3));
                tarefa.setIdTarefa(m.getLong(0));
                tarefa.setMat(m.getString(4));
                tarefa.setDate(Date.valueOf(m.getString(2)));
                tarefa.setTipo(m.getInt(5));
                Log.i("datas", tarefa.getDate().toString());
                Log.i("date", date.toString());
                if (tarefa.getDate().toString().equals(date.toString()))
                    tarefasdanotificacao.add(tarefa);
                m.moveToNext();
            }
            Log.i("Menu - recuperei " + m.getCount() + " tarefas do aluno " + a.getMatricula(), "turma " + a.getTurma());
            m.close();
        }
        db.close();

        return tarefasdanotificacao;
    }

    public Boolean MudaStatus(long idTarefa) {
        Boolean resultado = false;
        Log.i("TarefasDao - MudaStatus", "inicio");
        try {
            db = helper.opendatabase();
        } catch (SQLException e) {
            Log.e("erro", e.getMessage());
            e.printStackTrace();
        }
        ContentValues newValues = new ContentValues();
        newValues.put("idtarefas", idTarefa);
        newValues.put("Status", 1);
        if (idTarefa != 0) {
            String[] args = new String[]{String.valueOf(idTarefa)};
            Cursor m = db.rawQuery("SELECT * FROM tarefas WHERE idtarefas=" + String.valueOf(idTarefa), null);
            if (m.getCount() > 0) {
                Log.e("tentando alterar status", String.valueOf(idTarefa));
                int atualizado = db.update("tarefas", newValues, "idtarefas=?", args);
                if (atualizado == 1) {
                    Log.e("Alterado o status da tarefa", String.valueOf(idTarefa));
                    resultado = true;
                }
            }
            m.close();
        }
        db.close();
        return resultado;
    }

    protected Boolean AtualizarTarefas(Context ctx, Aluno a) {
        Boolean atualizou = false;
        try {
            if (a.getMatricula() != "000000") {
                GetTarefasTask up = new GetTarefasTask(ctx, helper);
                up.execute(a);
                String result = up.get();
                Log.i("AtualizarTarefas- result", result);
                atualizou = true;
            }
        } catch (InterruptedException e) {
            Log.e("Interrrupted Exception", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Execution Exception", e.getMessage());
            e.printStackTrace();
        } catch (CancellationException ce) {
            Log.e("Cancellation Exception", ce.getMessage());
            ce.printStackTrace();

        }
        helper.closeDB();
        return atualizou;
    }

    protected Boolean AtualizarTarefasBackground(Context ctx, Aluno a) {
        Boolean atualizou = false;
        try {
            if (a.getMatricula() != "000000") {
                GetTarefasTaskBackground up = new GetTarefasTaskBackground(ctx, helper);
                up.execute(a);
                String result = up.get();
                Log.i("AtualizarTarefas- result", result);
                atualizou = true;
            }
        } catch (InterruptedException e) {
            Log.e("Interrrupted Exception", e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("Execution Exception", e.getMessage());
            e.printStackTrace();
        } catch (CancellationException ce) {
            Log.e("Cancellation Exception", ce.getMessage());
            ce.printStackTrace();

        }
        helper.closeDB();
        return atualizou;
    }

}
