package it.elearn.rest.utente.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtentiListResponseDto {
    private List<UtenteResponseDto> utenti;
}
