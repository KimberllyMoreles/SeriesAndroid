package com.aluno.series;

import java.io.Serializable;

/**
 * Created by aluno on 14/12/16.
 */
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long _id;
    public String nome;
    public String sinopse;
    public String diretor;
    public int status;

    @Override
    public String toString() {
        return "Serie{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", diretor='" + diretor + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}