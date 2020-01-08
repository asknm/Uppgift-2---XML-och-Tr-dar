import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A SwingWorker that handles the process of loading radio channels from the API using the ChannelXMLReader class.
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class ChannelWorker extends SwingWorker<List<Channel>, Object> {

    private MainWindow mainWindow;
    private Model model;

    public ChannelWorker(MainWindow mainWindow, Model model) {
        this.mainWindow = mainWindow;
        this.model = model;
    }

    /**
     * Gets the available channels on a separate thread
     * @return a list of channels
     */
    @Override
    protected List<Channel> doInBackground() {
        ChannelXMLReader xmlReader = new ChannelXMLReader();
        return xmlReader.getChannels();
    }

    /**
     * Gets the loaded channels and stores them in Model when the SwingWorker is done executing.
     */
    @Override
    protected void done() {
        try {
            List<Channel> channels = get();
            model.setChannels(channels);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
