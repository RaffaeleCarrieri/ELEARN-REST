package it.elearn.rest.utente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.elearn.generic.PatchRequestDto;
import it.elearn.rest.utente.model.dto.UtenteRequestDto;
import it.elearn.rest.utente.model.dto.UtenteResponseDto;
import it.elearn.rest.utente.model.dto.UtentiListResponseDto;
import it.elearn.rest.utente.service.UtenteService;
import it.elearn.rest.utente.service.impl.UtenteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
@RequiredArgsConstructor
@Tag(
        name = "Utenti",
        description = "API per la gestione degli utenti"
)
public class UtenteController {

    private final UtenteServiceImpl utenteService;

    @Operation(
            summary = "Recupera un utente",
            description = "Recupera un utente tramite il suo identificativo"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utente trovato",
                    content = @Content(
                            schema = @Schema(implementation = UtenteResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utente non trovato"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UtenteResponseDto> findUserById(
            @Parameter(
                    description = "ID dell'utente",
                    required = true
            )
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                utenteService.findUserById(id)
        );
    }


    @Operation(
            summary = "Recupera tutti gli utenti",
            description = "Restituisce la lista completa degli utenti"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista utenti recuperata correttamente"
    )
    @GetMapping
    public ResponseEntity<UtentiListResponseDto> findAllUsers() {

        return ResponseEntity.ok(
                utenteService.findAllUsers()
        );
    }


    @Operation(
            summary = "Crea un nuovo utente",
            description = "Crea un nuovo utente nel sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Utente creato correttamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dati della richiesta non validi"
            )
    })
    @PostMapping
    public ResponseEntity<UtenteResponseDto> createUser(
            @RequestBody UtenteRequestDto request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(utenteService.createUser(request));
    }


    @Operation(
            summary = "Aggiorna completamente un utente",
            description = "Sostituisce i dati dell'utente con quelli forniti nella richiesta"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utente aggiornato correttamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dati della richiesta non validi"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utente non trovato"
            )
    })
    @PutMapping
    public ResponseEntity<UtenteResponseDto> updateUser(
            @RequestBody UtenteRequestDto request
    ) {

        return ResponseEntity.ok(
                utenteService.updateUser(request)
        );
    }


    @Operation(
            summary = "Modifica parzialmente un utente",
            description = """
                    Modifica solamente i campi specificati nella richiesta.
                    I campi non presenti nella richiesta rimangono invariati.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Utente aggiornato correttamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Richiesta non valida"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utente non trovato"
            )
    })
    @PatchMapping
    public ResponseEntity<UtenteResponseDto> patchUser(
            @RequestBody PatchRequestDto request
    ) {

        return ResponseEntity.ok(
                utenteService.updateUser(request)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") String id){
        utenteService.deleteUser(UUID.fromString(id));
    }
}