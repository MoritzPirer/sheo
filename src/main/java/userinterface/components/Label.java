package userinterface.components;

public interface Label extends UiComponent {
    void setText(String text);
    String getText();

    void enableAutomaticTextColor();
}
