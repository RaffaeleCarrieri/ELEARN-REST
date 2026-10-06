package it.elearn.rest.utente.service.impl;

import it.elearn.generic.PatchRequestDto;
import it.elearn.rest.utente.model.UtenteModel;
import it.elearn.rest.utente.model.dto.UtenteRequestDto;
import it.elearn.rest.utente.model.dto.UtenteResponseDto;
import it.elearn.rest.utente.model.dto.UtentiListResponseDto;
import it.elearn.rest.utente.repository.UtenteRepository;
import it.elearn.rest.utente.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    @Override
    public UtenteResponseDto findUserById(UUID id) {
        Optional<UtenteModel> user = findUserOptional(id);
        return user.map(this::toDto).orElseThrow();
    }

    @Override
    public UtentiListResponseDto findAllUsers() {
        List<UtenteModel> users = utenteRepository.findAll();
        return UtentiListResponseDto
                .builder()
                .utenti(users
                        .stream()
                        .map(this::toDto)
                        .toList()
                )
                .build();
    }

    @Override
    public UtenteResponseDto createUser(UtenteRequestDto req) {
        UtenteModel userSaved = this.utenteRepository.saveAndFlush(toModel(req));
        return toDto(userSaved);
    }

    @Override
    public UtenteResponseDto updateUser(UtenteRequestDto req) throws RuntimeException{
        //UtenteModel userSaved = this.utenteRepository;
        String userId = req.getId();
        if(userId == null || userId.isBlank()) throw new RuntimeException("l'id deve essere popolato");
        /*utente.setNome(req.getNome());
        utente.setCognome(req.getCognome());
        utente.setEmail(req.getEmail());
        utente.setPassword(req.getPassword());
        utente.setRuolo(req.getRuolo());
        utente.setDataNascita(req.getDataNascita());*/
        List<String> fieldName = Arrays.stream(req.getClass().getDeclaredFields()).map(
                Field::getName
        ).filter(
                f->!f.equalsIgnoreCase("id")
        ).toList();

        HashMap<String,Object> fieldsUpdated = new HashMap<>();
        fieldName.forEach(
                f->{/*
                    String getMethod = generateGetterOrSetterMethod(true, f);
                    try {
                        Field reqField = req.getClass().getField(f);
                        req.getClass().getMethod(getMethod,reqField.getType()).invoke(req);
                        callObjectMethod(true,f)
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }*/
                    try {
                        Object value = callObjectMethod(true, f, req, req.getClass());
                        if(value != null){
                            fieldsUpdated.put(f,value);
                        }
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
        );


        return this.updateUser(PatchRequestDto
                .builder()
                .id(userId)
                .fields(fieldsUpdated)
                .build());
    }

    @Override
    public UtenteResponseDto updateUser(PatchRequestDto req) throws RuntimeException{
        UtenteModel user = findUserOptional(UUID.fromString(req.getId())).orElseThrow();
        HashMap<String, Object> fields = req.getFields();

        if(fields.containsKey("id")) throw  new RuntimeException("ID not allowed into PATCH");
        Class<? extends UtenteModel> userModelCls = user.getClass();
        fields.forEach(
                (field,value) -> {
                    try {
                        /*
                        String setter = generateGetterOrSetterMethod(false,field);
                        Field f = userModelCls.getDeclaredField(field);
                        userModelCls.getMethod(setter,f.getType()).invoke(user,value);*/
                        callObjectMethod(false,field,user,user.getClass(), value);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        return toDto(utenteRepository.save(user));
    }

    @Override
    public void deleteUser(UUID id) {
        utenteRepository.deleteById(id);
    }

    private Optional<UtenteModel> findUserOptional(UUID id){
        return utenteRepository.findById(id);
    }

    private <T> Object callObjectMethod(boolean isGetter, String field, T obj, Class<?> cls, Object ... values) throws Throwable {

        String methodName = generateGetterOrSetterMethod(isGetter,field);
        //Class<? extends T> userModelCls = (Class<? extends T>) obj.getClass();
        Field f = cls.getDeclaredField(field);
        Method method = null;
        if(isGetter){
            method = cls.getMethod(methodName);
        }else{
            method = cls.getMethod(methodName,f.getType());
        }
        return method.invoke(obj,values);
    }

    private String generateGetterOrSetterMethod(boolean isGetter,String field) {
        String regexMethod = isGetter ? "get%s" : "set%s";
        return String.format(regexMethod,
                field.substring(0, 1).toUpperCase().concat(field.substring(1)));
    }

    private UtenteModel toModel(UtenteRequestDto req){
        return UtenteModel
                .builder()
                .nome(req.getNome())
                .cognome(req.getCognome())
                .email(req.getEmail())
                .password(req.getPassword())
                .dataNascita(req.getDataNascita())
                .ruolo(req.getRuolo())
                .build();
    }

    private UtenteResponseDto toDto(UtenteModel m){
        return UtenteResponseDto
                .builder()
                .id(m.getId().toString())
                .nome(m.getNome())
                .cognome(m.getCognome())
                .ruolo(m.getRuolo())
                .email(m.getEmail())
                .dataNascita(m.getDataNascita())
                .build();
    }
}
