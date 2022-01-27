package configure;

public class GradleDependency {
    public final String type;
    public final String name;
    public final String version;

    public GradleDependency(String type, String name, String version) {
        this.type = type;
        this.name = name;
        this.version = version;
    }

    public String fullString() {
        return type + " '" + name + ":" + version + "'";
    }

    public String onlyNameVersionString() {
        return "'" + name + ":" + version + "'";
    }
}
