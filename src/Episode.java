import java.awt.image.BufferedImage;
import java.time.ZonedDateTime;

public class Episode {

    private String title;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String description;
    private BufferedImage image;

    public Episode(String title, ZonedDateTime startTime, ZonedDateTime endTime, String description, BufferedImage image) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.image = image;
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

    public BufferedImage getImage() {
        return image;
    }
}
