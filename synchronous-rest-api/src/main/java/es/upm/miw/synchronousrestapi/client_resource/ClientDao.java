package es.upm.miw.synchronousrestapi.client_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientDao extends MongoRepository<Client, String> {
}
