package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.ApiTestConfig;
import es.upm.miw.reactiverestapi.dtos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class ActivityResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    ActivityCreationDto createActivity(String name, boolean clientsIds, boolean trainerId) {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto(name, 1.5, 5, null);
        if (trainerId) {
            String trainer = this.webTestClient
                    .post().uri(TrainerResource.TRAINERS)
                    .body(BodyInserters.fromObject(new TrainerCreationDto("name")))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(TrainerDto.class)
                    .returnResult().getResponseBody().getId();
            activityCreationDto.setTrainerId(trainer);
        }
        if (clientsIds) {
            List<String> clientList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                ClientCreationDto clientCreationDto = new ClientCreationDto("client" + i, 6, LocalDateTime.of(2019, 10, 13, 19, 0));
                String client = this.webTestClient
                        .post().uri(ClientResource.CLIENTS)
                        .body(BodyInserters.fromObject(clientCreationDto))
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(ClientBasicDto.class)
                        .returnResult().getResponseBody().getId();
                activityCreationDto.getClientsIds().add(client);
            }
        }
        return activityCreationDto;
    }

    @Test
    void testCreate() {
        String activityName = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Pilates", true, true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody().getActivityName();
        assertEquals("Pilates", activityName);
    }

    @Test
    void testCreateWithoutClientsAndTrainers() {
        String activityName = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Yoga", false, false)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody().getActivityName();
        assertEquals("Yoga", activityName);
    }

    @Test
    void testCreateTrainerIdException() {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto("name", 1.5, 5, "trainerIdNoExistente");
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateClientIdException() {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto("name", 1.5, 5, "trainerIdNoExistente");
        activityCreationDto.getClientsIds().add("clientIdNoExistente");
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateNotFoundNameException() {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto(null, 1.5, 5, null);
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateNotFoundLength() {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto("name", null, 5, null);
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateNotFoundMinLevelRequired() {
        ActivityCreationDto activityCreationDto = new ActivityCreationDto("name", 1.5, null, null);
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteActivity() {
        ActivityBasicDto activityBasicDto = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Pilates", true, true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody();
        this.webTestClient
                .delete().uri(uriBuilder ->
                uriBuilder.path(ActivityResource.ACTIVITIES + ActivityResource.ID_ID)
                        .build(activityBasicDto.getId()));
    }

    void testUpdate() {
        ActivityUpdateDto activityUpdateDto = new ActivityUpdateDto();
        String id = getIdFromExistingActivity();
        activityUpdateDto.setLenght(2.5);
        this.webTestClient
                .put().uri(ActivityResource.ACTIVITIES + ActivityResource.ID_ID + ActivityResource.DURATION, id)
                .body(BodyInserters.fromObject(activityUpdateDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteActivityNotFoundException() {
        ActivityBasicDto activityBasicDto = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Pilates", true, true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody();
        this.webTestClient
                .delete().uri(uriBuilder ->
                uriBuilder.path(ActivityResource.ACTIVITIES + ActivityResource.ID_ID)
                        .build("IdNoExistente"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String getIdFromExistingActivity() {
        return this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Pilates", true, true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody().getId();
    }

    @Test
    void testUpdateActivityNotFound() {
        ActivityUpdateDto activityUpdateDto = new ActivityUpdateDto();
        activityUpdateDto.setLenght(2.5);
        this.webTestClient
                .put().uri(ActivityResource.ACTIVITIES + ActivityResource.ID_ID + ActivityResource.DURATION, "ActivityIdNoExistente")
                .body(BodyInserters.fromObject(activityUpdateDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateNoDurationException() {
        ActivityUpdateDto activityUpdateDto = new ActivityUpdateDto();
        this.webTestClient
                .put().uri(ActivityResource.ACTIVITIES + ActivityResource.ID_ID + ActivityResource.DURATION, getIdFromExistingActivity())
                .body(BodyInserters.fromObject(activityUpdateDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateBadDurationException() {
        ActivityUpdateDto activityUpdateDto = new ActivityUpdateDto();
        activityUpdateDto.setLenght(-2.5);
        this.webTestClient
                .put().uri(ActivityResource.ACTIVITIES + ActivityResource.ID_ID + ActivityResource.DURATION, getIdFromExistingActivity())
                .body(BodyInserters.fromObject(activityUpdateDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testSearch() {
        ActivityBasicDto zumba = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Zumba", true, true)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody();
        List<ActivityBasicDto> activities = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(ActivityResource.ACTIVITIES + ActivityResource.SEARCH)
                                .queryParam("q", "client.physicalLevel:>5")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ActivityBasicDto.class)
                .returnResult().getResponseBody();
        assertFalse(activities.isEmpty());
    }

    @Test
    void testSearchEmpty() {
        this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(createActivity("Zumba", true, true)))
                .exchange()
                .expectStatus().isOk();
        List<ActivityBasicDto> activities = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(ActivityResource.ACTIVITIES + ActivityResource.SEARCH)
                                .queryParam("q", "client.physicalLevel:>8")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ActivityBasicDto.class)
                .returnResult().getResponseBody();
        assertTrue(activities.isEmpty());
    }
}
