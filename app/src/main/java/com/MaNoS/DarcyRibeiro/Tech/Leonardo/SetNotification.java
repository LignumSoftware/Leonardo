package com.MaNoS.DarcyRibeiro.Tech.Leonardo;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class SetNotification extends Service {
    private ArrayList<Tarefa> Tarefas = new ArrayList<Tarefa>();
    private ArrayList<String> Materias = new ArrayList<String>();
    private String[] contents;
    private Aluno a;
    private AlunoDao ad;
    private TarefasDao td;
    private DataBaseHelper helper;
    private Context ctx;
    StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        try {
            helper = new DataBaseHelper(ctx);
        } catch (IOException e) {
            Log.i("Db error", e.getMessage());
//            e.printStackTrace();
        }
        a = new Aluno();
        ad = new AlunoDao(helper);
        td = new TarefasDao(helper);
        ad.readAluno(a);
        Log.w("OncreateService", "fim do onCreate notificacao");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (a.getMatricula() != "000000") {
            Log.i("Aluno", "Logado");
            if (td.AtualizarTarefasBackground(ctx, a))
                Log.d("onStart - atualizar tarefas", "Tarefas atualizadas ao iniciar serviço");

            Log.d("onStart", "fim do método onStartService - aluno " + a.getMatricula());
            Tarefas = td.notificacao(a);
            contents = new String[Tarefas.size() + 2];
            int cont = 0;
            if (Tarefas.size() == 0) {
                Log.i("Tarefa nula", "Nula");
                Tarefa vazia = new Tarefa();
                vazia.setDesc("Nenhuma tarefa para amanhã");
                vazia.setMat("Não há tarefas");
                contents[cont] = "Não há tarefas";
                stringBuilder.append("Não há tarefas\nOlhe as próximas tarefas");
                Tarefas.add(new Tarefa());
                cont++;
            } else {
                for (Tarefa t : Tarefas) {
                    stringBuilder.append(t.getMat() + "\n");
                    contents[cont] = t.getMat();
                    Log.i("Contents", t.getMat() + "null");
                    cont++;
                }
            }
            contents[cont] = "Olhe as próximas tarefas";

        }
        intent = new Intent(this, MainActivity.class);
        NotificationUtil.create(this, "Tarefas", "Tarefas", stringBuilder.toString(), R.drawable.ic_launcher, 0, intent, contents);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
