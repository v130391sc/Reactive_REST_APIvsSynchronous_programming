package es.upm.miw.reactiverestapi.repositories;

import es.upm.miw.reactiverestapi.documents.Trainer;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface TrainerReactRepository extends ReactiveSortingRepository<Trainer, String> {

    Flux<Trainer> findByNameStartingWith(String regexp);
}
