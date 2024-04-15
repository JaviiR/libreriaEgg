package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vila.securityeeg.servicios.UsuarioServicios;
import com.vila.securityeeg.utileria.Utileria;

@Controller
@RequestMapping("/registrar")
public class RegistroController {
    @Autowired
    private UsuarioServicios usuarioServicios;
    @Value("${libreria.usuario.imagenes}")
    private String rutaUsuarioImgs;

    @GetMapping()
    public String registrar() {
        return "/registersAndLogins/registro";
    }
    

    @PostMapping()
    public String registrar(@RequestParam MultipartFile archivo,@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, RedirectAttributes redirect) {
        try {
            String nombreImg="default.jpg";
            if(!archivo.isEmpty()){
                String nombre_imagen=Utileria.guardarArchivo(archivo, rutaUsuarioImgs);
                if(nombre_imagen!=null){
                    nombreImg=nombre_imagen;
                }
                
            }
            usuarioServicios.registrar(nombreImg,nombre, email, password, password2);
            System.out.println( "Usuario registrado Correctamente");
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirect.addFlashAttribute("error", e.getMessage());
            redirect.addFlashAttribute("nombre", nombre);
            redirect.addFlashAttribute("email", email);
            return "redirect:/registrar";
        }
            }
}
