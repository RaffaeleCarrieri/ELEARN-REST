package it.elearn.rest.utente.service;

import it.elearn.generic.PatchRequestDto;
import it.elearn.rest.utente.model.dto.UtenteRequestDto;
import it.elearn.rest.utente.model.dto.UtenteResponseDto;
import it.elearn.rest.utente.model.dto.UtentiListResponseDto;

import java.util.UUID;

public interface UtenteService {
    UtenteResponseDto findUserById(UUID id);
    UtentiListResponseDto findAllUsers();
    UtenteResponseDto createUser(UtenteRequestDto req);
    UtenteResponseDto updateUser(UtenteRequestDto req);
    UtenteResponseDto updateUser(PatchRequestDto req);
    void deleteUser(UUID id);
}
