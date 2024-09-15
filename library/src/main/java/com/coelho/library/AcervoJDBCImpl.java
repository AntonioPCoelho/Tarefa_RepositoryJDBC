package com.coelho.library;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AcervoJDBCImpl implements IAcervoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AcervoJDBCImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Livro> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM livros",
            (rs, rowNum)->
                new Livro(rs.getInt("id"),rs.getString("titulo"),rs.getString("autor"),rs.getInt("ano"),rs.getInt("codigoUsuario")));
    }

    @Override
    public List<String> getTitulos() {
        return this.jdbcTemplate.query(
            "SELECT titulo FROM livros", 
                (rs, rowNum) -> rs.getString("titulo"));
}

    @Override
    public List<String> getAutores() {
        return this.jdbcTemplate.query(
            "SELECT autor FROM livros", 
                (rs, rowNum) -> rs.getString("autor"));
}

    @Override
    public List<Livro> getLivrosDoAutor(String autor) {
        return this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE autor = ?", 
            ps -> ps.setString(1, autor),
            (rs, rowNum) -> 
                new Livro(rs.getLong("id"),rs.getString("titulo"),rs.getString("autor"),rs.getInt("ano"), rs.getLong("codigoUsuario")));
}


    @Override
    public Livro getLivroTitulo(String titulo) {
        List<Livro> livros = this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE titulo = ?", 
            ps -> ps.setString(1, titulo),
            (rs, rowNum) -> 
                new Livro(rs.getLong("id"),rs.getString("titulo"),rs.getString("autor"),rs.getInt("ano"),rs.getLong("codigoUsuario")));

        return livros.isEmpty() ? null : livros.get(0);
}


    @Override
    public boolean cadastraLivroNovo(Livro livro) {
        this.jdbcTemplate.update(
            "INSERT INTO livros(id, titulo, autor, ano, codigoUsuario) VALUES (?,?,?,?,?)",
            livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAno(), livro.getCodigoUsuario());
        return true;
    }

    @Override
    public boolean removeLivro(long codigo) {
        this.jdbcTemplate.update("DELETE From livros WHERE id = ?", codigo);
        return true;
    }

    public boolean retiraLivro(long codigo, long codigoUsuario) {
        this.jdbcTemplate.update("UPDATE livros SET codigoUsuario = ? WHERE id = ?", codigoUsuario, codigo);
        return true;
    }

    public boolean devolveLivro(long codigo) {
        this.jdbcTemplate.update("UPDATE livros SET codigoUsuario = -1 WHERE id = ?", codigo);
        return true;
    }

    public List<Livro> getLivrosNaoEmprestados() {
        return this.jdbcTemplate.query(
            "SELECT * FROM livros WHERE codigoUsuario = -1", 
            (rs, rowNum) -> 
                new Livro(rs.getLong("id"),rs.getString("titulo"),rs.getString("autor"),rs.getInt("ano"),rs.getLong("codigoUsuario")));
    }

    public List<Livro> getLivrosEmprestados(long codigoUsuario) {
        String query = "SELECT * FROM livros WHERE codigoUsuario = ?";
        return jdbcTemplate.query(query, (PreparedStatementSetter) ps -> ps.setLong(1, codigoUsuario), (rs, rowNum) -> 
            new Livro(rs.getLong("id"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("ano"), rs.getLong("codigoUsuario")));
    }
}