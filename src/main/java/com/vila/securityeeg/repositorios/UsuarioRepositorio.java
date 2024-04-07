package com.vila.securityeeg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vila.securityeeg.entitys.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

    public Usuario findUsuarioByEmail(String email);

    /*@Query("SELECT u FROM Usuario u WHERE u.email= : email")
    public Usuario buscarPorEmail(@Param("email")String email);*/
}
