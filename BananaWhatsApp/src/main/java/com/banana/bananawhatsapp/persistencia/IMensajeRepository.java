package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.mysql.cj.protocol.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface IMensajeRepository extends JpaRepository<Mensaje,Integer>{

    List<Mensaje> findAllByRemitenteIdAndDestinatarioId(Usuario remitente, Usuario destinatario);


}
