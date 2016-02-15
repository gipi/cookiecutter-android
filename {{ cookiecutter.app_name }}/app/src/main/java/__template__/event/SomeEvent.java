package {{ cookiecutter.package_name }}.event;

public class SomeEvent {

    public final String data;

    public SomeEvent(String data) {
        this.data = data;
    }
}
