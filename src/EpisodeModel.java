import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage the table data
 */
public class EpisodeModel extends AbstractTableModel {

    private List<Episode> episodeList = new ArrayList<>();
    private List<String> columnNames = new ArrayList<>();

    public EpisodeModel() {
        columnNames.add("Title");
        columnNames.add("Start");
        columnNames.add("End");
    }

    /**
     * Gets the row count
     * @return the row count
     */
    @Override
    public int getRowCount() {
        return episodeList.size();
    }

    /**
     * Gets the column count
     * @return the column count
     */
    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    /**
     * Gets the name of a given column
     * @param col column number to get the name of
     * @return the name of the column
     */
    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    /**
     * Gets the value at a given row and column
     * @param rowIndex the row
     * @param columnIndex the column
     * @return the value
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Episode episode = episodeList.get(rowIndex);
        switch (getColumnName(columnIndex)) {
            case "Title":
                return episode.getTitle();
            case "Start":
                return String.valueOf(episode.getStartTime());
            case "End":
                return String.valueOf(episode.getEndTime());
            case "Description":
                return episode.getDescription();
            case "Image":
                return episode.getImage();
        }
        return null;
    }

    /**
     * Gets the class of a given column.
     * @param columnIndex the column number
     * @return the class
     */
    public Class<?> getColumnClass(int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Image":
                return BufferedImage.class;
            default:
                return String.class;
        }
    }

    /**
     * Sets the episodeList which is the data of the table
     * @param episodeList the list of Episodes to use as table data
     */
    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
        fireTableDataChanged();
    }

    /**
     * Returns the color to paint a row with
     * @param row the row
     * @return the color. Red if the episode has already aired, green otherwise.
     */
    public Color getRowColor(int row) {
        if (episodeList.get(row).getEndTime().isBefore(ZonedDateTime.now())) {
            return Color.RED;
        }
        else {
            return Color.GREEN;
        }
    }

    public void toggleDescription(boolean state) {
        columnNames.remove("Description");
        if (state) {
            columnNames.add("Description");
        }
        fireTableStructureChanged();
    }

    public void toggleImage(boolean state) {
        columnNames.remove("Image");
        if (state) {
            columnNames.add("Image");
        }
        fireTableStructureChanged();
    }
}
