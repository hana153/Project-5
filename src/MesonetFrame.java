import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class to create a GUI to accept the given input and relay the calculated information from that. This uses a 
 * Mesonet.txt file to find the given stations with the parameters inputed by the user. The user can also add
 * a station to the given list and there is a section where the ascii values of the selected word are displayed.
 * 
 * @author Hana Stevenson
 * @version 2019-04-29
 *
 */
public class MesonetFrame extends JFrame
{
    /** variables for the slider section of the frame */
    private JLabel sliderSection = new JLabel("Enter Hamming Dist:");
    private JTextField sliderText = new JTextField(1);
    private JSlider slider = new JSlider(0, 4);
    private int sliderVal;
    private String[] stations;
    
    /** variables for the large display section of the frame */
    private JTextArea display = new JTextArea();
    private JScrollPane displayScroll = new JScrollPane(display);
    
    /** variables for the buttons on the frame */
    private JButton show = new JButton("Show Stations");
    private JButton calculate = new JButton("Calculate HD");
    private JButton add = new JButton("Add Station");
    
    /** variables for the hamming distance labels and text fields */
    private JLabel d0 = new JLabel("Distance 0:");
    private JLabel d1 = new JLabel("Distance 1:");
    private JLabel d2 = new JLabel("Distance 2:");
    private JLabel d3 = new JLabel("Distance 3:");
    private JLabel d4 = new JLabel("Distance 4:");
    private JTextField hd0 = new JTextField(1);
    private JTextField hd1 = new JTextField(1);
    private JTextField hd2 = new JTextField(1);
    private JTextField hd3 = new JTextField(1);
    private JTextField hd4 = new JTextField(1);
    private JTextField added = new JTextField(4);
    
    /** variables for the drop down menu */
    private JLabel compare = new JLabel("Compare with:");
    private JComboBox<String> dropDown = new JComboBox<String>();
    
    /** variables for the right section of ascii values */
    private JButton ascii = new JButton("Show ascii calculations");
    private JLabel ceil = new JLabel("Ascii Ceiling is:");
    private JLabel flo = new JLabel("Ascii Floor is:");
    private JLabel avg = new JLabel("Ascii Average is:");
    private JLabel chr = new JLabel("Letter Avg is:");
    private JTextField ceilVal = new JTextField(2);
    private JTextField floVal = new JTextField(2);
    private JTextField avgVal = new JTextField(2);
    private JTextField chrVal = new JTextField(1);

    /**
     * This method finds which stations match the given HD and place them into a string array to be displayed
     * @param station the station to which the HD should match
     * @return String[] array of the stations that match the given HD
     */
    public String[] hammingDist(String station)
    {
        int count = 0;
        int totalCount = 0;
        
        // this time through the for loops counts the amount of stations with the same HD
        for (int i = 0; i < stations.length; ++i) {
            if (station.equals(stations[i])) {
            }
            else {
                for (int j = 0; j < station.length(); ++j) {
                    if(station.charAt(j) != (stations[i]).charAt(j)) {
                        ++count;
                    }
                }
                if (count == sliderVal) {
                    ++totalCount;
                }
                count = 0;
            }
        }
        
        // creating the array with the correct size acquired above
        String[] st = new String[totalCount];
        totalCount = 0;
        
        // this time through the set of loops adds the words with the required HD to the array
        for (int i = 0; i < stations.length; ++i) {
            if (station.equals(stations[i])) {
            }
            else {
                for (int j = 0; j < station.length(); ++j) {
                    if(station.charAt(j) != (stations[i]).charAt(j)) {
                        ++count;
                    }
                }
                if (count == sliderVal) {
                    st[totalCount] = stations[i];
                    ++totalCount;
                }
                count = 0;
            }
        }
       
        return st;
    }
    
