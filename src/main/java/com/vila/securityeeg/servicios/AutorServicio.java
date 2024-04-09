package com.vila.securityeeg.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Libro;
import com.vila.securityeeg.repositorios.AutorRepository;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void nuevoAutor(String nombreAutor,String img)throws Exception{
        try {
            if(nombreAutor.length()<4){
            throw new Exception("Nombre inválido debe tener entre 4 o mas digitos");
            }
            else{
                Autor autor=new Autor();
                autor.setNombre(nombreAutor);
                autor.setImg(img);
                autorRepository.save(autor);   
            }
        } catch (Exception e) {
            throw new Exception("Error al crear el autor debido a: "+e.getMessage());
        }
        
    }

    @Transactional
    public void editarAutor(String idAutor,String nombreAutor,String nombreImg)throws Exception{
        try {
            if(nombreAutor.length()<4){
            throw new Exception("Nombre inválido debe tener entre 4 o mas digitos");
            }
            else{
                Autor autor=new Autor();
                autor.setId(idAutor);
                autor.setNombre(nombreAutor);
                autor.setImg(nombreImg);
                autorRepository.save(autor);
            }
        } catch (Exception e) {
            throw new Exception("Error al editar el autor debido a: "+e.getMessage());
        }
    }

    @Transactional
    public void eliminarAutor(String autorId)throws Exception{
        try {
            autorRepository.deleteById(autorId);    
        } catch (Exception e) {
            throw new Exception("Error al eliminar el autor debido a: "+e.getMessage());
        }
        
    }

    public List<Autor> listaAutores(){
        return autorRepository.findAll();
    }

    public Autor buscarAutorPorId(String autorId) throws Exception{
        Autor autor=autorRepository.findAutorById(autorId);
        if(autor==null){
            throw new Exception("Autor no existe");
        }else{
            return autor;
        }
        
    }

    public List<Autor> buscarAutoresPorNombre(String nombreAutor) throws Exception{
        return autorRepository.findAllByNombre(nombreAutor);
    }
    public int totalPaginasde5en5(){
        return autorRepository.totalPaginas(5.0);
    }

    public List<Autor> listar5en5(int fila){
        int datosPasados=0;
        if(fila>1){
            datosPasados=(fila-1)*5;
        }
        return autorRepository.AutoresDe5en5(datosPasados);
    }
}
