package gui;

import java.awt.*;
import javax.swing.*;
public class MyButton extends JButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyButton(String pic1,String pic2){
		Dimension d = new Dimension(200,120);
		this.setSize(d);
	    this.setMaximumSize(d);
	    this.setMinimumSize(d);
	    ImageIcon button1 = new ImageIcon(Gui.class.getResource(pic1));
	    ImageIcon button2 = new ImageIcon(Gui.class.getResource(pic2));
	    setIcon(button1);
	    setRolloverIcon(button2);
	    setBorderPainted(false);
	    setFocusPainted(false);
	    setContentAreaFilled(false);
	    setFocusable(true);
	    setMargin(new Insets(0, 0, 0, 0));
	}
}
