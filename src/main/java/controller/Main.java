package controller;

import shared.ColorUtility;

import java.awt.*;

//TODO: Rename this class once the final name has been decided
public class Main {
   public static void main(String[] args) {
       int success = 0;
       int attempt = 0;
       for (int r = 0; r < 256; r++) {
           for (int g = 0; g < 256; g++) {
               for (int b = 0; b < 256; b++) {
                   Color color = new Color(r, g, b);
                   double[] hsl = ColorUtility.toHsl(color);
                   Color result = ColorUtility.fromHsl(hsl[0], hsl[1], hsl[2]);
                   if (color.equals(result)) {
                       success++;
                   }
                   attempt++;
               }
           }
       }

       System.out.println("correct: " + success + "/" + attempt + "(" + (double)success / attempt + ")");
       Controller controller = new Controller();
       controller.run(args);
   }
}
