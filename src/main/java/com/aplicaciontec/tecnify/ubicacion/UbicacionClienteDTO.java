package com.aplicaciontec.tecnify.ubicacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionClienteDTO {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
}
