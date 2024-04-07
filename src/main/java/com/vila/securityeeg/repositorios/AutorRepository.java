package com.vila.securityeeg.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vila.securityeeg.entitys.Autor;
@Repository
public interface AutorRepository extends JpaRepository<Autor,String>{
    
    public List<Autor> findAllByNombre(String nombre) throws Exception;
    public Autor findAutorById(String id)throws Exception;
}
