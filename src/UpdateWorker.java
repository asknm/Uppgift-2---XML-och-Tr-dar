import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A SwingWorker that handles loading or updating the schedule of a channel
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class UpdateWorker extends SwingWorker<List<Episode>, Object> {

    private MainWindow mainWindow;
    private Model model;
    private int id = -1;

    public UpdateWorker(MainWindow mainWindow, Model model, Object source) {
        this.mainWindow = mainWindow;
        this.model = model;
        JMenuItem menuItem = (JMenuItem) source;
        id = model.getChannelId(menuItem.getText());
        model.setCurrentChannel(id);
    }

    public UpdateWorker(MainWindow mainWindow, Model model) {
        this.mainWindow = mainWindow;
        this.model = model;
        id = model.getCurrentChannelId();
    }

    /**
     * Gets episodes on a separate thread
     * @return a list of episodes
     */
    @Override
    protected List<Episode> doInBackground() {
        EpisodeXMLReader xmlReader = new EpisodeXMLReader(id);
        if (id != -1) {
            return xmlReader.getEpisodes();
        }
        return new ArrayList<>();
    }

    /**
     * Gets the loaded episodes and sets them to the EpisodeModel used to hold the data for the table when the
     * SwingWorker is done executing.
     */
    @Override
    protected void done() {
        try {
            mainWindow.getEpisodeModel().setEpisodeList(get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
