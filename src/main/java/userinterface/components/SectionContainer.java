package userinterface.components;

public interface SectionContainer extends UiComponent {

    void setCenter(UiComponent component);
    void setTop(UiComponent component);
    void setBottom(UiComponent component);
    void setLeft(UiComponent component);
    void setRight(UiComponent component);
}
