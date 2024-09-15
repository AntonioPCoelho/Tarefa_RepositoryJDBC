package com.coelho.library;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJDBCImpl implements IUsuarioRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return this.jdbcTemplate.query("SELECT * from usuarios",
            (rs, rowNum)->
                new Usuario(rs.getLong("id"), rs.getString("nome"), rs.getInt("anoNascimento")));
    }

    @Override
    public Usuario getUsuarioById(long id) {
        List<Usuario> usuarios = this.jdbcTemplate.query("SELECT * FROM usuarios WHERE id = ?",
            (rs, rowNum) -> 
                new Usuario(rs.getLong("id"), rs.getString("nome"), rs.getInt("anoNascimento")),id);
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    @Override
    public boolean cadastraUsuario(Usuario usuario) {
        this.jdbcTemplate.update(
            "INSERT INTO usuarios(id, nome, anoNascimento) VALUES (?,?,?)",
            usuario.getId(), usuario.getNome(), usuario.getAnoNascimento());
        return true;
    }

    @Override
    public boolean removeUsuario(long codigo) {
        this.jdbcTemplate.update("DELETE FROM usuarios WHERE id = ?", codigo);
        return true;
    }
}

