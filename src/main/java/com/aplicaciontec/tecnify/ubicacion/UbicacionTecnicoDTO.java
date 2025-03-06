package com.aplicaciontec.tecnify.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionTecnicoDTO {
    private Long id;
    private String nombre;
    private String profesion;
    private String descripcion;
    private Double precio;
    private Double latitud;
    private Double longitud;
}
