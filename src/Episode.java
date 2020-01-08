import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;

/**
 * Holds the information for an episode in a channels schedule
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class Episode {

    private String title;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String description;
    private String imageUrl;
    private ImageIcon imageIcon;

    public Episode(String title, ZonedDateTime startTime, ZonedDateTime endTime, String description, String imageUrl) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    /**
     * Getter for the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the start time
     * @return the start time
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for the end time
     * @return the end time
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Getter for the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the imageIcon or loads it if it has not yet been loaded
     * @return the imageIcon
     */
    public ImageIcon getImageIcon() {
        if (imageIcon == null) {
            try {
                URL url = new URL(imageUrl);
                BufferedImage c = ImageIO.read(url);
                imageIcon = new ImageIcon(c);
            } catch (IOException e) {
                return null;
            }
        }
        return imageIcon;
    }
}
