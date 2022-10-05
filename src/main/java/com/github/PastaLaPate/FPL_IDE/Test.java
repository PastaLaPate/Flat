package com.github.PastaLaPate.FPL_IDE;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class Test {
    public static void main(String[] args) throws IOException {
        // Create icon
        Image image = ImageIO.read(Objects.requireNonNull(Test.class.getResource("/img/cross.png")));;
        Icon icon =  new ImageIcon( image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));


        // Create label
        final JLabel lbl = new JLabel("Hello, World", icon, (int) JLabel.LEFT_ALIGNMENT);

        // Create button
        JButton btn = new JButton("Click Me");
        btn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            // Remove icon when button is clicked.
            lbl.setIcon(null);

            // **IMPORTANT** to call revalidate() to cause JLabel to resize and be repainted.
            lbl.revalidate();
          }
        });
        btn.setMaximumSize(new Dimension(300, 50));
        JFrame jFrame = new JFrame("AHHA");
        jFrame.add(lbl);
        jFrame.add(btn);
        jFrame.setLayout(new FlowLayout());
        jFrame.pack();
        jFrame.setSize(300, 200);
        jFrame.setVisible(true);
    }
}
