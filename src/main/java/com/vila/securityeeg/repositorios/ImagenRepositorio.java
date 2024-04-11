package com.vila.securityeeg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vila.securityeeg.entitys.Imagen;

public interface ImagenRepositorio extends JpaRepository<Imagen,String> {
    
}
