package com.aplicaciontec.tecnify.mensaje;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByIdRemitente_IdAndIdReceptor_Id(Long idRemitente, Long idReceptor);
    List<Mensaje> findByIdReceptor_Id(Long idReceptor);
}
