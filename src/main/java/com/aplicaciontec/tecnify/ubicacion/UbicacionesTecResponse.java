package com.aplicaciontec.tecnify.ubicacion;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionesTecResponse {
    Long id;
    String nombre;
    String profesion;
    String descripcion;
    Double precio;
    Double latitud;
    Double longitud;
}
