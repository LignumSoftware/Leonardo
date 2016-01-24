package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

public class TipodeTarefa {
    private Long IdTipo;
    private String NomeTipodeTarefa;

    public TipodeTarefa() {
        this.IdTipo = (long) 0;
        this.NomeTipodeTarefa = "";
    }

    public TipodeTarefa(long id) {
        this.IdTipo = id;
        this.NomeTipodeTarefa = "";
    }

    public TipodeTarefa(long id, String Tipo) {
        this.IdTipo = id;
        this.NomeTipodeTarefa = Tipo;
    }

    public Long getIdTipo() {
        return IdTipo;
    }

    public void setIdTipo(Long idTipo) {
        IdTipo = idTipo;
    }

    public String getNomeTipodeTarefa() {
        return NomeTipodeTarefa;
    }

    public void setNomeTipodeTarefa(String nomeTipodeTarefa) {
        NomeTipodeTarefa = nomeTipodeTarefa;
    }

    public String tipoToJSON() {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.array();
            jsonStringer.object()
                    .key("idtipo").value(IdTipo)
                    .key("tipo").value(NomeTipodeTarefa)
                    .endObject();
            jsonStringer.endArray();
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.e("Erro na geração do JSON de Matéria", e.getMessage());
            return null;
        }
    }

    public ArrayList<TipodeTarefa> jSONToTipodeTarefa(String json) {
        Log.i("JsontoTipodeTarefa", json);
        ArrayList<TipodeTarefa> tipos = new ArrayList<TipodeTarefa>();
        Log.i("JSONToTipodeTarefa", json);
        try {
            JSONArray tiposLists = new JSONArray(json);
            for (int j = 0; j < tiposLists.length(); j++) {
                JSONObject tiposLista = tiposLists.getJSONObject(j);
                TipodeTarefa tipo = new TipodeTarefa();
                tipo.setIdTipo(tiposLista.getLong("idtipo"));
                tipo.setNomeTipodeTarefa(Html.fromHtml(tiposLista.getString("tipo")).toString());
                tipos.add(tipo);
            }
        } catch (JSONException e) {
            Log.e("JsontoTipodeTarefa", "Erro no parsing do JSON de Matéria", e);
            return null;
        }
        return tipos;
    }
}