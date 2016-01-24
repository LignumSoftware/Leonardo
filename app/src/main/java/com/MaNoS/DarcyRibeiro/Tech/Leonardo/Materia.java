package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

public class Materia {
    private String Materia;

    public Materia() {
        this.Materia = "";
    }

    public String getMateria() {
        return Materia;
    }

    public void setMateria(String materia) {
        Materia = materia;
    }

    public String materiaToJSON() {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.array();
            jsonStringer.object()
                    .key("Materia").value(Materia)
                    .endObject();
            jsonStringer.endArray();
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.e("Erro na geração do JSON de Matéria", e.getMessage());
            return null;
        }
    }

    public ArrayList<Materia> jSONToMateria(String json) {
        Log.i("JsontoMateria", json);
        ArrayList<Materia> materias = new ArrayList<Materia>();
        Log.i("JSONToMateria", json);
        try {
            JSONArray materiasLists = new JSONArray(json);
            for (int j = 0; j < materiasLists.length(); j++) {
                JSONObject materiasList = materiasLists.getJSONObject(j);
                Materia mat = new Materia();
                mat.setMateria(Html.fromHtml(materiasList.getString("Materia")).toString());
                Log.i("Materia", mat.getMateria());
                materias.add(mat);
            }
        } catch (JSONException e) {
            Log.e("JsontoMateria", "Erro no parsing do JSON de Matéria", e);
            return null;
        }
        return materias;
    }
}



