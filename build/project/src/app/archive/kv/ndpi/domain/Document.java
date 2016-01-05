package app.archive.kv.ndpi.domain;

/**
 * Created by konstr on 22.12.2015.
 */
public class Document {
    private int id;
    private String name;
    private String type;
    private String kind;
    private Placing place;

    public Document(){}

    public Document(String name, String type, String kind, Placing place) {
        this.name=name;
        this.type=type;
        this.kind=kind;
        this.place=place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Placing getPlace() {
        return place;
    }

    public void setPlace(Placing place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;

        Document document = (Document) o;

        if (id != document.id) return false;
        if (kind != null ? !kind.equals(document.kind) : document.kind != null) return false;
        if (name != null ? !name.equals(document.name) : document.name != null) return false;
        if (place != null ? !place.equals(document.place) : document.place != null) return false;
        if (type != null ? !type.equals(document.type) : document.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id= \t" + id + "\t" + type + "\t" + kind + "\t" + name +
                "\t" + place.getRoom() + "\t Level - " + place.getLevel() +
                "\t Number - " + place.getNumber();

    }
}
