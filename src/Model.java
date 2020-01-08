import java.util.List;

/**
 * The model class for the application holding the available channels
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class Model {

    private MainWindow mainWindow;
    private Controller controller;

    private List<Channel> channels;
    private Channel currentChannel;

    public Model(MainWindow mainWindow, Controller controller) {
        this.mainWindow = mainWindow;
        this.controller = controller;
    }

    /**
     * Sets the list of channels
     * @param channels the list of channels
     */
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
        mainWindow.setChannels(channels);
        controller.setChannelListener();
    }

    /**
     * Gets the id of a channel with a given name
     * @param name the name
     * @return the id of the channel
     */
    public int getChannelId(String name) {
        for (Channel channel : channels) {
            if (channel.getName().equals(name)) {
                return channel.getId();
            }
        }
        return -1;
    }

    /**
     * Gets the id of the currently selected channel
     * @return the id
     */
    public int getCurrentChannelId() {
        if (currentChannel != null) {
            return currentChannel.getId();
        }
        return -1;
    }

    /**
     * Sets the current channel to a channel with a specific id
     * @param id the id
     */
    public void setCurrentChannel(int id) {
        for (Channel channel : channels) {
            if (channel.getId() == id) {
                currentChannel = channel;
                return;
            }
        }
    }
}
