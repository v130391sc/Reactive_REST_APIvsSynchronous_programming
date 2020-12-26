package es.upm.miw.synchronousrestapi.client_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ClientBasicDto create(@RequestBody ClientCreationDto clientCreationDto) {
        clientCreationDto.validate();
        return this.clientBusinessController.create(clientCreationDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.clientBusinessController.delete(id);
    }

    @GetMapping
    public List<ClientBasicDto> readAll() {
        return this.clientBusinessController.readAll();
    }
}
