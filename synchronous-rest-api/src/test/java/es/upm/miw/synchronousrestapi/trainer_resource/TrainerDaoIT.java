package es.upm.miw.synchronousrestapi.trainer_resource;

import es.upm.miw.synchronousrestapi.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class TrainerDaoIT {

    @Autowired
    private TrainerDao trainerDao;

    @Test
    void testCreate() {
        Trainer trainer = new Trainer("Testname");
        this.trainerDao.save(trainer);
        Trainer databaseTrainer = this.trainerDao.findById(trainer.getId()).orElseGet(Assertions::fail);
        assertEquals("Testname", databaseTrainer.getName());
    }
}
