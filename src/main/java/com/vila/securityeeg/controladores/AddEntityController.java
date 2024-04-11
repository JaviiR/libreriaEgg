package com.vila.securityeeg.controladores;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.servicios.AutorServicio;
import com.vila.securityeeg.servicios.EditorialServicio;
import com.vila.securityeeg.servicios.LibroServicio;
import com.vila.securityeeg.utileria.Utileria;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/add")
public class AddEntityController {

    @Value("${libreria.autor.imagenes}")
    private String rutaAutorImgs;
    @Value("${libreria.editorial.imagenes}")
    private String rutaEditorialImgs;
    @Value("${libreria.libro.imagenes}")
    private String rutaLibroImgs;

    @Autowired
    private AutorServicio autorService;
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private EditorialServicio editorialService;

    @GetMapping("/autor")
    public String formAddAutor(HttpSession session, Model modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/autor";
        } else {
            return "/addEntitys/addAutor";
        }
    }

    @PostMapping("/autor")
    public String addAutor(@RequestParam String nombre, @RequestParam("archivoImagen") MultipartFile multiPart,
            RedirectAttributes redirect, Model modelo, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/autor";
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            String nombre_imagen = "default.jpg";
            try {
                if (!multiPart.isEmpty()) {
                    // String ruta="d://escuela/img-alumnos/";
                    String nombreImagen = Utileria.guardarArchivo(multiPart, rutaAutorImgs);
                    if (nombreImagen != null) {
                        nombre_imagen = nombreImagen;
                    }
                }
                autorService.nuevoAutor(nombre, nombre_imagen);
                System.out.println("autor creado");
                return "redirect:/listar/autorAdmin";
            } catch (Exception e) {
                redirect.addFlashAttribute("nombre", nombre);
                redirect.addFlashAttribute("error", e.getMessage());
                return "redirect:/add/autor";
            }
        }
    }

    @GetMapping("/editorial")
    public String addEditorial(Model modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/editorial";
        } else {
            return "/addEntitys/addEditorial";
        }
    }

    @PostMapping("/editorial")
    public String addEditorial(@RequestParam String nombre, @RequestParam("archivoImagen") MultipartFile multipart,
            RedirectAttributes redirect, Model modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/editorial";
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            String nombre_imagen = "default.jpg";
            try {
                if (!multipart.isEmpty()) {
                    String nombreImg = Utileria.guardarArchivo(multipart, rutaEditorialImgs);
                    if (nombreImg != null) {
                        nombre_imagen = nombreImg;
                    }
                }
                editorialService.nuevaEditorial(nombre, nombre_imagen);
                System.out.println("editorial creado");
                return "redirect:/listar/editorialAdmin";
            } catch (Exception e) {
                redirect.addFlashAttribute("nombre", nombre);
                redirect.addFlashAttribute("error", e.getMessage());
                return "redirect:/add/editorial";
            }
        }
    }

    @GetMapping("/libro")
    public String addLibro(Model modelo, HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/libro";
        } else {
            return "/addEntitys/addLibro";
        }
    }

    @PostMapping("/libro")
    public String addLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam Integer ejemplar, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date alta,
            @RequestParam String autorId, @RequestParam String editorialId,
            @RequestParam("archivoImagen") MultipartFile multipart, RedirectAttributes redirect, Model modelo,
            HttpSession session) throws Exception {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        if (logueado == null) {
            return "redirect:/login";
        } else if (logueado.getRol().toString().equals("USER")) {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            return "redirect:/listar/libro";
        } else {
            modelo.addAttribute("nombreUsuario", logueado.getNombre());
            String nombre_imagen = "default.jpg";
            try {
                if (!multipart.isEmpty()) {
                    String nombreImg = Utileria.guardarArchivo(multipart, rutaLibroImgs);
                    if (nombreImg != null) {
                        nombre_imagen = nombreImg;
                    }
                }
                libroServicio.crearLibro(isbn, titulo, ejemplar, alta, autorId, editorialId, nombre_imagen);
                System.out.println("libro creado");
                return "redirect:/listar/libroAdmin";
            } catch (Exception e) {
                String Nombreautor = autorService.buscarAutorPorId(autorId).getNombre();
                String NombreEditorial = editorialService.buscarEditorialPorId(editorialId).getNombre();
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
                redirect.addFlashAttribute("error", e.getMessage());
                return "redirect:/add/libro";
            }
        }
    }

}
