package com.vila.securityeeg.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vila.securityeeg.entitys.Editorial;
@Repository
public interface EditorialRepository extends JpaRepository<Editorial,String>{
    

    public List<Editorial> findAllByNombre(String nombre) throws Exception;
    public Editorial findLibroById(String id)throws Exception;
}
