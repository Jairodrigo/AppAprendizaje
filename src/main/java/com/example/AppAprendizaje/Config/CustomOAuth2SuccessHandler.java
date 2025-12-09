package com.example.AppAprendizaje.Config;

import com.example.AppAprendizaje.Model.Nacionalidad;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Repository.NacionalidadRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final NacionalidadRepository nacionalidadRepository;

    public CustomOAuth2SuccessHandler(UsuarioRepository usuarioRepository,
                                      NacionalidadRepository nacionalidadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.nacionalidadRepository = nacionalidadRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        //Buscar usuario en BD
        Usuario usuario = usuarioRepository.findByCorreo(email).orElseGet(() -> {
            Usuario nuevo = new Usuario();
            nuevo.setCorreo(email);
            nuevo.setNombre(oAuth2User.getAttribute("given_name"));
            nuevo.setApellidoPaterno(oAuth2User.getAttribute("family_name"));
            nuevo.setFechaRegistro(LocalDateTime.now());

            // Valores por defecto
            nuevo.setGenero(Usuario.Genero.O);
            Nacionalidad desconocida = nacionalidadRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Debe existir nacionalidad 'Desconocida' en la BD"));
            nuevo.setNacionalidad(desconocida);

            return usuarioRepository.save(nuevo);
        });

        //Decidir redirección
        if (usuario.getGenero() == Usuario.Genero.O ||
                "Desconocida".equals(usuario.getNacionalidad().getPais())) {
            response.sendRedirect("https://app-aprendizaje-front.vercel.app/cuestionario?usuarioId=" + usuario.getIdUsuario());
        } else {
            response.sendRedirect("https://app-aprendizaje-front.vercel.app/inicio?usuarioId=" + usuario.getIdUsuario());
        }

    }
}
