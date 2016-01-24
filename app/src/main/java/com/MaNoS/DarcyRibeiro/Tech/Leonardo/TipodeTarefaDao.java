package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;


public class TipodeTarefaDao {
    private DataBaseHelper helper;
    protected SQLiteDatabase db;

    public TipodeTarefaDao(DataBaseHelper helper) {
        this.helper = helper;
    }

    public String gravaTipodeTarefas(ArrayList<TipodeTarefa> tipos) {
        Log.i("TipodeTarefasDao - gravaTipodeTarefas", "inicio");
        try {
            db = helper.opendatabase();
        } catch (SQLException e) {
            Log.e("erro ao abrir o banco de dados", e.getMessage());
            e.printStackTrace();
        }
        for (TipodeTarefa registro : tipos) {
            ContentValues newValues = new ContentValues();
            newValues.put("idtipo", registro.getIdTipo());
            newValues.put("tipo", registro.getNomeTipodeTarefa());
            Cursor m = db.rawQuery("SELECT * FROM tipo WHERE idtipo=" + String.valueOf(registro.getIdTipo()), null);
            if (m.getCount() > 0) {
                Log.e("TipodeTarefa ja incluída", String.valueOf(registro.getIdTipo()));
                db.update("tipo", newValues, "idtipo=" + String.valueOf(registro.getIdTipo()), null);
            } else {
                Log.e("tentando inserir registro", "inserindo...");
                db.insert("tipo", null, newValues);
            }
        }
        db.close();
        Log.i("TipodeTarefasDao - gravaTipodeTarefas", "fim");
        return "atualizado";
    }

    public ArrayList<TipodeTarefa> leTipodeTarefas() {
        ArrayList<TipodeTarefa> tipos = new ArrayList<TipodeTarefa>();
        db = helper.opendatabase();
        Log.i("Menu - recuperando as tipos do banco", "tipos");
        Cursor m = db.rawQuery("SELECT * FROM materia", null);
        if (m.getCount() > 0) {
            m.moveToFirst();
            while (!m.isAfterLast()) {
                TipodeTarefa tipo = new TipodeTarefa();
                tipo.setIdTipo(m.getLong(0));
                tipo.setNomeTipodeTarefa(m.getString(1));
                tipos.add(tipo);
                m.moveToNext();
            }
            Log.i("Le tipos", "Recuperei " + m.getCount() + " tipos");
            m.close();
        }
        db.close();
        return tipos;
    }

    protected Boolean AtualizarTipodeTarefas(Context ctx) {
        Boolean atualizou = false;
        try {
            GetTiposTask up = new GetTiposTask(ctx, helper);
            up.execute();
            String result = up.get();
            Log.i("AtualizarTipodeTarefas- result", result);
            atualizou = true;
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
        return atualizou;
    }
}
