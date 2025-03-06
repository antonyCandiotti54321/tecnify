package com.aplicaciontec.tecnify.mensaje;

import com.aplicaciontec.tecnify.user.User;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_remitente", nullable = false)
    private User idRemitente;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private User idReceptor;

    private String mensaje;
}
