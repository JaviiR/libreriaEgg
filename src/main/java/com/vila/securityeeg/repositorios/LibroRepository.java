package com.vila.securityeeg.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>{
    

    public Libro findLibroByTitulo(String titulo);
    public List<Libro> findLibroByAutor(Autor autor);

   @Query(value = "select ceiling(count(*)/ :cantDatos) from libro",nativeQuery = true)
   public int totalPaginas(@Param("cantDatos") double cantDatos);

   @Query(value = "SELECT * FROM Libro ORDER BY titulo OFFSET :datosPasados ROWS FETCH NEXT 5 ROWS ONLY;",nativeQuery = true)
   public List<Libro> librosDe5en5(@Param("datosPasados") int datosPasados);

    /*@Query("SELECT l from Libro l WHERE l.titulo = :titulo")
    public Libro  findLibroByTitulo(@Param("titulo") String titulo);*/
    /*@Query("SELECT l from Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> findLibroByAutor(@Param("nombre") String nombre);*/
}
