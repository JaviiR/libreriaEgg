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

import java.util.List;
import java.util.ArrayList;
import com.vila.securityeeg.entitys.Usuario;
import com.vila.securityeeg.enumeraciones.Rol;
import com.vila.securityeeg.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicios implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombre,String email,String password,String password2) throws Exception{
        validar(nombre,email,password,password2);
        Usuario usuario=new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuarioRepositorio.save(usuario);
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
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }else{
            return null;
        }
    }
}
