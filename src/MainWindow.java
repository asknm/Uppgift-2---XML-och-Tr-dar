import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * The main window for the application
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class MainWindow extends JFrame {

    private JMenu channelMenu = new JMenu("Channels");
    private JMenuItem updateMenuItem = new JMenuItem("Update");
    private EpisodeModel episodeModel = new EpisodeModel();

    public MainWindow() {
        super("RadioInfo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(channelMenu);

        JMenu updateMenu = new JMenu("Update");
        updateMenu.add(updateMenuItem);
        menuBar.add(updateMenu);

        setJMenuBar(menuBar);

        JTable table = new JTable(episodeModel);
        table.addMouseListener(new TableMouseListener(episodeModel, table));
        table.setDefaultRenderer(String.class, new StringRenderer());
        table.setDefaultRenderer(ZonedDateTime.class, new ZonedDateTimeRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    /**
     * Adds an action listener to the update MenuItem
     * @param listener the ActionListener
     */
    public void addUpdateMenuItemListener(ActionListener listener) {
        updateMenuItem.addActionListener(listener);
    }

    /**
     * Sets an action listener for each of the channels's menu items
     * @param listener the ActionListener
     */
    public void setChannelListener(ActionListener listener) {
        for (int i=0; i<channelMenu.getItemCount(); i++) {
            JMenu pageMenu = (JMenu) channelMenu.getItem(i);
            for (int j =0; j<pageMenu.getItemCount(); j++) {
                ActionListener[] actionListeners = pageMenu.getItem(j).getActionListeners();
                for (ActionListener actionListener : actionListeners) {
                    pageMenu.getItem(j).removeActionListener(actionListener);
                }
                pageMenu.getItem(j).addActionListener(listener);
            }
        }
    }

    /**
     * Sets the channels that will be available to pick from
     * @param channelList a list of channels
     */
    public void setChannels(List<Channel> channelList) {
        channelMenu.removeAll();
        ButtonGroup group = new ButtonGroup();
        int pageNr = 1;
        JMenu pageMenu = new JMenu("Page " + pageNr);
        channelMenu.add(pageMenu);
        for (Channel channel : channelList) {
            if (pageMenu.getItemCount() > 9) {
                pageNr++;
                pageMenu = new JMenu("Page " + pageNr);
                channelMenu.add(pageMenu);
            }
            JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem(channel.getName());
            group.add(rbMenuItem);
            pageMenu.add(rbMenuItem);
        }
    }

    /**
     * Getter for the EpisodeModel
     * @return the EpisodeModel
     */
    public EpisodeModel getEpisodeModel() {
        return episodeModel;
    }
}
