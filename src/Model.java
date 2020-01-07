import java.util.List;

/**
 * The model class for the application
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

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
        mainWindow.setChannels(channels);
        controller.setChannelListener();
    }

    public int getChannelId(String name) {
        for (Channel channel : channels) {
            if (channel.getName().equals(name)) {
                return channel.getId();
            }
        }
        return -1;
    }

    public int getCurrentChannelId() {
        if (currentChannel != null) {
            return currentChannel.getId();
        }
        return -1;
    }

    public void setCurrentChannel(int id) {
        for (Channel channel : channels) {
            if (channel.getId() == id) {
                currentChannel = channel;
                return;
            }
        }
    }
}
