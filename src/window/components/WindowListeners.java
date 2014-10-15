package window.components;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import ServerClients.Client;

public class WindowListeners implements WindowListener {

    private GUI gui;
	private Client client;

    public WindowListeners(GUI gui) {
    	this.gui =gui;
        gui.addWindowListener(this);
    }

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    }

    @Override
    public void windowClosing(WindowEvent event) {
        ServerClients.UDPpackets.Packet01Disconnect packet = new ServerClients.UDPpackets.Packet01Disconnect(this.client.name);
        packet.writeData(client);
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
    }

	public void setClient(Client client) {
		// TODO Auto-generated method stub
		this.client = client;
	}

}
