package es.upm.miw.synchronousrestapi;

public class VersionDto {

    private String version;

    public VersionDto() {
        // Empty for framework
    }

    public VersionDto(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "VersionDto{" +
                "version='" + version + '\'' +
                '}';
    }
}
