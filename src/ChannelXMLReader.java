import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChannelXMLReader {

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

}
