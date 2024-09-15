package com.coelho.library;

public class Usuario {
    private long id;
    private String nome;
    private int anoNascimento;

    public Usuario(long id, String nome, int anoNascimento) {
        this.id = id;
        this.nome = nome;
        this.anoNascimento = anoNascimento;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", anoNascimento=" + anoNascimento + "]";
    }
}