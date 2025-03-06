package com.aplicaciontec.tecnify.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nombre;

    public UserDTO(User user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
    }
}
