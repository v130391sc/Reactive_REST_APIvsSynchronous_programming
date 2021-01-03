package es.upm.miw.reactiverestapi.repositories;

import es.upm.miw.reactiverestapi.documents.Room;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface RoomReactRepository extends ReactiveSortingRepository<Room, String> {
}
