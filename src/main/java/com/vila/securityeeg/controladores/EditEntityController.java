package com.vila.securityeeg.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vila.securityeeg.entitys.Autor;
import com.vila.securityeeg.entitys.Editorial;
import com.vila.securityeeg.entitys.Libro;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.enumeraciones.Rol;
import com.vila.securityeeg.servicios.AutorServicio;
import com.vila.securityeeg.servicios.EditorialServicio;
import com.vila.securityeeg.servicios.LibroServicio;
import com.vila.securityeeg.servicios.UsuarioServicios;
import com.vila.securityeeg.utileria.Utileria;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/editar")
public class EditEntityController {
    @Autowired
    private UsuarioServicios usuarioServicio;
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    @Value("${libreria.autor.imagenes}")
    private String rutaAutorImgs;
    @Value("${libreria.editorial.imagenes}")
    private String rutaEditorialImgs;
    @Value("${libreria.libro.imagenes}")
    private String rutaLibroImgs;

    @GetMapping("/libro")
    public String editarLibro(Model modelo, @RequestParam(required = false) Long libroId, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            if (libroId != null) {
                Libro libro = libroServicio.BuscarLibroPorId(libroId);
                modelo.addAttribute("isbn", libro.getIsbn());
                modelo.addAttribute("titulo", libro.getTitulo());
                modelo.addAttribute("ejemplar", libro.getEjemplar());
                modelo.addAttribute("alta", libro.getAlta());
                modelo.addAttribute("autor", libro.getAutor().getNombre());
                modelo.addAttribute("editorial", libro.getEditorial().getNombre());
                modelo.addAttribute("autorId", libro.getAutor().getId());
                modelo.addAttribute("editorialId", libro.getEditorial().getId());
                modelo.addAttribute("img", libro.getImg());
            }
            return "/editEntitys/editLibro";

        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/libro";
        }
    }

    @GetMapping("/autor")
    public String editarAutor(Model modelo, @RequestParam(required = false) String autorId, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            if (autorId != null) {
                try {
                    Autor autor = autorServicio.buscarAutorPorId(autorId);
                    modelo.addAttribute("nombre", autor.getNombre());
                    modelo.addAttribute("img", autor.getImg());
                    modelo.addAttribute("autorId", autor.getId());
                } catch (Exception e) {
                    System.out.println("Error: metodo editarAutorGet");
                }

            }
            return "/editEntitys/editAutor";
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/autor";
        }
    }

    @GetMapping("/editorial")
    public String editarEditorial(Model modelo, @RequestParam(required = false) String editorialId,
            HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            if (editorialId != null) {
                try {
                    Editorial editorial = editorialServicio.buscarEditorialPorId(editorialId);
                    modelo.addAttribute("editorialId", editorial.getId());
                    modelo.addAttribute("nombre", editorial.getNombre());
                    modelo.addAttribute("img", editorial.getLogo());

                } catch (Exception e) {
                    System.out.println("Error: metodo editarEditorialGet");
                }

            }
            return "/editEntitys/editEditorial";
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/editorial";
        }
    }

    @PostMapping("/libro")
    public String editLibro(RedirectAttributes redirect, @RequestParam Long isbn, @RequestParam String titulo,
            @RequestParam Integer ejemplar, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date alta,
            @RequestParam String autorId, @RequestParam String editorialId,
            @RequestParam("archivoImagen") MultipartFile multipart, HttpSession session, Model modelo)
            throws Exception {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            String nombre_img = "default.jpg";
            try {
                if (!multipart.isEmpty()) {
                    String nombreImg = Utileria.guardarArchivo(multipart, rutaLibroImgs);
                    if (nombreImg != null) {
                        nombre_img = nombreImg;
                    }
                }

                libroServicio.EditarLibro(isbn, titulo, ejemplar, alta, autorId, editorialId, nombre_img);
                System.out.println("libro editado");
                return "redirect:/listar/libroAdmin";
            } catch (Exception e) {
                System.out.println("id autor: " + autorId);
                System.out.println("id editorial: " + editorialId);
                String Nombreautor = libroServicio.BuscarLibroPorId(isbn).getAutor().getNombre();
                String NombreEditorial = libroServicio.BuscarLibroPorId(isbn).getEditorial().getNombre();
                redirect.addFlashAttribute("error", e.getMessage());
                redirect.addFlashAttribute("isbn", isbn);
                redirect.addFlashAttribute("titulo", titulo);
                redirect.addFlashAttribute("ejemplar", ejemplar);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                // Analizamos la cadena de fecha en un objeto LocalDateTime
                LocalDateTime fecha = LocalDateTime.parse(alta.toString(), formatter);
                // Creamos un formateador para el nuevo formato deseado (solo fecha)
                DateTimeFormatter nuevoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // Formateamos la fecha según el nuevo formato
                String fechaFormateada = fecha.format(nuevoFormatter);
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                redirect.addFlashAttribute("alta", formatter2.parse(fechaFormateada));
                redirect.addFlashAttribute("autor", Nombreautor);
                redirect.addFlashAttribute("editorial", NombreEditorial);
                redirect.addFlashAttribute("autorId", autorId);
                redirect.addFlashAttribute("editorialId", editorialId);
                return "redirect:/editar/libro";
            }
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/libro";
        }
    }

    @PostMapping("/autor")
    public String editarAutor(RedirectAttributes redirect, @RequestParam String autorId, @RequestParam String nombre,
            @RequestParam("archivoImagen") MultipartFile multipart, HttpSession session, Model modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            String nombre_img = "default.jpg";
            try {
                if (!multipart.isEmpty()) {
                    String nombreImg = Utileria.guardarArchivo(multipart, rutaAutorImgs);
                    if (nombreImg != null) {
                        nombre_img = nombreImg;
                    }
                }
                autorServicio.editarAutor(autorId, nombre, nombre_img);
                return "redirect:/listar/autorAdmin";
            } catch (Exception e) {
                return "redirect:/editar/autor";
            }
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/autor";
        }
    }

    @PostMapping("/editorial")
    public String editarEditorial(RedirectAttributes redirect, @RequestParam String nombre,
            @RequestParam("archivoImagen") MultipartFile multipart, @RequestParam String editorialId, Model modelo,
            HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("ADMIN")) {
            String nombre_img = "default.jpg";
            try {
                if (!multipart.isEmpty()) {
                    String nombreImg = Utileria.guardarArchivo(multipart, rutaEditorialImgs);
                    if (nombreImg != null) {
                        nombre_img = nombreImg;
                    }
                }
                editorialServicio.editarEditorial(editorialId, nombre, nombre_img);
                return "redirect:/listar/editorialAdmin";
            } catch (Exception e) {
                return "redirect:/editar/editorial";
            }
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/editorial";
        }

    }


    @GetMapping("/usuario")
    public String editarUsuario(@RequestParam(required = false) String usuarioId,Model modelo) {
        Usuario usuario=usuarioServicio.usuarioById(usuarioId);
        Map<Rol,String> listaRoles=new LinkedHashMap<>();
        listaRoles.put(Rol.ADMIN, "ADMIN");
        listaRoles.put(Rol.USER,"USER");
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("roles", listaRoles);
        return "editEntitys/editUser";
    }

    @PostMapping("/usuario")
    public String editarUsuario(@RequestParam MultipartFile archivo,@RequestParam String usuarioId,@RequestParam String nombre, @RequestParam String email,@RequestParam Rol rol, @RequestParam String password,
    @RequestParam String password2, RedirectAttributes redirect) {
        try {
            usuarioServicio.actualizar(archivo, usuarioId, nombre, email, rol, password, password2);
            redirect.addFlashAttribute("mensaje","Usuario actulizado correctamente.");
            return "redirect:/listar/usuario";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensaje",e.getMessage());
            
            return "redirect:/listar/usuario";
        }
        
        
    }
    
    

}
