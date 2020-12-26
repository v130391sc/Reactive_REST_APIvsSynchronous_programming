package es.upm.miw.synchronousrestapi.trainer_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainerDao extends MongoRepository<Trainer, String> {

    List<Trainer> findByNameStartingWith(String regexp);
}
