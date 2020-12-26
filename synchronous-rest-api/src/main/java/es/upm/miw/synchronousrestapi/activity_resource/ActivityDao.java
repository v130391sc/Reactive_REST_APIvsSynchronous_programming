package es.upm.miw.synchronousrestapi.activity_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityDao extends MongoRepository<Activity, String> {
}
