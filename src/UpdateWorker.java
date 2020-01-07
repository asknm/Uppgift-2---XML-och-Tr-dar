import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @Override
    protected List<Episode> doInBackground() {
        XMLReader xmlReader = new XMLReader();
        if (id != -1) {
            return xmlReader.getEpisodes(id);
        }
        return new ArrayList<>();
    }

    @Override
    protected void done() {
        try {
            mainWindow.getEpisodeModel().setEpisodeList(get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
