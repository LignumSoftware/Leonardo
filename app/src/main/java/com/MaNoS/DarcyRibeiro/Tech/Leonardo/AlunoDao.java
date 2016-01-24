package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.concurrent.ExecutionException;


public class AlunoDao {
    private DataBaseHelper helper;
    private SQLiteDatabase db;


    public AlunoDao(DataBaseHelper helper) {
        this.helper = helper;
    }

    public String gravaAluno(Aluno a) {
        if (a.getLogado()) {
            ContentValues newValues = new ContentValues();
            newValues.put("Matricula", a.getMatricula());
            newValues.put("Senha", a.getSenha());
            newValues.put("Turma", a.getTurma());
            newValues.put("representante", a.getRepresentante());
            newValues.put("email", a.getEmail());
            String[] args = new String[]{String.valueOf(a.getMatricula())};
            db = helper.opendatabase();
            Cursor m = db.rawQuery("SELECT Matricula FROM alunos WHERE Matricula='" + a.getMatricula() + "'", null);
            Log.i("inserindo aluno - select " + "SELECT Matricula FROM alunos WHERE Matricula='" + a.getMatricula() + "'", String.valueOf(m.getCount()));
            if (m.getCount() > 0) {
                db.update("tarefas", newValues, "idtarefas=?", args);
                Log.e("Alterado registro do aluno", a.getMatricula());
            } else {
                Log.e("tentando inserir registro aluno", "inserindo...");
                db.insert("alunos", null, newValues);
            }
            m.close();
            db.close();
            return "Aluno atualizado";
        } else {
            return "Aluno inválido";
        }
    }

    public void readAluno(Aluno a) {
        db = helper.opendatabase();
        Cursor m = db.rawQuery("SELECT * FROM alunos", null);
        if (m.getCount() > 0) {
            m.moveToFirst();
            a.setMatricula(m.getString(0));
            a.setNome(m.getString(1));
            a.setEmail(m.getString(4));
            a.setRepresentante(m.getInt(5));
            a.setTurma(m.getString(3));
            a.setSenha(m.getString(2));
            if (a.getMatricula() == "000000")
                a.setLogado(false);
            else
                a.setLogado(true);
        } else {
            a.setMatricula("000000");
            a.setRepresentante(0);
            a.setTurma("000");
            a.setSenha("000000");
            a.setNome(" ");
            a.setEmail(" ");
            a.setLogado(false);
        }
        db.close();
    }


    public Boolean login(Context ctx, Aluno a) throws FailedLoginException {
        Boolean result = false;
        FailedLoginException fLogin = new FailedLoginException();
        try {
            if (!a.getLogado()) {
                EnviaJsonTask EnviaLogin = new EnviaJsonTask(ctx, a);
                EnviaLogin.execute(a);
                a = EnviaLogin.get();
                Log.i("gravando...", a.getMatricula() + " " + a.getTurma());
                if (a.getLogado()) {
                    gravaAluno(a);
                    result = true;
                } else
                    result = false;
            }
        } catch (InterruptedException e) {
            Log.e("erro login ", e.getMessage());
            result = false;
            throw fLogin;
        } catch (ExecutionException e) {
            Log.e("erro login ", e.getMessage());
            result = false;
            throw fLogin;
        }
        return result;
    }
}
