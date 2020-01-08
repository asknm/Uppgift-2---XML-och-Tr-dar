import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TableMouseListener implements MouseListener {

    private EpisodeModel episodeModel;
    private JTable table;

    public TableMouseListener(EpisodeModel episodeModel, JTable table) {
        this.episodeModel = episodeModel;
        this.table = table;
    }

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
