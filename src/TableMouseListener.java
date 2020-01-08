import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A mouse listener that displays more information about an episode in a separate view
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class TableMouseListener implements MouseListener {

    private EpisodeModel episodeModel;
    private JTable table;

    public TableMouseListener(EpisodeModel episodeModel, JTable table) {
        this.episodeModel = episodeModel;
        this.table = table;
    }

    /**
     * Displays more information about the episode who's row was clicked
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Episode episode = episodeModel.getEpisode(table.getSelectedRow());
        EpisodeView episodeView = new EpisodeView(episode);
        episodeView.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
