package es.upm.miw.reactiverestapi.api_rest_controllers;

import es.upm.miw.reactiverestapi.ApiTestConfig;
import es.upm.miw.reactiverestapi.dtos.TrainerCreationDto;
import es.upm.miw.reactiverestapi.dtos.TrainerDto;
import es.upm.miw.reactiverestapi.dtos.TrainerPatchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestConfig
public class TrainerResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        TrainerDto trainerDto = createTrainer("Testname");
        assertEquals("Testname", trainerDto.getName());
    }

    TrainerDto createTrainer(String name) {
        TrainerCreationDto trainerCreationDto = new TrainerCreationDto(name);
        return this.webTestClient
                .post().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(TrainerDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateTrainerException() {
        TrainerCreationDto trainerCreationDto = new TrainerCreationDto(null);
        this.webTestClient
                .post().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testFind() {
        createTrainer("Sergio Apellido");
        createTrainer("Sergio Nombre");
        createTrainer("Alvaro");
        List<TrainerDto> trainerList = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(TrainerResource.TRAINERS + TrainerResource.SEARCH)
                                .queryParam("query", "name:=Sergio")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TrainerDto.class)
                .returnResult().getResponseBody();
        assertEquals(2, trainerList.size());
    }

    @Test
    void testFindEmpty() {
        createTrainer("Sergio Apellido");
        createTrainer("Sergio Nombre");
        createTrainer("Alvaro");
        List<TrainerDto> trainerList = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(TrainerResource.TRAINERS + TrainerResource.SEARCH)
                                .queryParam("query", "name:=NombreNoExistente")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TrainerDto.class)
                .returnResult().getResponseBody();
        assertTrue(trainerList.isEmpty());
    }

    @Test
    void testFindException() {
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(TrainerResource.TRAINERS + TrainerResource.SEARCH)
                        .queryParam("query", "nombre:=NombreNoExistente")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatch() {
        List<TrainerPatchDto> trainerPatchDtoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TrainerDto trainer = createTrainer("OldName" + i);
            trainerPatchDtoList.add(new TrainerPatchDto(trainer.getId(), "newName" + i));
        }
        this.webTestClient
                .patch().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerPatchDtoList))
                .exchange()
                .expectStatus().isOk();
        List<TrainerDto> trainerList = this.webTestClient
                .get().uri(uriBuilder ->
                        uriBuilder.path(TrainerResource.TRAINERS + TrainerResource.SEARCH)
                                .queryParam("query", "name:=newName")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TrainerDto.class)
                .returnResult().getResponseBody();
        assertEquals("newName0", trainerList.get(0).getName());
        assertEquals("newName1", trainerList.get(1).getName());
        assertEquals("newName2", trainerList.get(2).getName());
    }

    @Test
    void testPatchBadRequestIdException() {
        List<TrainerPatchDto> trainerPatchDtoList = new ArrayList<>();
        TrainerDto trainer = createTrainer("Name1");
        trainerPatchDtoList.add(new TrainerPatchDto(null, "OldName1"));
        TrainerDto trainer2 = createTrainer("Name2");
        trainerPatchDtoList.add(new TrainerPatchDto(trainer2.getId(), "OldName2"));
        this.webTestClient
                .patch().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerPatchDtoList))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatchBadRequestNameException() {
        List<TrainerPatchDto> trainerPatchDtoList = new ArrayList<>();
        TrainerDto trainer = createTrainer("Name1");
        trainerPatchDtoList.add(new TrainerPatchDto(trainer.getId(), "OldName1"));
        TrainerDto trainer2 = createTrainer("Name2");
        trainerPatchDtoList.add(new TrainerPatchDto(trainer2.getId(), null));
        this.webTestClient
                .patch().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerPatchDtoList))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPatchIdNotFoundException() {
        List<TrainerPatchDto> trainerPatchDtoList = new ArrayList<>();
        TrainerDto trainer = createTrainer("Name1");
        trainerPatchDtoList.add(new TrainerPatchDto(trainer.getId(), "OldName1"));
        trainerPatchDtoList.add(new TrainerPatchDto("IdNoExistente", "newNameNoExistente"));
        this.webTestClient
                .patch().uri(TrainerResource.TRAINERS)
                .body(BodyInserters.fromObject(trainerPatchDtoList))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testPutName() {
        String id = createTrainer("Testname").getId();
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Newtestname");
        this.webTestClient
                .put().uri(TrainerResource.TRAINERS + TrainerResource.ID + TrainerResource.NAME, id)
                .body(BodyInserters.fromObject(trainerDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testPutNameNotFoundException() {
        String id = createTrainer("Testname").getId();
        this.webTestClient
                .put().uri(TrainerResource.TRAINERS + TrainerResource.ID + TrainerResource.NAME, id)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutNameBadRequestException() {
        this.webTestClient
                .put().uri(TrainerResource.TRAINERS + TrainerResource.ID + TrainerResource.NAME, "noId")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
