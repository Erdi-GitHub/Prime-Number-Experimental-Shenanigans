package me.erdi.primeshenanigans.demo;

import me.erdi.primeshenanigans.SieveOfEratosthenesIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GraphicalDemo extends JFrame {
    private final JPanel panel = new JPanel();

    private GraphicalDemo() {
        super("Primes :3");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setPreferredSize(new Dimension(768, 768));
        add(panel);
        pack();
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        GraphicalDemo app = new GraphicalDemo();

        int size = 1849;
        SieveOfEratosthenesIterator priterator = new SieveOfEratosthenesIterator(size);

        AtomicInteger n = new AtomicInteger(0);
        new Thread(() -> {
            while(priterator.hasNext()) {
                try {
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                int prime = priterator.next();
                n.set(prime);

                if(prime > priterator.getMaxCompareSize())
                    break;
            }
        }).start();

        new Thread(() -> {
            while(true)
                app.update(30, n.get() - 1, priterator.getMaxCompareSize() - 1, priterator.tapeStream());
        }).start();
    }

    private void update(int scale, int currPrime, int compareSize, Stream<Boolean> tape) {
        BufferedImage buffer = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = buffer.createGraphics();

        Iterator<Boolean> iter = tape.iterator();
        int i = 0;

        if(currPrime > compareSize) {
            graphics.setColor(new Color(0x90EE90));
            graphics.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        }

        while(iter.hasNext()) {
            boolean prime = iter.next();

            int x = (i * scale) % panel.getWidth();
            int y = (i * scale) / panel.getWidth();
            y *= scale;

            graphics.setColor(prime ? Color.GREEN : Color.RED);
            if(currPrime <= compareSize) {
                if(i == currPrime)
                    graphics.setColor(Color.YELLOW);
                else if(i == compareSize)
                    graphics.setColor(Color.CYAN);
            }

            graphics.fillRect(x, y, scale, scale);
            graphics.setColor(Color.BLUE);
            graphics.drawString(String.valueOf(i + 1), x, y + scale);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x, y, scale, scale);

            i++;
        }

        panel.getGraphics().drawImage(buffer, 0, 0, this);
    }
}
