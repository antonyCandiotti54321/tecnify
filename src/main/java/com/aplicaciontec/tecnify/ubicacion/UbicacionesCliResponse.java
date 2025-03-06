package com.aplicaciontec.tecnify.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionesCliResponse {
    Long id;
    String nombre;
    String latitud;
    String longitud;
}
