package botapi.entity.utilEntity;

public class IMessageEntity {
    private String type;
    private int offset;
    private int length;

    public IMessageEntity(String type, int offset, int length) {
        this.type = type;
        this.offset = offset;
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
}
