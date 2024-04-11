package com.vila.securityeeg.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vila.securityeeg.entitys.Editorial;
@Repository
public interface EditorialRepository extends JpaRepository<Editorial,String>{
    

    public List<Editorial> findAllByNombre(String nombre) throws Exception;
    public Editorial findLibroById(String id)throws Exception;
    @Query(value = "select ceiling(count(*)/ :cantDatos) from editorial",nativeQuery = true)
   public int totalPaginas(@Param("cantDatos") double cantDatos);

   @Query(value = "SELECT * FROM Editorial ORDER BY nombre OFFSET :datosPasados ROWS FETCH NEXT 5 ROWS ONLY;",nativeQuery = true)
   public List<Editorial> EditorialesDe5en5(@Param("datosPasados") int datosPasados);
}
