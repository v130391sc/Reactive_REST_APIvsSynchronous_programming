package es.upm.miw.synchronousrestapi.client_resource;

import es.upm.miw.synchronousrestapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientBusinessController {

    private ClientDao clientDao;

    @Autowired
    public ClientBusinessController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public ClientBasicDto create(ClientCreationDto clientCreationDto) {
        Client client = new Client(clientCreationDto.getFullName(), clientCreationDto.getStrongLevel(), clientCreationDto.getBirthDay());
        this.clientDao.save(client);
        return new ClientBasicDto(client);
    }

    public void delete(String id) {
        this.clientDao.delete(this.find(id));
    }

    private Client find(String id) {
        return this.clientDao.findById(id).orElseThrow((() -> new NotFoundException("Client id: " + id)));
    }

    public List<ClientBasicDto> readAll(){
        List<Client> clients = this.clientDao.findAll();
        return clients.stream().map(ClientBasicDto::new).collect(Collectors.toList());
    }
}
