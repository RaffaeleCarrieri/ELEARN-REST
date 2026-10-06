package it.elearn.rest.utente.model.dto;

import it.elearn.rest.utente.model.RuoloEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteRequestDto {
    private String id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private RuoloEnum ruolo;
    private LocalDate dataNascita;
}
