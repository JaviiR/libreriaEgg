package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.servicios.UsuarioServicios;

@Controller
@RequestMapping("/imagen")
public class ImagenController {
    
    @Autowired
    UsuarioServicios usuarioServicios;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id){
        Usuario usuario=usuarioServicios.usuarioById(id);
        byte[] imagen=usuario.getImagen().getContenido();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(imagen,headers,HttpStatus.OK);
    }
}