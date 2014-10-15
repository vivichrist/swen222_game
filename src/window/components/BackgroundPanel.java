package window.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BackgroundPanel extends Panel{

	public BackgroundPanel(GUI gui) {
		super(gui);
		setBounds(0, 0, 800, 770);
	}

	@Override
	protected void setUpComponents() {
		ImageIcon background = new ImageIcon("Resource/bg1.png");
		JLabel jl = new JLabel(background);
		add(jl);
	}

	@Override
	protected void addListenner() {
	}

}
