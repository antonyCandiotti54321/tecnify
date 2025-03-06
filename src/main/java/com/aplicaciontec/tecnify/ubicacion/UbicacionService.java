package com.aplicaciontec.tecnify.ubicacion;
import com.aplicaciontec.tecnify.user.Role;
import com.aplicaciontec.tecnify.user.UpdateTecnicoDTO;
import com.aplicaciontec.tecnify.user.User;
import com.aplicaciontec.tecnify.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UbicacionService {

    @Autowired
    private UserRepository userRepository;

    public void actualizarUbicacion(String correo, Double latitud, Double longitud) {
        // Buscar el usuario por correo
        User usuario = userRepository.findByUsername(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar la ubicación
        usuario.setLatitud(latitud);
        usuario.setLongitud(longitud);

        // Guardar el usuario actualizado
        userRepository.save(usuario);
    }

    public List<UbicacionClienteDTO> obtenerUbicacionesClientes() {
        // Obtener todos los usuarios con rol CLIENTE
        List<User> clientes = userRepository.findByRole(Role.CLIENTE);

        // Convertir a DTO
        return clientes.stream()
                .map(cliente -> new UbicacionClienteDTO(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getLatitud(),
                        cliente.getLongitud()
                ))
                .toList();
    }

    public List<UbicacionTecnicoDTO> obtenerUbicacionesTecnicos() {
        // Obtener todos los usuarios con rol TECNICO
        List<User> tecnicos = userRepository.findByRole(Role.TECNICO);

        // Convertir a DTO
        return tecnicos.stream()
                .map(tecnico -> new UbicacionTecnicoDTO(
                        tecnico.getId(),
                        tecnico.getNombre(),
                        tecnico.getProfesion(),
                        tecnico.getDescripcion(),
                        tecnico.getPrecio(),
                        tecnico.getLatitud(),
                        tecnico.getLongitud()
                ))
                .toList();
    }

    public void actualizarTecnico(UpdateTecnicoDTO updateTecnicoDTO) {
        // Obtener el correo desde el contexto de seguridad
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String correo = userDetails.getUsername(); // El correo del usuario autenticado

        // Buscar al usuario por correo (técnico)
        User tecnico = userRepository.findByUsername(correo)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado"));

        // Actualizar los campos solo si no son nulos
        if (updateTecnicoDTO.getProfesion() != null) {
            tecnico.setProfesion(updateTecnicoDTO.getProfesion());
        }
        if (updateTecnicoDTO.getDescripcion() != null) {
            tecnico.setDescripcion(updateTecnicoDTO.getDescripcion());
        }
        if (updateTecnicoDTO.getPrecio() != null) {
            tecnico.setPrecio(updateTecnicoDTO.getPrecio());
        }

        // Guardar los cambios
        userRepository.save(tecnico);
    }
}
