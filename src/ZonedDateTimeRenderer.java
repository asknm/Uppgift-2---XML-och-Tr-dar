import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeRenderer implements TableCellRenderer {

    private static final String dateTimeFormat = "dd/MM - HH:mm:ss";

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        EpisodeModel episodeModel = (EpisodeModel) table.getModel();
        ZonedDateTime zonedDateTime = (ZonedDateTime) value;
        JLabel label = new JLabel(DateTimeFormatter.ofPattern(dateTimeFormat).format(zonedDateTime));
        label.setBackground(episodeModel.getRowColor(row));
        label.setOpaque(true);
        return label;
    }
}
