package com.vila.securityeeg.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.vila.securityeeg.servicios.UsuarioServicios;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SeguridadWeb {

    @Autowired
    public UsuarioServicios usuarioServicios;


    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicios)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    
    
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth->auth
        .requestMatchers("/listar/**","/principal/**","/admin/**","/add/**","/editar/**","/eliminar/**").authenticated()
        .anyRequest().permitAll())
        .formLogin(form->form.loginPage("/login").loginProcessingUrl("/logincheck")
        .usernameParameter("email").passwordParameter("password")
        .defaultSuccessUrl("/principal")
        .permitAll())
        .logout(logout->logout.logoutUrl("/logout")
        .logoutSuccessUrl("/").permitAll()
        )
        .build();
    }
    


    
}
