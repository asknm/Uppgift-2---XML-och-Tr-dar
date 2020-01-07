import javax.swing.*;
import java.time.ZonedDateTime;

/**
 * The controller for the application
 */
public class Controller {

    private MainWindow mainWindow = new MainWindow();
    private Model model = new Model(mainWindow, this);
    private Timer timer;

    public Controller() {
        mainWindow.addUpdateMenuItemListener(actionEvent -> update());
        mainWindow.addDescriptionListener(actionEvent -> toggleDescription(actionEvent.getSource()));
        mainWindow.addImageListener(actionEvent -> toggleImage(actionEvent.getSource()));
        ChannelWorker channelWorker = new ChannelWorker(mainWindow, model);
        channelWorker.execute();
    }

    /**
     * Starts updating the schedule for a specific channel
     * @param source the menu item that was clicked. Used to determine which channel should be viewed
     */
    private void update(Object source) {
        UpdateWorker updateWorker = new UpdateWorker(mainWindow, model, source);
        updateWorker.execute();
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(60*60*1000, actionEvent -> update());
        timer.start();
    }

    /**
     * Starts updating the schedule for the current channel
     */
    private void update() {
        System.out.println(ZonedDateTime.now());
        UpdateWorker updateWorker = new UpdateWorker(mainWindow, model);
        updateWorker.execute();
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(60*60*1000, actionEvent -> update());
        timer.start();
    }

    /**
     * Sets the action listener for the channel menu items
     */
    public void setChannelListener() {
        mainWindow.setChannelListener(actionEvent -> update(actionEvent.getSource()));
    }

    private void toggleDescription(Object source) {
        JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) source;
        mainWindow.getEpisodeModel().toggleDescription(checkBoxMenuItem.getState());
    }

    private void toggleImage(Object source) {
        JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) source;
        mainWindow.getEpisodeModel().toggleImage(checkBoxMenuItem.getState());
    }

}
