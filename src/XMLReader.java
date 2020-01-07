import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class XMLReader {

    public List<Channel> getChannels() {
        String url = "http://api.sr.se/api/v2/channels/";
        return getChannels(url);
    }

    private List<Channel> getChannels(String url) {
        List<Channel> channels = new ArrayList<>();
        Element root;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
            root = doc.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return channels;
        }
        String nextPageUrl;
        try {
            nextPageUrl = root.getElementsByTagName("nextpage").item(0).getTextContent();
        } catch (NullPointerException e) {
            nextPageUrl = null;
        }
        Element channelsNode = (Element) root.getElementsByTagName("channels").item(0);
        NodeList channelNodes = channelsNode.getElementsByTagName("channel");
        for (int i = 0; i < channelNodes.getLength(); i++) {
            Node channelNode = channelNodes.item(i);
            int id = Integer.parseInt(channelNode.getAttributes().getNamedItem("id").getTextContent());
            String name = channelNode.getAttributes().getNamedItem("name").getTextContent();
            Channel channel = new Channel(id, name);
            channels.add(channel);
        }
        if (nextPageUrl != null) {
            channels.addAll(getChannels(nextPageUrl));
        }
        return channels;
    }

    public List<Episode> getEpisodes(int channelId) {
        String url = "http://api.sr.se/api/v2/scheduledepisodes?channelid=" + channelId;
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
        for (int i = 0; i < episodeNodes.getLength(); i++) {
            Element episodeElement = (Element) episodeNodes.item(i);
            String title = episodeElement.getElementsByTagName("title").item(0).getTextContent();
            String description = episodeElement.getElementsByTagName("description").item(0).getTextContent();
            String startTimeStr = episodeElement.getElementsByTagName("starttimeutc").item(0).getTextContent();
            ZonedDateTime startTime = ZonedDateTime.parse(startTimeStr, ISO_DATE_TIME);
            String endTimeStr = episodeElement.getElementsByTagName("endtimeutc").item(0).getTextContent();
            ZonedDateTime endTime = ZonedDateTime.parse(endTimeStr, ISO_DATE_TIME);
            BufferedImage image = null;
            try {
                String imageUrl = episodeElement.getElementsByTagName("imageurl").item(0).getTextContent();
                image = ImageIO.read(new URL(imageUrl));
            } catch (NullPointerException | IOException e) {
                //e.printStackTrace();
            }
            Episode episode = new Episode(title, startTime, endTime, description, image);
            if (Duration.between(endTime, ZonedDateTime.now()).toHours() < 12) {
                if (Duration.between(ZonedDateTime.now(), startTime).toHours() < 12) {
                    episodes.add(episode);
                }
                else {
                    done = true;
                }
            }
        }
        if (!done && nextPageUrl != null) {
            episodes.addAll(getEpisodes(nextPageUrl));
        }
        return episodes;
    }

}
