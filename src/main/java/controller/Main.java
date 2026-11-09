package controller;

import model.colorname.ColorNameProvider;
import model.colorname.KnnColorNameProvider;
import model.colorname.NullColorNameProvider;

import java.awt.*;
import java.net.URISyntaxException;
import java.nio.file.Path;

//TODO: Rename this class once the final name has been decided
public class Main {
   public static void main(String[] args) {
       ColorNameProvider nameProvider;
       try {
           Path colorNameFilePath = Path.of(Main.class.getClassLoader().getResource("colornames.csv").toURI());
           nameProvider = new KnnColorNameProvider(colorNameFilePath);
       } catch (URISyntaxException e) {
           nameProvider = new NullColorNameProvider();
       }
       Controller controller = new Controller(nameProvider);
       controller.run(args);
   }
}
