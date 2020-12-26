package es.upm.miw.synchronousrestapi.room_resource;

import es.upm.miw.synchronousrestapi.ApiTestConfig;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityBasicDto;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityCreationDto;
import es.upm.miw.synchronousrestapi.activity_resource.ActivityResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

@ApiTestConfig
public class RoomResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    RoomBasicDto createRoom(LocalDateTime schedule, Integer capacity) {
        ActivityCreationDto activityCreationDto =
                new ActivityCreationDto("Pilates", 1.5, 6, null);
        String activityId = this.webTestClient
                .post().uri(ActivityResource.ACTIVITIES)
                .body(BodyInserters.fromObject(activityCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityBasicDto.class)
                .returnResult().getResponseBody().getId();
        return this.webTestClient
                .post().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(new RoomCreationDto(schedule, capacity, activityId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(RoomBasicDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreate() {
        this.createRoom(LocalDateTime.of(2019, 10, 21, 18, 0), 30);
    }

    @Test
    void testCreateBadRequestScheduleException() {
        RoomCreationDto roomCreationDto =
                new RoomCreationDto(null, 20, null);
        this.webTestClient
                .post().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(roomCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateBadRequestSeatingException() {
        RoomCreationDto roomCreationDto =
                new RoomCreationDto(LocalDateTime.of(2019, 10, 21, 18, 0), -10, null);
        this.webTestClient
                .post().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(roomCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateActivityNotFoundException() {
        RoomCreationDto roomCreationDto =
                new RoomCreationDto(LocalDateTime.of(2019, 10, 21, 18, 0), 50, "actividadIdNoExistente");
        this.webTestClient
                .post().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(roomCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateAllRoomsSchedule () {
        RoomPatchDto roomPatchDto = new RoomPatchDto("schedule", 3);
        for (int i = 0; i < 3; i++) {
            RoomBasicDto room = createRoom(LocalDateTime.of(2019, 10,21,15,0).plusHours(i), 20);
        }
        this.webTestClient
                .patch().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(roomPatchDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateRoomsBadRequestException() {
        this.webTestClient
                .patch().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(new RoomPatchDto("name",2)))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testRoomsPatchDtoBadRequestException() {
        this.webTestClient
                .patch().uri(RoomResource.ROOMS)
                .body(BodyInserters.fromObject(new RoomPatchDto()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
