package com.vila.securityeeg.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.enumeraciones.Rol;
import com.vila.securityeeg.repositorios.UsuarioRepositorio;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServicios implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(String nombreImg,String nombre,String email,String password,String password2) throws Exception{
        validar(nombre,email,password,password2);
        Usuario usuario=new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setImagen(nombreImg);
        usuarioRepositorio.save(usuario);
    }


    @Transactional
    public void actualizar(String nombreImg,String idUsuario,String nombre,String email,Rol rol,String password,String password2) throws Exception{
        validar(nombre,email,password,password2);
        Optional<Usuario> respuesta=usuarioRepositorio.findById(idUsuario);
        if(respuesta.isPresent()){
            Usuario usuario=respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setRol(rol);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setImagen(nombreImg);
            usuarioRepositorio.save(usuario);
        }else{
            System.out.println("repsuesta esta vacia");
        }
        
    }


    private void validar(String nombre,String email,String password,String password2)throws Exception{
        if(nombre.isEmpty() || nombre==null){
            throw new Exception("el nombre no puede ser nulo o estar vacio");
        }
        if(email.isEmpty() || email==null){
            throw new Exception("el email no puede ser nulo o estar vacio");
        }
        if(password.isEmpty() || password==null ||password.length()<=3){
            throw new Exception("el password no puede ser nulo, estar vacio o tener menos de 4 digitos");
        }
        if(!password.equals(password2)){
            throw new Exception("las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario=usuarioRepositorio.findUsuarioByEmail(email);
        if(usuario!=null){
            List<GrantedAuthority> permisos=new ArrayList<>();
            GrantedAuthority p=new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion=attr.getRequest().getSession(true);
            sesion.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }else{
            return null;
        }
    }

    public Usuario usuarioById(String id){
        Usuario usuario=usuarioRepositorio.findById(id).get();
        return usuario;
    }


    public List<Usuario> findAllUsers(){
        return usuarioRepositorio.findAll();
    }
}
