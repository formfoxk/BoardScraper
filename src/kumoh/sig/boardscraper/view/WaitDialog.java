package kumoh.sig.boardscraper.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitDialog extends JDialog{
	public WaitDialog(JFrame frame){
        super(frame);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
       
        
        JLabel lbWait = new JLabel("Waiting");
        lbWait.setFont(new Font("돋움", Font.BOLD, 20));
        lbWait.setForeground(Color.WHITE);
        
        JLabel lbImage = new JLabel();
        ImageIcon icon = new ImageIcon(this.getClass().getResource(
                "images/ajax-loader.gif"));
        lbImage.setIcon(icon);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);
        
        panel.add(lbImage);
        panel.add(lbWait);
        
        this.add(panel);
        
        this.pack();
    }
}
