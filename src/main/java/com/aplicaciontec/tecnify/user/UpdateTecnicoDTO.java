package com.aplicaciontec.tecnify.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTecnicoDTO {
    private String profesion;
    private String descripcion;
    private Double precio;
}
