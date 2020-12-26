package es.upm.miw.synchronousrestapi.suggestion_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestionDao extends MongoRepository<Suggestion, String> {
}
