import javax.swing.JFrame;

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

    /**
     * Constructor for the MesonetFrame class. 
     */
    public MesonetFrame()
    {
        // basics for the initial JFrame
        this.setTitle("Hamming Distance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setVisible(true);
    }
    
    public static void main(String[]args)
    {
        MesonetFrame test = new MesonetFrame();
    }
}
