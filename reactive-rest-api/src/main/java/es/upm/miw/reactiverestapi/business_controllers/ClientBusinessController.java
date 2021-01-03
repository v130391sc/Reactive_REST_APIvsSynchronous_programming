package es.upm.miw.reactiverestapi.business_controllers;

import es.upm.miw.reactiverestapi.documents.Client;
import es.upm.miw.reactiverestapi.dtos.ClientBasicDto;
import es.upm.miw.reactiverestapi.dtos.ClientCreationDto;
import es.upm.miw.reactiverestapi.exceptions.NotFoundException;
import es.upm.miw.reactiverestapi.repositories.ClientReactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ClientBusinessController {

    private ClientReactRepository clientReactRepository;

    @Autowired
    public ClientBusinessController(ClientReactRepository clientReactRepository) {
        this.clientReactRepository = clientReactRepository;
    }

    public Mono<ClientBasicDto> create(ClientCreationDto clientCreationDto) {
        return this.clientReactRepository.save(new Client(clientCreationDto.getFullName(), clientCreationDto.getStrongLevel(), clientCreationDto.getBirthDay())).map(ClientBasicDto::new);
    }

    public Mono<Void> delete(String id) {
        Mono<Client> client = this.find(id);
        return Mono.when(client).then(this.clientReactRepository.deleteById(id));
    }

    private Mono<Client> find(String id) {
        return this.clientReactRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException("Client id: " + id)));
    }

    public Flux<ClientBasicDto> readAll(){
        return this.clientReactRepository.findAll().map(ClientBasicDto::new);
    }
}
