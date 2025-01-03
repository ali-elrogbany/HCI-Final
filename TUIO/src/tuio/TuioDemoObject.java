package TUIO;
 
/*
TUIO Java GUI Demo
Copyright (c) 2005-2014 Martin Kaltenbrunner <martin@tuio.org>
Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files
(the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:
The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
import Project.CircleMenu;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import TUIO.*;
 
public class TuioDemoObject extends TuioObject {
 
    private Shape square;
 
    public TuioDemoObject(TuioObject tobj) {
        super(tobj);
        int size = TuioDemoComponent.object_size;
        square = new Rectangle2D.Float(-size / 2, -size / 2, size, size);
 
        AffineTransform transform = new AffineTransform();
        transform.translate(xpos, ypos);
        transform.rotate(angle, xpos, ypos);
        square = transform.createTransformedShape(square);
    }
 
    public void paint(Graphics2D g, int width, int height) {
        float Xpos = xpos * width;
        float Ypos = ypos * height;
        float scale = height / (float) TuioDemoComponent.table_size;
 
        AffineTransform trans = new AffineTransform();
        trans.translate(-xpos, -ypos);
        trans.translate(Xpos, Ypos);
        trans.scale(scale, scale);
        Shape s = trans.createTransformedShape(square);
        if (symbol_id == 1){
            g.setPaint(Color.red);
            g.fill(s);
            g.setPaint(Color.white);
            g.drawString(symbol_id + "Kenzy", Xpos - 10, Ypos);
 
        }
        else{
            g.setPaint(Color.blue);
            g.fill(s);
            g.setPaint(Color.WHITE);
            g.drawString("Aabat", Xpos - 10, Ypos);
        }
    }
 
    public void update(TuioObject tobj) {
        float dx = tobj.getX() - xpos;
        float dy = tobj.getY() - ypos;
        float da = tobj.getAngle() - angle;

        if ((dx != 0) || (dy != 0)) {
            AffineTransform trans = AffineTransform.getTranslateInstance(dx, dy);
            square = trans.createTransformedShape(square);
        }

        if (da != 0) {
            AffineTransform trans = AffineTransform.getRotateInstance(da, tobj.getX(), tobj.getY());
            square = trans.createTransformedShape(square);
        }

        super.update(tobj);
        
        if (CircleMenu.instance != null && CircleMenu.instance.getPublicModeActive() == false) {
            switch (symbol_id) {
                case 0:
                    float angleInDegrees = (float) Math.toDegrees(angle);
                    CircleMenu.instance.highlightTileByRotation(angleInDegrees);
                    break;
                case 1:
                    CircleMenu.instance.selectOption();
                    break;
                default:
                    CircleMenu.instance.SetSelectedProductIndex(symbol_id);
                    break;
            }
        } 
        else {
            System.err.println("CircleMenu instance is null. Cannot perform the requested action.");
        }
    }
}