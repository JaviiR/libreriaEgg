package com.vila.securityeeg.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vila.securityeeg.entitys.Autor;
@Repository
public interface AutorRepository extends JpaRepository<Autor,String>{
    
    public List<Autor> findAllByNombre(String nombre) throws Exception;
    public Autor findAutorById(String id)throws Exception;
    @Query(value = "select ceiling(count(*)/ :cantDatos) from autor",nativeQuery = true)
   public int totalPaginas(@Param("cantDatos") double cantDatos);

   @Query(value = "SELECT * FROM Autor ORDER BY nombre OFFSET :datosPasados ROWS FETCH NEXT 5 ROWS ONLY;",nativeQuery = true)
   public List<Autor> AutoresDe5en5(@Param("datosPasados") int datosPasados);
}
