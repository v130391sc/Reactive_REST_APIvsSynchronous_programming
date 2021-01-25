package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.documents.Activity;
import lombok.Data;

import java.util.List;

@Data
public class ActivityBasicDto {

    private String id;

    private String activityName;

    private List<String> clientIds;

    public ActivityBasicDto() {
    }

    public ActivityBasicDto(Activity activity) {
        this.id = activity.getId();
        this.activityName = activity.getName();
        this.clientIds = activity.getClientsIds();
    }
}
