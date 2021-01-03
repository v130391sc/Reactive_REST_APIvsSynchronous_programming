package es.upm.miw.reactiverestapi.repositories;

import es.upm.miw.reactiverestapi.documents.Activity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface ActivityReactRepository extends ReactiveSortingRepository<Activity, String> {
}
