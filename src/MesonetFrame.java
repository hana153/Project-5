import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Class to create a GUI to accept the given input and relay the calculated information from that. This uses a 
 * Mesonet.txt file to find the given stations with the parameters inputed by the user. The user can also add
 * a station to the given list and there is a section where _____ can occur.
 * 
 * @author Hana Stevenson
 * @version 2019-04-29
 *
 */
public class MesonetFrame extends JFrame
{
    // variables for the slider section of the frame
    private JLabel sliderSection = new JLabel("Enter Hamming Dist:");
    private JSlider slider = new JSlider(0, 4);
    private int sliderValue;

    /**
     * Constructor for the MesonetFrame class. 
     */
    public MesonetFrame()
    {
        // creating the slider portion graphics of the frame
        JPanel sliders = new JPanel(new GridLayout(2, 1));
        sliders.add(sliderSection);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        sliders.add(slider);
        sliderValue = slider.getValue();
        
        
        
        
        
        // adding all of the panels to the frame
        this.add(sliders);
        
        // basics for the initial JFrame
        this.setTitle("Hamming Distance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setVisible(true);
    }
    
    /**
     * Creates the Mesonet frame.
     * @param args the argument for the main method
     */
    public static void main(String[]args)
    {
        MesonetFrame test = new MesonetFrame();
    }
}
