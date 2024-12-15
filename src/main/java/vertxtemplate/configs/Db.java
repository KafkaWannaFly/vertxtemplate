package vertxtemplate.configs;

public record Db(String host, String port, String user, String password, String name) {
    public String url() {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, name);
    }
}
