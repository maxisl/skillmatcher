package restapi.jsonView;

public class DataView {
    public interface User {}
    public interface UserWithMinimalDetails{}
    public interface UserWithAllDetails extends UserWithMinimalDetails{}
    public interface UserWithProjects extends User, Project{}
    public interface Project {}
    public interface Skill {}
    public interface UserWithSkill extends User, Skill{}
    public interface ProjectWithSkill extends Project, Skill{}
}