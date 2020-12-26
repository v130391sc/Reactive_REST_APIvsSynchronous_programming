package es.upm.miw.synchronousrestapi.room_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomDao extends MongoRepository<Room, String> {
}
