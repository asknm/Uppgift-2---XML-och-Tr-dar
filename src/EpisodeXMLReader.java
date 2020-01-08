import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class EpisodeXMLReader {

    private int channelId;
    private static final ZoneId stockholmZone = ZoneId.of("Europe/Stockholm");

    public EpisodeXMLReader(int channelId) {
        this.channelId = channelId;
    }

    public List<Episode> getEpisodes() {
        String url = "http://api.sr.se/api/v2/scheduledepisodes?channelid=" + channelId;
        return getEpisodes(url);
    }

    public List<Episode> getEpisodes(ZonedDateTime zonedDateTime) {
        ZonedDateTime dayAfter = zonedDateTime.plusDays(1);
        String dateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dayAfter);
        String url = "http://api.sr.se/api/v2/scheduledepisodes?channelid=" + channelId + "&date=" + dateStr;
        return getEpisodes(url);
    }

    private List<Episode> getEpisodes(String url) {
        List<Episode> episodes = new ArrayList<>();

        Element root;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
            root = doc.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return episodes;
        }
        String nextPageUrl;
        try {
            nextPageUrl = root.getElementsByTagName("nextpage").item(0).getTextContent();
        } catch (NullPointerException e) {
            nextPageUrl = null;
        }
        Element schedule = (Element) root.getElementsByTagName("schedule").item(0);
        NodeList episodeNodes = schedule.getElementsByTagName("scheduledepisode");
        boolean done = false;
        ZonedDateTime latestStartTime = null;
        for (int i = 0; i < episodeNodes.getLength(); i++) {
            Element episodeElement = (Element) episodeNodes.item(i);
            String endTimeStr = episodeElement.getElementsByTagName("endtimeutc").item(0).getTextContent();
            ZonedDateTime endTime = ZonedDateTime.parse(endTimeStr, ISO_DATE_TIME).withZoneSameInstant(stockholmZone);
            if (Duration.between(endTime, ZonedDateTime.now()).toHours() < 12) {
                String startTimeStr = episodeElement.getElementsByTagName("starttimeutc").item(0).getTextContent();
                ZonedDateTime startTime = ZonedDateTime.parse(startTimeStr, ISO_DATE_TIME).withZoneSameInstant(stockholmZone);
                latestStartTime = startTime;
                if (Duration.between(ZonedDateTime.now(), startTime).toHours() < 12) {
                    String title = episodeElement.getElementsByTagName("title").item(0).getTextContent();
                    String description = episodeElement.getElementsByTagName("description").item(0).getTextContent();
                    String imageUrl;
                    try {
                        imageUrl = episodeElement.getElementsByTagName("imageurl").item(0).getTextContent();
                    } catch (NullPointerException e) {
                        imageUrl = null;
                    }
                    Episode episode = new Episode(title, startTime, endTime, description, imageUrl);
                    episodes.add(episode);
                }
                else {
                    done = true;
                    break;
                }
            }
        }
        if (!done) {
            if (nextPageUrl != null) {
                episodes.addAll(getEpisodes(nextPageUrl));
            }
            else if (latestStartTime != null) {
                episodes.addAll(getEpisodes(latestStartTime));
            }
        }
        return episodes;
    }

}
