import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * A window to display more information about an episode
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class EpisodeView extends JFrame {

    private static final String dateTimeFormat = "dd/MM - HH:mm:ss";

    public EpisodeView(Episode episode) {
        super(episode.getTitle());

        JPanel westPanel = new JPanel();
        westPanel.setMaximumSize(new Dimension(512, 512));
        getContentPane().add(BorderLayout.WEST, westPanel);
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("<html><br>" + episode.getTitle() + "</html>");
        westPanel.add(title);
        JLabel description = new JLabel("<html><br>" + episode.getDescription() + "</html>");
        westPanel.add(description);
        JLabel start =
                new JLabel( "<html><br>Start time: " + DateTimeFormatter.ofPattern(dateTimeFormat).format(episode.getStartTime()) +
                        "</html>");
        westPanel.add(start);
        JLabel end =
                new JLabel("<html><br>End time: " + DateTimeFormatter.ofPattern(dateTimeFormat).format(episode.getEndTime()) +
                        "</html>");
        westPanel.add(end);

        JPanel eastPanel = new JPanel();
        getContentPane().add(BorderLayout.EAST, eastPanel);
        ImageIcon imageIcon = episode.getImageIcon();
        if (imageIcon != null) {
            westPanel.setPreferredSize(new Dimension(512, 512));
            eastPanel.setPreferredSize(new Dimension(512, 512));
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(imageIcon);
            eastPanel.add(imageLabel);
        }
        pack();
    }
}
