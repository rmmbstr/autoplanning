package one;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ME on 10/31 031.
 */
public class Mygraphic extends JPanel {
    double[] data;
    String name;
    public Mygraphic(){
        setSize(700,700);
    }
    public Mygraphic(double[] Inputdata, String name){
        setSize(700,700);
        data = Inputdata;
        this.name = name;
    }
    Font fn = new Font("Arial", Font.PLAIN, 20);
    int x = 50;
    int y = 20;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(x-1, y, 2, 600);
        g2d.fillRect(x-1, y+600, 600, 2);
        for (int i = 0; i < 37; i++)
        {
            g2d.drawLine(x + i * 15, 600 + y, x + i * 15, y + 45);
            g2d.drawLine(x, 600 + y - i * 15, x + 555, y + 600 - i * 15);
            g2d.drawString("0", x - 20, y+620);
            if (i % 2 == 0 && i / 2 != 0)
            {
                g2d.drawString(String.valueOf(i / 2*6), x - 20, y+605 - i / 2 * 30);
                g2d.drawString(String.valueOf(i / 2*500), x - 5 + i / 2 * 30, y+620);
            }
        }
        g2d.setFont(fn);
        g2d.setColor(Color.black);
        g2d.drawString("%", x-20, y+40);
        g2d.drawString("Dose", x+570, y+620);
        g2d.drawString(name,x+550,y+20);
        g2d.setColor(Color.RED);
        for (int i = 0; i < data.length-1; i++) {
            g2d.drawLine(x+i*5,(int)(y+600-data[i]*500),x+(i+1)*5,(int)(y+600-data[i+1]*500));
        }

        g2d.dispose();
    }

}
