package es.upm.miw.synchronousrestapi.suggestion_resource;

import es.upm.miw.synchronousrestapi.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
class SuggestionResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        SuggestionDto suggestionDto = this.webTestClient
                .post().uri(SuggestionResource.SUGGESTIONS)
                .body(BodyInserters.fromObject(new SuggestionDto(false, "Suggestion description")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SuggestionDto.class)
                .returnResult().getResponseBody();
        assertNotNull(suggestionDto);
        assertFalse(suggestionDto.getNegative());
        assertEquals("Suggestion description", suggestionDto.getDescription());
    }

    @Test
    void testCreateSuggestionException() {
        SuggestionDto suggestionDto = new SuggestionDto(false, null);
        this.webTestClient
                .post().uri(SuggestionResource.SUGGESTIONS)
                .body(BodyInserters.fromObject(suggestionDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}

