package tests.window.components;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import window.components.GUI;


public class GUITests {
	
	@Test
	public void TestName(){
		GUI gui = new GUI();
		assertEquals(gui.strServerName, gui.strServerNameC);
	}
	
	@Test
	public void TestName1(){
		GUI gui = new GUI();
		assertEquals(gui.strPortNum, gui.strPortNumC);
	}
	

}
