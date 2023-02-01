package restapi.jsonView;

// see: https://spring.io/blog/2014/12/02/latest-jackson-integration-improvements-in-spring

public class DataView {
    public interface User {}
    public interface UserWithMinimalDetails{}
    public interface UserWithAllDetails extends UserWithMinimalDetails{}
    public interface UserWithProjects extends User, Project{}
    public interface Project {}
    public interface Skill {}
    public interface ChatMessage {}
    public interface UserWithSkill extends User, Skill{}

    public interface ProjectWithSkill extends Project, Skill{}
}