    /**
     * This method calculates the amount of stations that have the same Hamming dist as the given station
     * @param station the stations that the HD should be calculated for
     * @param nodes the number of nodes to be calculated for
     * @return int the number of stations that have the same number of nodes as the gievn station
     */
    public int entireHammingDist(String station, int nodes) 
    {
        int count = 0;
        int totalCount = 0;
        
        //these for loops count how many words have the same distance needed
        for (int i = 0; i < stations.length; ++i) {
            if (station.equals(stations[i])) {
            }
            else {
                for (int j = 0; j < station.length(); ++j) {
                    if(station.charAt(j) != (stations[i]).charAt(j)) {
                        ++count;
                    }
                }
                if (count == nodes) {
                    ++totalCount;
                }
                count = 0;
            }
        }
        return totalCount;
    }
    
    /**
     * This method reads the given file and places the stations within the file into a string array.
     * @param filename name of the file to be read
     * @throws IOException
     */
    public void readFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
       
        String st = "";
        st = br.readLine();
        for (int i = 0; i < stations.length; ++i) {
            String word = st.substring(0, 4);
            stations[i] = word;
            st = br.readLine();
        }
        br.close();
    }
    
    /**
     * Creates the list needed to put the stations into the combo list
     * @return DefaultComboBoxModel<String> holds the string values to go in the drop box
     */
    private DefaultComboBoxModel<String> getComboBoxModel(String addedStation)
    {
        ArrayList<String> displayNames = new ArrayList<String>();
        for (int i = 0; i < stations.length; ++i)
        {
            displayNames.add(stations[i]);
        }
        if(addedStation != null)
        {
            if(!displayNames.contains(addedStation))
            {
                displayNames.add(addedStation); 
                Collections.sort(displayNames);
            }
           
        }
        String[] comboBoxModel = displayNames.toArray(new String[displayNames.size()]);
        return new DefaultComboBoxModel<>(comboBoxModel);
    }
    
    /**
     * Computes the different average values for the given station and returns them
     * @param station the station to get the ascii values for
     * @return int[] the list of values for the averages
     */
    public int[] calAverage(String station)
    {
        char[] station1 = station.toCharArray();
        int sum = 0;
        // for loop to find sum of ascii values
        for (int i = 0; i < station1.length; ++i) 
        {
            sum += (int) station1[i];
        }
        
        // array of values for output
        int[] result = new int[3];
        
        // finding ceiling value
        result[0] = (int) Math.ceil(sum / (double) station1.length);
        
        // finding floor value
        result[1] = (int) Math.floor(sum / (double) station1.length);
        
        // finding average value
        result[2] = (int) Math.round(sum / (double) station1.length);
        
        return result;
    }
    
    /**
     * This method computes the average ascii value and turns that into a char
     * @param station the station the value should be computed for 
     * @return char the value of the avg ascii value
     */
    public char letterAverage(String station)
    {
        int sum = 0;
        char[] station1 = station.toCharArray();
        
        // for loop to find the sum of ascii values
        for (int i = 0; i < station1.length; ++i) 
        {
            sum += (int) station1[i];
        }
        
        // finding the average value
        int result = (int) Math.round(sum / (double) station1.length);
       
        // returning the char at the average ascii value
        return (char) result;
    }
    
    /**
     * Constructor for the MesonetFrame class. 
     * @throws IOException 
     */
    public MesonetFrame() throws IOException
    {
        // creating the major panels
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new GridLayout(7, 0));
        JPanel rightPanel = new JPanel(new GridLayout(4, 1));
        
        // creating the array with the stations in it
        stations = new String[120];
        readFile("Mesonet.txt");
        
        // creating the slider portion graphics of the frame
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        sliderText.setEditable(false);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
              sliderVal = slider.getValue();
              sliderText.setText(Integer.toString(sliderVal));
            }
          });
        
        
        // making HD values and large display box not editable
        hd0.setEditable(false);
        hd1.setEditable(false);
        hd2.setEditable(false);
        hd3.setEditable(false);
        hd4.setEditable(false);
        display.setEditable(false);
        
        // creating the drop down menu
        DefaultComboBoxModel<String> comboBoxModel = getComboBoxModel(null);
        dropDown.setModel(comboBoxModel);
        dropDown.setSelectedIndex(0);
        
        // making average values not editable 
        ceilVal.setEditable(false);
        floVal.setEditable(false);
        avgVal.setEditable(false);
        chrVal.setEditable(false);
        
        // result of pushing the show station button
        show.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String[] print = hammingDist(dropDown.getSelectedItem().toString());
                String pt = "";
                for (int i = 0; i < print.length; ++i)
                {
                    pt += print[i] + "\n";
                }
                display.setText(pt);
            } 
        });
        
        // result of pushing calculate HD button
        calculate.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                hd0.setText(Integer.toString((entireHammingDist(dropDown.getSelectedItem().toString(), 0))));
                hd1.setText(Integer.toString((entireHammingDist(dropDown.getSelectedItem().toString(), 1))));
                hd2.setText(Integer.toString((entireHammingDist(dropDown.getSelectedItem().toString(), 2))));
                hd3.setText(Integer.toString((entireHammingDist(dropDown.getSelectedItem().toString(), 3))));
                hd4.setText(Integer.toString((entireHammingDist(dropDown.getSelectedItem().toString(), 4))));
            } 
        });
        
        // result of pushing add station button
        add.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String st2 = (added.getText()).toUpperCase();
                st2 = st2.substring(0, 4);
                DefaultComboBoxModel<String> comboBoxModel2 = getComboBoxModel(st2);
                dropDown.setModel(comboBoxModel2);
                dropDown.setSelectedIndex(0);
            } 
        });
        
        // result of pushing ascii calculate button
        ascii.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                int[] vals = calAverage((dropDown.getSelectedItem()).toString());
                char val = letterAverage((dropDown.getSelectedItem()).toString());
                ceilVal.setText(Integer.toString(vals[0]));
                floVal.setText(Integer.toString(vals[1]));
                avgVal.setText(Integer.toString(vals[2]));
                chrVal.setText(Character.toString(val));
            } 
        });
        

        // adding slider portion to panel     
        JPanel sliderTexts = new JPanel();
        sliderTexts.add(sliderSection);
        sliderTexts.add(sliderText);
        JPanel sliderVis = new JPanel();
        sliderVis.add(slider);
        
        // adding first button to panel 
        JPanel showButton = new JPanel();
        showButton.add(show);
        
        // adding the display area to the panel
        JPanel displayBox = new JPanel(new GridLayout(1, 1));
        displayScroll.createVerticalScrollBar();
        displayBox.add(displayScroll);
        
        // adding combo box to panel
        JPanel dropMenu = new JPanel();
        dropMenu.add(compare);
        dropMenu.add(dropDown);
        
        // adding second button to panel
        JPanel calc = new JPanel();
        calc.add(calculate);
        
        // adding Hamming Distance labels and fields and creating the bottom panel
        JPanel hamDist = new JPanel(new GridLayout(6, 2));
        hamDist.add(d0);
        hamDist.add(hd0);
        hamDist.add(d1);
        hamDist.add(hd1);
        hamDist.add(d2);
        hamDist.add(hd2);
        hamDist.add(d3);
        hamDist.add(hd3);
        hamDist.add(d4);
        hamDist.add(hd4);
        
        // adding third button to panel
        hamDist.add(add);
        hamDist.add(added);
        
        // adding smaller panels to left panel
        leftPanel.add(sliderTexts);
        leftPanel.add(sliderVis);
        leftPanel.add(showButton);
        leftPanel.add(displayBox);
        leftPanel.add(dropMenu);
        leftPanel.add(calc);
        leftPanel.add(hamDist);
        
        // adding components to right panel
        JPanel r0 = new JPanel();
        r0.add(ascii);
        JPanel r1 = new JPanel(new GridLayout(4, 2));
        r1.add(ceil);
        r1.add(ceilVal);
        r1.add(flo);
        r1.add(floVal);
        r1.add(avg);
        r1.add(avgVal);
        r1.add(chr);
        r1.add(chrVal);
        
        // adding smaller panels to right panel
        rightPanel.add(r0);
        rightPanel.add(r1);
        
        // adding side panels to main panel
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        // adding all of the panels to the frame
        this.add(mainPanel);
        
        // basics for the initial JFrame
        this.setTitle("Hamming Distance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        this.setVisible(true);
    }
    
    /**
     * Creates the Mesonet frame.
     * @param args the argument for the main method
     * @throws IOException 
     */
    public static void main(String[]args) throws IOException
    {
        MesonetFrame test = new MesonetFrame();
    }
}
