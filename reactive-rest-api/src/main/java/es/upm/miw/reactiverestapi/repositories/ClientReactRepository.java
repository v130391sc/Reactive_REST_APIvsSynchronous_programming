package es.upm.miw.reactiverestapi.repositories;

import es.upm.miw.reactiverestapi.documents.Client;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ClientReactRepository extends ReactiveSortingRepository<Client, String> {
}
