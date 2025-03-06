package com.aplicaciontec.tecnify.mensaje;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.aplicaciontec.tecnify.user.User;
import com.aplicaciontec.tecnify.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MensajeService {
    private final MensajeRepository mensajeRepository;
    private final UserRepository userRepository;



    public Mensaje saveMensaje(MensajeRequest request)
    {
        User remitente = userRepository.findById(request.getIdRemitente())
                .orElseThrow(() -> new RuntimeException("Remitente no encontrado"));
        User receptor = userRepository.findById(request.getIdReceptor())
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));


        Mensaje mensaje = Mensaje.builder()
                .idRemitente(remitente)
                .idReceptor(receptor)
                .mensaje(request.getMensaje())
                .build();
        return mensajeRepository.save(mensaje);
    }

    public List<MensajeResponse> obtenerMensajesPorReceptor() {
        // Obtener el correo desde el contexto de seguridad
        String correo = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Buscar el usuario por correo para obtener el ID
        Long idReceptor = userRepository.findByUsername(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getId();

        // Buscar los mensajes para el receptor
        List<Mensaje> mensajes = mensajeRepository.findByIdReceptor_Id(idReceptor);

        // Convertir los mensajes a la respuesta DTO antes de eliminarlos
        List<MensajeResponse> mensajesResponse = mensajes.stream()
                .map(MensajeResponse::new)
                .toList();

        // Eliminar solo los mensajes enviados al receptor (se eliminan después de ser procesados)
        mensajes.forEach(mensaje -> {
            if (mensaje.getIdReceptor().getId().equals(idReceptor)) {
                mensajeRepository.delete(mensaje);  // Eliminar mensaje que corresponde al receptor
            }
        });

        // Retornar la lista de mensajes que fueron enviados al receptor
        return mensajesResponse;
    }

}
