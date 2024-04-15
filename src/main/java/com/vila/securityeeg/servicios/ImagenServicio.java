package com.vila.securityeeg.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import com.vila.securityeeg.entitys.Imagen;
import com.vila.securityeeg.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepo;
    //cambiarlo por una funcion que guarde la imagen en una carpeta y no en la base de datos
    public Imagen guardarImagen(MultipartFile archivo) throws Exception{
        if(archivo!=null){
            try {
                Imagen imagen=new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imagenRepo.save(imagen);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return null;
    }
    
    public Imagen actualizar(MultipartFile archivo, String idImagen)throws Exception{
        if(archivo!=null){
            try {
                Imagen imagen=new Imagen();
                if(idImagen!=null){
                    Optional<Imagen> respuesta=imagenRepo.findById(idImagen);
                    if(respuesta.isPresent()){
                        imagen=respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getOriginalFilename());
                imagen.setContenido(archivo.getBytes());
                return imagenRepo.save(imagen);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return null;
    }
}
