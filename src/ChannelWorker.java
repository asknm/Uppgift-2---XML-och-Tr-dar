import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChannelWorker extends SwingWorker<List<Channel>, Object> {

    private MainWindow mainWindow;
    private Model model;

    public ChannelWorker(MainWindow mainWindow, Model model) {
        this.mainWindow = mainWindow;
        this.model = model;
    }

    @Override
    protected List<Channel> doInBackground() {
        ChannelXMLReader xmlReader = new ChannelXMLReader();
        return xmlReader.getChannels();
    }

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
