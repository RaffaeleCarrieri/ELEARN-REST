package it.elearn.rest.utente.model;

import it.elearn.rest.utente.model.RuoloEnum;
import it.elearn.rest.utente.model.dto.UtenteRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "utente", schema = "portfolio_ebook")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UtenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 20, nullable = false)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "cognome", length = 100, nullable = false)
    private String cognome;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo", length = 20, nullable = false)
    private RuoloEnum ruolo;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    public UtenteModel(){
        
    }
    
    public UtenteModel(UUID id, String nome, String cognome, String email, String password, RuoloEnum ruolo, LocalDate dataNascita) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.dataNascita = dataNascita;
    }
}