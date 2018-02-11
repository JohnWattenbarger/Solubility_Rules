package solubility_rules;
import java.awt.*;
import static java.awt.Label.CENTER;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/* 
Author: John Wattenbarger
Date created:   3/6/17
Date last edited:   5/17/17

Description: 
    - Creates a window to calculate solubility or display solubility rules

Last Edit:
    - Finished adding the solubility rules
    - added isSlightlySoluble()
    - changed button, label, textField, and colors to variable names
    - documented isSoluble()

Next Edit:
    - Change the solubility tests from a boolean to an integer
        - 0 = insoluble, 1 = soluble, 2 = slightly soluble
        - this will change problems where tests for slightly soluble lead to
            insoluble substances
    - Possibly remove invalid (there are many chemicals)
    - Change the Button/TextField/Label widths/heights to constants
    - Possibly add a hints button at the top of the window
    - Edit the error message popup so it works
    - Finish Documentation

Possible Future Edits:
    - Add a periodic table picture/link
    - Change the anions/cations so only correct spellings work
    - Add SFrame to Solubility_Rules
*/

/**
 * Creates a window to calculate solubility or display solubility rules
 * 
 * @author John Wattenbarger
 */
public class SFrame  extends Frame implements ActionListener
{
    private Label cation, anion, solution;
    private TextField field_cation, field_anion, field_solution;
    private Button calculate, rules;
    private Color labelColor = new Color(235, 220, 85);
    private Color button_background = new Color(220, 225, 230);
    private Color button_foreground = new Color(40, 80, 240);
    private int button_width = 350, button_height = 100, text_field_width = 450,
            text_field_height = 50, label_width = 200, label_height = 50;
    
