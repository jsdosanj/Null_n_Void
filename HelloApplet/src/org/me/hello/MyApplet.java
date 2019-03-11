   package org.me.hello;

     import java.applet.Applet;
     import java.awt.Graphics;

     public class MyApplet extends Applet {
         @Override
         public void paint(Graphics g) {
             g.drawString("Hello applet!", 50, 25);
         }
     }
                    