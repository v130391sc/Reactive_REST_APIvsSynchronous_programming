package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.business_controllers.ClientBusinessController;
import es.upm.miw.reactiverestapi.dtos.ClientBasicDto;
import es.upm.miw.reactiverestapi.dtos.ClientCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(ClientResource.CLIENTS)
public class ClientResource {

    public static final String CLIENTS = "/v0/clients";
    static final String ID_ID = "/{id}";

    private ClientBusinessController clientBusinessController;

    @Autowired
    public ClientResource(ClientBusinessController clientBusinessController) {
        this.clientBusinessController = clientBusinessController;
    }

    @PostMapping
    public Mono<ClientBasicDto> create(@RequestBody ClientCreationDto clientCreationDto) {
        clientCreationDto.validate();
        return this.clientBusinessController.create(clientCreationDto);
    }

    @DeleteMapping(value = ID_ID)
    public Mono<Void> delete(@PathVariable String id) {
        return this.clientBusinessController.delete(id);
    }

    @GetMapping
    public Flux<ClientBasicDto> readAll() {
        return this.clientBusinessController.readAll();
    }
}