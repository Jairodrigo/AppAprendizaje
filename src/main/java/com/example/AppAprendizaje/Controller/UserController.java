package com.example.AppAprendizaje.Controller;

import com.example.AppAprendizaje.Dto.UsuarioCompletarDto;
import com.example.AppAprendizaje.Model.Nacionalidad;
import com.example.AppAprendizaje.Model.Usuario;
import com.example.AppAprendizaje.Repository.NacionalidadRepository;
import com.example.AppAprendizaje.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    //inyectamos dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    // Obtener usuario(OAuth2User) o crearlo si no existe
    @GetMapping
    public Usuario obtenerUsuario(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return null;

        String email = principal.getAttribute("email");

        //verificamos si el usuario existe, sino se crea uno nuevo
        return usuarioRepository.findByCorreo(email).orElseGet(() -> {
            Usuario nuevo = new Usuario();
            nuevo.setCorreo(email);
            nuevo.setNombre(principal.getAttribute("given_name"));
            nuevo.setApellidoPaterno(principal.getAttribute("family_name"));
            nuevo.setFechaRegistro(LocalDateTime.now());

            // Valores por defecto
            nuevo.setGenero(Usuario.Genero.O);

            //Valor por default
            Nacionalidad desconocida = nacionalidadRepository.findByPais("Desconocida")
                    .orElseGet(() -> {
                        Nacionalidad n = new Nacionalidad();
                        n.setPais("Desconocida");
                        return nacionalidadRepository.save(n);
                    });
            nuevo.setNacionalidad(desconocida);

            //se guarda el nuevo usuario
            return usuarioRepository.save(nuevo);
        });
    }

    // Completar datos de usuario
    @PostMapping("/completar")
    public ResponseEntity<?> completarUsuario(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody UsuarioCompletarDto datos) {

        //verificamos que el usuario se haya autenticado
        if (principal == null) {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }

        //Buscamos al usuario en la bd por el email
        String email = principal.getAttribute("email");
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar género
        try {
            Usuario.Genero genero = Usuario.Genero.valueOf(datos.getGenero().trim().toUpperCase());
            usuario.setGenero(genero);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Género inválido");
        }

        // Validar nacionalidad
        Optional<Nacionalidad> nacionalidadOpt = nacionalidadRepository.findById(datos.getNacionalidad());
        if (nacionalidadOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Nacionalidad no encontrada");
        }
        usuario.setNacionalidad(nacionalidadOpt.get());

        // Guardar cambios
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/actual")
    public ResponseEntity<?> getUsuarioActual(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        String email = principal.getAttribute("email");
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(email);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

}
