import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;

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

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

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
