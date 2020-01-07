import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageRenderer extends Renderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        //JPanel c = (JPanel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JPanel c = new JPanel();
        if (value != null) {
            JLabel imageLabel = new JLabel();
            BufferedImage image = (BufferedImage) value;
            Image i = image.getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_FAST);
            ImageIcon icon = new ImageIcon(i);
            imageLabel.setIcon(icon);
            c.add(imageLabel);
        }
        EpisodeModel episodeModel = (EpisodeModel) table.getModel();
        c.setBackground(episodeModel.getRowColor(row));
        return c;
    }
}
