package com.MaNoS.DarcyRibeiro.Tech.Leonardo;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private final ArrayList<Tarefa> mTarefas;


    public Adapter(Context context, ArrayList<Tarefa> tarefas) {
        this.mTarefas = tarefas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTarefas != null ? mTarefas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mTarefas != null ? mTarefas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View cView, ViewGroup parent) {
        View view;
        try {

            ViewHolder holder;
            Log.i("posicao " + String.valueOf(position), String.valueOf(cView) + " " + String.valueOf(parent));
            if (cView == null) {
                view = inflater.inflate(R.layout.tarefa_view, parent, false);
                holder = new ViewHolder();
                holder.mat = (TextView) view.findViewById(R.id.txtViewMat);
                holder.data = (TextView) view.findViewById(R.id.txtViewData);
                holder.desc = (TextView) view.findViewById(R.id.txtViewDesc);
                view.setTag(holder);
            } else {
                view = cView;
                holder = (ViewHolder) view.getTag();
            }
            Tarefa a = mTarefas.get(position);
            holder.mat.setText(a.getMat());
            holder.data.setText(a.getData());
            holder.desc.setText(a.getDesc());
        } catch (Exception e) {
            view = cView;
            Log.e("erro no getview", e.getMessage() + " getView " + String.valueOf(position));
        }
        return view;
    }

    static class ViewHolder {
        TextView mat;
        TextView data;
        TextView desc;
        int position;
    }


}
