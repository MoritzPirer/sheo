package userinterface.javafxinterface;

import userinterface.UiFactory;
import userinterface.components.*;

public class JavaFxFactory implements UiFactory {
    @Override
    public Button createButton(String text) {
        return new JavaFxButton(text);
    }

    @Override
    public Label createLabel(String text) {
        return new JavaFxLabel(text);
    }

    @Override
    public UiContainer createVerticalUiContainer() {
        return new JavaFxVerticalContainer();
    }

    @Override
    public UiContainer createHorizontalUiContainer() {
        return new JavaFxHorizontalContainer();
    }

    @Override
    public Scene createScene(UiContainer uiContainer) {
        return new JavaFxScene(uiContainer);
    }

    @Override
    public UiApplication createUiApplication(String title) {
        return new JavaFxApplication(title);
    }

    @Override
    public ColorPicker createCanvasColorPicker() {
        return new JavaFxCanvasColorPicker();
    }

    @Override
    public ColorPicker createRgbColorPicker() {
        return new JavaFxSliderColorPicker(new RgbSliderManager());
    }

    @Override
    public ColorPicker createHsvColorPicker() {
        return new JavaFxSliderColorPicker(new HsvSliderManager());
    }

    @Override
    public ColorPicker createHslColorPicker() {
        return new JavaFxSliderColorPicker(new HslSliderManager());
    }

    @Override
    public ColorPicker createTextBoxColorPicker() {
        return new JavaFxTextBoxColorPicker();
    }

    @Override
    public DropdownSelection createDropdownSelection(String... options) {
        return new JavaFxDropdownSelection(options);
    }

}
