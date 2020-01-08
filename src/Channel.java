/**
 * A class to hold the information of a radio channel loaded from the API
 *
 * @author ens19amn - Ask Norheim Morken
 */
public class Channel {

    private int id;
    private String name;

    public Channel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for the id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the name
     * @return the name
     */
    public String getName() {
        return name;
    }
}
