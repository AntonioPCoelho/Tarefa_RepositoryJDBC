package com.coelho.library;

import java.util.List;

public interface IUsuarioRepository {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(long id);
    boolean cadastraUsuario(Usuario usuario);
    boolean removeUsuario(long codigo);
}