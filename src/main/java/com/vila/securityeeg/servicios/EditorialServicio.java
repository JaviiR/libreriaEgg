package com.vila.securityeeg.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Editorial;
import com.vila.securityeeg.repositorios.EditorialRepository;

@Service
public class EditorialServicio {
    

    @Autowired
    private EditorialRepository editorialRepository;

    @Transactional
    public void nuevaEditorial(String nombreEditorial,String nombre_imagen) throws Exception{
        try {
            if(nombreEditorial.length()<2){
                throw new Exception("el nombre de la editorial debe tener al menos 2 digitos");
            }else{
            Editorial editorial=new Editorial();
            editorial.setNombre(nombreEditorial);
            editorial.setLogo(nombre_imagen);
            editorialRepository.save(editorial);
            }
        } catch (Exception e) {
            throw new Exception("Error al crear Editorial debido a: "+e.getMessage());
            
        }
        
    }

    @Transactional
    public void editarEditorial(String id,String nombre,String nombreImg)throws Exception{
        try {
            if(nombre.length()<2){
                throw new Exception("el nombre de la editorial debe tener al menos 2 digitos");
            }else{
                Editorial editorial=new Editorial();
                editorial.setId(id);
                editorial.setNombre(nombre);
                editorial.setLogo(nombreImg);
            editorialRepository.save(editorial);
            }
        } catch (Exception e) {
            throw new Exception("Error al editar Editorial debido a: "+e.getMessage());
            
        }
    }

    @Transactional
    public void eliminarEditorial(String editorialId)throws Exception{
        try {
            editorialRepository.deleteById(editorialId);
        } catch (Exception e) {
            throw new Exception("Error al eliminar editorial debido a:"+e.getMessage());
        }
        
    }

    public List<Editorial> listaEditoriales(){
        return editorialRepository.findAll();
    }

    public Editorial buscarEditorialPorId(String editorialId) throws Exception{
        Editorial editorial=editorialRepository.findLibroById(editorialId);
        if(editorial==null){
            throw new Exception("Editorial no existe");
        }else{
            return editorial;
        }
        
    }

    public List<Editorial> buscarEditorialesPorNombre(String nombreEditorial) throws Exception{
        return editorialRepository.findAllByNombre(nombreEditorial);
    }


    public int totalPaginasde5en5(){
        return editorialRepository.totalPaginas(5.0);
    }

    public List<Editorial> listar5en5(int fila){
        int datosPasados=0;
        if(fila>1){
            datosPasados=(fila-1)*5;
        }
        return editorialRepository.EditorialesDe5en5(datosPasados);
    }
}
