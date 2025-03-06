package com.aplicaciontec.tecnify.mensaje;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensajeRequest {
    Long idRemitente;
    Long idReceptor;
    String mensaje;
}
