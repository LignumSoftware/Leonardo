package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

//import android.content.Context;

public class Aluno {
    private String Senha;
    private String Matricula;
    private String Turma;
    private String Nome;
    private int representante;
    private String email;
    private Boolean logado;

    public Aluno() {
        Senha = "000000";
        Matricula = "000000";
        Turma = "000";
        Nome = " ";
        representante = 0;
        email = " ";
        logado = false;
    }


    public Aluno(String Matricula, String Senha, String Turma) {
        this.setMatricula(Matricula);
        this.setTurma(Turma);
        this.setSenha(Senha);
        this.setNome(" ");
        this.setRepresentante(0);
        this.setEmail(" ");
        this.setLogado(false);

    }

    public Aluno(String Matricula, String Senha) {
        this.setMatricula(Matricula);
        this.setSenha(Senha);
        this.setNome(" ");
        this.setRepresentante(0);
        this.setEmail(" ");
        this.setTurma("000");
        this.setLogado(false);
    }

    public Boolean getLogado() {
        return logado;
    }


    public void setLogado(Boolean logado) {
        this.logado = logado;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRepresentante() {
        return representante;
    }

    public void setRepresentante(int representante) {
        this.representante = representante;
    }

    public String getTurma() {
        return Turma;
    }

    public void setTurma(String turma2) {
        Turma = turma2;
    }

    public String getMatricula() {
        return Matricula;
    }

    protected void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getSenha() {
        return Senha;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String alunoToJSON() {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.array();
            jsonStringer.object()
                    .key("Matricula").value(Matricula)
                    .key("Nome").value(Nome)
                    .key("Senha").value(Senha)
                    .key("representante").value(representante)
                    .key("Turma").value(Turma)
                    .key("email").value(email)
                    .endObject();
            jsonStringer.endArray();
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.e("Erro na geração do JSON", e.getMessage());
            return null;
        }
    }

    public String alunoToJSONLogin() {
        try {
            JSONStringer jsonStringer = new JSONStringer();
            jsonStringer.array();
            jsonStringer.object()
                    .key("Matricula").value(Matricula)
                    .key("Senha").value(Senha)
                    .endObject();
            jsonStringer.endArray();
            return jsonStringer.toString();
        } catch (JSONException e) {
            Log.e("Erro na geração do JSON", e.getMessage());
            return null;
        }
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Aluno jSONToAluno(String json) throws Exception {
        JSONArray jsonAluno = null;
        try {
            jsonAluno = new JSONArray(json);
        } catch (JSONException e) {
            throw new Exception("Falha na conversão");
        }
        JSONObject alunojson = null;
        try {
            alunojson = jsonAluno.getJSONObject(0);
            setTurma(alunojson.getString("Turma"));
            setRepresentante(Integer.parseInt(alunojson.getString("representante")));
            setNome(alunojson.getString("Nome"));
            setMatricula(alunojson.getString("Matricula"));
            setSenha(alunojson.getString("Senha"));
            setEmail(alunojson.getString("email"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}
