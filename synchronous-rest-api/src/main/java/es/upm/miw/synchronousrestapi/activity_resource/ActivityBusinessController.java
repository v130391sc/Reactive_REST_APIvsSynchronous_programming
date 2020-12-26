package es.upm.miw.synchronousrestapi.activity_resource;

import es.upm.miw.synchronousrestapi.client_resource.Client;
import es.upm.miw.synchronousrestapi.client_resource.ClientDao;
import es.upm.miw.synchronousrestapi.exceptions.NotFoundException;
import es.upm.miw.synchronousrestapi.trainer_resource.Trainer;
import es.upm.miw.synchronousrestapi.trainer_resource.TrainerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ActivityBusinessController {

    private ActivityDao activityDao;

    private TrainerDao trainerDao;

    private ClientDao clientDao;

    @Autowired
    public ActivityBusinessController(ActivityDao activityDao, TrainerDao trainerDao, ClientDao clientDao) {
        this.activityDao = activityDao;
        this.trainerDao = trainerDao;
        this.clientDao = clientDao;
    }

    public ActivityBasicDto create(ActivityCreationDto activityCreationDto) {
        Trainer trainer = null;
        if (activityCreationDto.getTrainerId() != null) {
            trainer = this.trainerDao.findById(activityCreationDto.getTrainerId())
                    .orElseThrow(() -> new NotFoundException("Trainer id: " + activityCreationDto.getTrainerId()));
        }
        Activity activity = new Activity(activityCreationDto.getActivityName(), activityCreationDto.getLength(), activityCreationDto.getMinLevelRequired(), trainer);
        if (activityCreationDto.getClientsIds() != null) {
            activityCreationDto.getClientsIds().stream().forEach(clientId -> {
                Client client = this.clientDao.findById(clientId).orElseThrow(() -> new NotFoundException("Client id: " + clientId));
                activity.getClients().add(client);
            });
        }
        return new ActivityBasicDto(this.activityDao.save(activity));
    }

    public void delete(String id) {
        this.activityDao.delete(this.find(id));
    }

    private Activity find(String id) {
        return this.activityDao.findById(id).orElseThrow((() -> new NotFoundException("Activity id: " + id)));
    }

    public List<ActivityBasicDto> findByClientPhysicalLevelGreaterThan(Integer value) {
        return this.activityDao.findAll().stream()
                .filter(activity -> this.anyMatchActivityClientPhysicalLevelGreaterThan(activity, value))
                .map(ActivityBasicDto::new)
                .collect(Collectors.toList());
    }

    private boolean anyMatchActivityClientPhysicalLevelGreaterThan(Activity activity, Integer value) {
        return activity.getClients().stream()
                .map(Client::getPhysicalLevel)
                .anyMatch(physicalLevel -> physicalLevel > value);
    }

    public void updateDuration(String id, ActivityUpdateDto activityUpdateDto) {
        Activity activity = this.activityDao.findById(id).orElseThrow(() -> new NotFoundException("Activity id: " + id));
        activity.setDuration(activityUpdateDto.getLenght());
        this.activityDao.save(activity);
    }
}
