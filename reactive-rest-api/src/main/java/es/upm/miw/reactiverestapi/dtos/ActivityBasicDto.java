package es.upm.miw.reactiverestapi.dtos;

import es.upm.miw.reactiverestapi.documents.Activity;

public class ActivityBasicDto {

    private String id;

    private String activityName;

    public ActivityBasicDto() {
    }

    public ActivityBasicDto(Activity activity) {
        this.id = activity.getId();
        this.activityName = activity.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "ActivityBasicDto{" +
                "id='" + id + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
