package app.archive.kv.ndpi.domain;


public class Placing {

    private int id;
    private String room;
    private String level;
    private String number;

    public Placing(){}

    public Placing(String room, String level, String number) {
        this.room=room;
        this.level=level;
        this.number=number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Placing)) return false;

        Placing placing = (Placing) o;

        if (id != placing.id) return false;
        if (level != placing.level) return false;
        if (number != placing.number) return false;
        if (room != null ? !room.equals(placing.room) : placing.room != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (room != null ? room.hashCode() : 0);        
        return result;
    }
}