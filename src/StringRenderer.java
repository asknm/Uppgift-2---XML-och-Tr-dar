import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Used to render a cell with a string with the correct background color
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class StringRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        EpisodeModel episodeModel = (EpisodeModel) table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(episodeModel.getRowColor(row));
        return c;
    }
}
