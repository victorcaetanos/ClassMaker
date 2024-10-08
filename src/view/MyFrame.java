package view;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class MyFrame extends JFrame {
    public MyFrame() throws HeadlessException {
        this.setIcon(getClass().getResourceAsStream("/icons/AppIcon.png"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1200, 900);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public void setIcon(InputStream imgStream) {
        BufferedImage myImg;
        try {
            myImg = ImageIO.read(imgStream);
            this.setIconImage(myImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract TableModel buildTableModel(final List<?> objectList);


    public void showErrorMessage(String message) {
        if (message == null) return;
        JOptionPane.showMessageDialog(null, message);
    }
}
