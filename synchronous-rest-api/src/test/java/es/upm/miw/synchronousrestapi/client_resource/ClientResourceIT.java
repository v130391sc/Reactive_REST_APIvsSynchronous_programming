package es.upm.miw.synchronousrestapi.client_resource;

import es.upm.miw.synchronousrestapi.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class ClientResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ClientBasicDto clientBasicDto = createClient("name");
        assertEquals("name", clientBasicDto.getName());
    }

    ClientBasicDto createClient(String name) {
        ClientCreationDto clientCreationDto = new ClientCreationDto(name, 6, LocalDateTime.of(2019, 10, 13, 19, 0));
        return this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(clientCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientBasicDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    public void testCreateClientException() {
        ClientCreationDto clientCreationDto = new ClientCreationDto("name", null, null);
        this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(clientCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateClientExceptionPhysicalLevel() {
        ClientCreationDto clientCreationDto = new ClientCreationDto("name", -2, LocalDateTime.of(2019, 10, 13, 19, 0));
        this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(clientCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testDeleteClient() {
        ClientBasicDto clientBasicDto = createClient("name");
        this.webTestClient
                .delete().uri(uriBuilder ->
                uriBuilder.path(ClientResource.CLIENTS + ClientResource.ID_ID)
                        .build(clientBasicDto.getId()))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testDeleteClientNotFoundException() {
        this.webTestClient
                .delete().uri(uriBuilder ->
                uriBuilder.path(ClientResource.CLIENTS + ClientResource.ID_ID)
                        .build("IdNoExistente"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testReadAll() {
        ClientCreationDto clientCreationDto = new ClientCreationDto("Testname", 2, LocalDateTime.of(2019, 10, 13, 19, 0));
        this.webTestClient
                .post().uri(ClientResource.CLIENTS)
                .body(BodyInserters.fromObject(clientCreationDto))
                .exchange()
                .expectStatus().isOk();
        List<ClientBasicDto> clientList =
                this.webTestClient
                        .get().uri(ClientResource.CLIENTS)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(ClientBasicDto.class)
                        .returnResult().getResponseBody();
        assertTrue(clientList.size() > 0);
        assertNotNull(clientList.get(0).getId());
        assertNotNull(clientList.get(0).getName());
    }
}
