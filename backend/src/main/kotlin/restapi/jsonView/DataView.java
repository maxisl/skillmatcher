package restapi.jsonView;

// see: https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring

public class DataView {
    public interface User {}
    public interface UserWithProjects extends User, Project{}
    public interface Project {}
    public interface ProjectWithOwner extends Project, User {}
    public interface ProjectWithAttendeesAndOwner extends ProjectWithOwner, User {}
}