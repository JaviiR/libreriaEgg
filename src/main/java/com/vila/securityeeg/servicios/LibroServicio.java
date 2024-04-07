package com.vila.securityeeg.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Editorial;
import com.vila.securityeeg.entitys.Libro;
import com.vila.securityeeg.repositorios.LibroRepository;
import jakarta.transaction.Transactional;

import java.util.Date;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer Ejemplar, Date alta, String autorId, String editorialId,String nombreImagen)
            throws Exception{
                boolean validacion=true;
                validacion=isbn==null||titulo==null||Ejemplar==null||alta==null||autorId==null||editorialId==null;
        try {
            Autor autor=autorServicio.buscarAutorPorId(autorId);
            Editorial editorial=editorialServicio.buscarEditorialPorId(editorialId);
            if(autor==null){
                throw new Exception("Autor nulo");
            }else if(editorial==null){
                throw new Exception("Editorial nulo");
            }else if(validacion){
                
                throw   isbn==null ? new Exception("campo isbn obligatorio"):
                        titulo==null ?  new Exception("campo titulo obligatorio"):
                        new Exception("campos isbn,titulo,ejemplar,alta,autor,editorial obligatorios");
            }
            else{
                Libro libro = new Libro();
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setEjemplar(Ejemplar);
                libro.setAlta(alta);
                libro.setAutor(autor);
                libro.setEditorial(editorial);
                libro.setImg(nombreImagen);
                libroRepository.save(libro);    
            }
        }
         catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        
    }

    @Transactional
    public void EditarLibro(Long isbn, String titulo, Integer Ejemplar, Date alta, String autorId, String editorialId,String img)
            throws Exception {
        try {
            Autor autor=autorServicio.buscarAutorPorId(autorId);
            Editorial editorial=editorialServicio.buscarEditorialPorId(editorialId);
            if (autor == null) {
                throw new Exception("Autor no valido");
            } else if (editorial == null) {
                throw new Exception("Editorial no valida");
            } else {
                Libro libro = new Libro();
                libro.setIsbn(isbn);
                libro.setTitulo(titulo);
                libro.setEjemplar(Ejemplar);
                libro.setAlta(alta);
                libro.setAutor(autor);
                libro.setEditorial(editorial);
                libro.setImg(img);
                libroRepository.save(libro);
            }    
        } catch (Exception e) {
            throw new Exception("Error al editar el libro debido a: "+e.getMessage());
        }
        

    }

    public Libro BuscarLibroPorId(Long id) {
        return libroRepository.findById(id).get();
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }
    public void eliminarLibro(Long id){
        libroRepository.deleteById(id);
    }


    public int totalPaginasde5en5(){
        return libroRepository.totalPaginas(5.0);
    }

    public List<Libro> listar5en5(int fila){
        int datosPasados=0;
        if(fila>1){
            datosPasados=(fila-1)*5;
        }
        return libroRepository.librosDe5en5(datosPasados);
    }
}
