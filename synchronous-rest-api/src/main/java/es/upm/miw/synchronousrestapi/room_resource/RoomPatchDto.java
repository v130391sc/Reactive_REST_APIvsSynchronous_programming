package es.upm.miw.synchronousrestapi.room_resource;

import es.upm.miw.synchronousrestapi.exceptions.BadRequestException;

public class RoomPatchDto {

    private String path;

    private Integer newValue;

    public RoomPatchDto(){

    }

    public RoomPatchDto(String path, Integer newValue){
        this.path = path;
        this.newValue = newValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getNewValue() {
        return newValue;
    }

    public void setNewValue(Integer newValue) {
        this.newValue = newValue;
    }

    public void validate(){
        if( this.path == null || this.path.isEmpty()){
            throw new BadRequestException("Incomplete UserPatchDto");
        }
    }
}
