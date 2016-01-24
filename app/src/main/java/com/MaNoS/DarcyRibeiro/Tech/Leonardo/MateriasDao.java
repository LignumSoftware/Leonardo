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


public class MateriasDao {
    private DataBaseHelper helper;
    protected SQLiteDatabase db;

    public MateriasDao(DataBaseHelper helper) {
        this.helper = helper;
    }

    public String gravaMaterias(ArrayList<Materia> materias) {
        Log.i("MateriasDao - gravaMaterias", "inicio");
        try {
            db = helper.opendatabase();
        } catch (SQLException e) {
            Log.e("erro ao abrir o banco de dados", e.getMessage());
            e.printStackTrace();
        }
        for (Materia registro : materias) {
            ContentValues newValues = new ContentValues();
            newValues.put("Materia", registro.getMateria());
            Cursor m = db.rawQuery("SELECT * FROM materia WHERE Materia='" + registro.getMateria() + "'", null);
            if (m.getCount() > 0) {
                Log.e("Materia ja incluida", registro.getMateria());
            } else {
                Log.e("tentando inserir registro", "inserindo...");
                db.insert("materia", null, newValues);
            }
        }
        db.close();
        Log.i("MateriasDao - gravaMaterias", "fim");
        return "atualizado";
    }

    public ArrayList<Materia> leMaterias() {
        ArrayList<Materia> materias = new ArrayList<Materia>();
        db = helper.opendatabase();
        Log.i("Menu - recuperando as materias do banco", "materias");
        Cursor m = db.rawQuery("SELECT * FROM materia", null);
        if (m.getCount() > 0) {
            m.moveToFirst();
            while (!m.isAfterLast()) {
                Materia materia = new Materia();
                materia.setMateria(m.getString(0));
                materias.add(materia);
                m.moveToNext();
            }
            Log.i("Le materias", "Recuperei " + m.getCount() + " materias");
            m.close();
        }
        db.close();
        return materias;
    }

    protected Boolean AtualizarMaterias(Context ctx) {
        Boolean atualizou = false;
        try {
            GetMateriasTask up = new GetMateriasTask(ctx, helper);
            up.execute();
            String result = up.get();
            Log.i("AtualizarMaterias- result", result);
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