    /**
     * Creates a default SFrame
     */
    public SFrame()
    {
        // set up window
        this.setSize(950, 900);
        this.setLocation(1000, 200);
        this.setBackground(new Color(10, 145, 230));   // Blue
        this.setLayout(null);
        this.setTitle("Solubility Rules");
        this.setFont(new Font("Arial", Font.PLAIN, 32));
        
        // Exit Window
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent set){
                System.exit(0);
            }
        });
        
    // Calculate Button
        calculate=new Button("Calculate");
        calculate.setBounds(100, 650, button_width, button_height);
        calculate.addActionListener(this);
        calculate.setBackground(button_background);
        calculate.setForeground(button_foreground);
        this.add(calculate);
        
    // Solubility Rules Button
        rules=new Button("Solubility Rules");
        rules.setBounds(500, 650, button_width, button_height);
        rules.addActionListener(this);
        rules.setBackground(button_background);
        rules.setForeground(button_foreground);
        this.add(rules);
        
        
    /***********************************************************************/
        
    // cation
        field_cation=new TextField();
        field_cation.setBounds(400, 200, text_field_width, text_field_height);
        this.add(field_cation);
        
        cation = new Label("cation", CENTER);
        cation.setBounds(100, 200, label_width, label_height);
        cation.setBackground(labelColor);
        this.add(cation);
        
    // anion
        field_anion=new TextField();
        field_anion.setBounds(400, 350, text_field_width, text_field_height);
        this.add(field_anion);
        
        anion = new Label("anion", CENTER);
        anion.setBounds(100, 350, label_width, label_height);
        anion.setBackground(labelColor);
        this.add(anion);
        
    // solution
        field_solution=new TextField();
        field_solution.setBounds(400, 500, text_field_width, text_field_height);
        field_solution.setEditable(false);
        this.add(field_solution);
        
        solution = new Label("Solution", CENTER);
        solution.setBounds(100, 500, label_width, label_height);
        solution.setBackground(labelColor);
        this.add(solution);
    }
    
    /**
     * An action listener, that checks if any buttons on SFrame are clicked
     * 
     * @param e1 ActionEvent
     */
    public void actionPerformed(ActionEvent e1)
    {
        if (e1.getSource() == calculate)
        {   
            boolean soluble, slightly_soluble;
            
            String c = setCation(field_cation.getText());
            String a = setAnion(field_anion.getText());
            
            soluble = isSoluble(c, a);
            slightly_soluble = isSlightlySoluble(c, a);
            
            if (slightly_soluble==true)
                field_solution.setText("Slightly Soluble");
            
            else if(soluble=true)
                field_solution.setText("Soluble (aq)");
            
            else
                field_solution.setText("Insoluble (s)");
            
            field_cation.setText(c);
            field_anion.setText(a);
        }
        
        if (e1.getSource() == rules)
        {
            BufferedImage img = null;
            
            try
            {
                img = ImageIO.read(new File(
                        "C:/Users/John/Pictures/Solubility_Rules.png"));
            }
            catch (Throwable e)
            {
                Dialog x = new Dialog(this, e.getMessage());
            }
            
            ImageIcon icon=new ImageIcon(img);
            JFrame frame=new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setLocation(2000, 200);
            frame.setSize(1140,680);
            JLabel lbl=new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
        }
    }
    
    /**
     * Gets the information from the Text Field "field_cation". Takes different 
     * forms of inputting the cation (ex: "NA", "Sodium", "NA+") and converts it
     * to its elemental form (ex: "Na"). The elemental form is returned. Case is
     * ignored.
     * 
     * @param c A cation String
     * @return The cation String in elemental form
     */
    public String setCation(String c)
    {
        switch(c.toUpperCase())
        {
        // group 1 ions and NH4
            case "H": case "HYDROGEN": case "H+": c="H"; break;
            
            case "LI": case "LITHIUM": case "LI+": c="Li"; break;
            
            case "NA": case "SODIUM": case "NA+": c="Na"; break;
            
            case "K": case "POTASSIUM": case "K+": c="K"; break;
            
            case "RB": case "RUBIDIUM": case "RB+": c="Rb"; break;
            
            case "CS": case "CAESIUM": case "CS+": c="Cs"; break;
            
            case "FR": case "FRANCIUM": case "FR+": c="Fr"; break;
            
            case "NH4": case "AMMONIUM": case "NH4+": c="NH4"; break;
            
        // group 17 ions (halides) EXCEPTIONS
            case "AG": case "SILVER": case "AG+": c="Ag"; break;
            
            case "CU": case "COPPER": case "CU+": c="Cu"; break;
            
            case "HG": case "MERCURY": case "MERCURY(I)": case "HG+":
            case "HG2": case "HG2+": c="Hg"; break;
            
            case "PB": case "LEAD": case "LEAD(2)": case "PB2+": case "PB+2":
            c="Pb"; break;
            
        //  SO4 EXCEPTIONS
            case "BA": case "BARIUM": case "BA2+": case "BA+2": c="Ba"; break;
            
            case "CA": case "CALCIUM": case "CA2+": case "CA+2": c="Ca"; break;
            
            case "SR": case "STRONTIUM": case "SR2+": case "SR+2": c="Cr";
            break;
        }
        
        return c;
    }
    
    /**
     * Gets the information from the Text Field "field_anion". Takes different 
     * forms of inputting the anion (ex: "NO3", "Nitrate", "NO3-") and converts it
     * to its elemental form (ex: "NO3"). The elemental form is returned. Case 
     * is ignored.
     * 
     * @param a An anion String
     * @return The anion String in elemental form
     */
    public String setAnion(String a)
    {
        switch(a.toUpperCase())
        {
        // NO3 and CH3COO
            case "NO3": case "NITRATE": case "NO3-": a="NO3"; break;
            
            case "CH3COO": case "CH3CO2": case "C2H3OO": case "C2H3O2":
            case "ACETATE": case "CH3COO-": case "CH3CO2-": case "C2H3OO-": 
            case "C2H3O2-": a="CH3COO"; break;
            
        // Group 17 ions (halides)
            case "F": case "FLOURINE": case "F-": a="F"; break;
            
            case "CL": case "CHLORINE": case "CL-": a="Cl"; break;
            
            case "BR": case "BROMINE": case "BR-": a="Br"; break;
            
            case "I": case "IODINE": case "I-": a="I"; break;
            
            case "At": case "ASTATINE": case "AT-": a="At"; break;
            
        // SO4 Sulfates
            case "SO4": case "SULFATE": case "SO42-": case "SO4-2": a="SO4";
            break;
            
        // OH Hydroxides
            case "OH": case "HYDROXIDE": case "OH-": a="OH"; break;
            
        // Carbonate
            case "CO3": case "CARBONATE": case "CO32-": case "CO3-2": a="CO3";
            break;
        
        // Phosphate
            case "PO4": case "PHOSPHATE": case "PO43-": case "PO4-3": a="PO4";
            break;
        
        // Sulfide
            case "S": case "SULFIDE": case "S-": a="S"; break;
        }
        
        return a;
    }
    
    /**
     * Given a cation and an anion, this function determines whether the
     * cation + anion combination is soluble or not. Returns true for soluble
     * and false for insoluble.
     * 
     * @param c The name of a cation
     * @param a The name of an anion
     * @return True if soluble, false if insoluble
     */
    public boolean isSoluble(String c, String a)
    {
        boolean soluble=false;
        
        switch(c)
        {
        // Group 1 ions and NH4
            case "H": case "Li": case "Na": case "K": case "Rb": case "Cs": 
            case "Fr": case "NH4": soluble=true; break;
        }
        
        switch(a)
        {
        // Anions NO3 and CH3COO
            case "NO3": case "CH3COO": soluble=true; break;
            
        // group 17 ions
            case "F": case "Cl": case "Br": case "I": case "At":
                switch(c)
                {
                    case "Ag": case "Cu": case "Hg": case "Pb": soluble=false;
                        break;
                    default: soluble=true;
                }
                
        // SO4
            case "SO4":
                switch(c)
                {
                    case "Ba": case "Ca": case "Hg": case "Pb": case "Sr":
                        soluble=false; break;
                    default: soluble=true;
                }
        }
        
        return soluble;
    }
    
    /**
     * Given a cation and an anion, this function determines whether the
     * cation + anion combination is slightly soluble or not. Returns true for 
     * if it is slightly soluble, and false if it is not slightly soluble.
     * 
     * @param c The name of a cation
     * @param a The name of an anion
     * @return True if soluble, false if insoluble
     */
    public boolean isSlightlySoluble(String c, String a)
    {
        boolean slightly_soluble = false;
        
        switch(a)
        {
            case "OH": 
                switch(c)
                {
                    case "H": case "Li": case "Na": case "K": case "Rb":
                        case "Cs": case "Fr": slightly_soluble=false; break;
                    default: slightly_soluble=true;
                }
                
            case "S":
                switch(c)
                {
                    case "H": case "Li": case "Na": case "K": case "Rb":
                        case "Cs": case "Fr": case "NH4": 
                        slightly_soluble=false; break;
                    default: slightly_soluble=true;
                }
            
            case "CO3":
                switch(c)
                {
                    case "H": case "Li": case "Na": case "K": case "Rb":
                        case "Cs": case "Fr": case "NH4": 
                        slightly_soluble=false; break;
                    default: slightly_soluble=true;
                }
                
            case "PO4":
                switch(c)
                {
                    case "H": case "Li": case "Na": case "K": case "Rb":
                        case "Cs": case "Fr": case "NH4": 
                        slightly_soluble=false; break;
                    default: slightly_soluble=true;
                }
        }
        
        return slightly_soluble;
    }
}
