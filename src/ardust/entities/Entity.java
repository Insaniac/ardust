package ardust.entities;

import ardust.shared.Constants;
import ardust.shared.Point3;
import ardust.shared.Values;

import java.nio.ByteBuffer;

public class Entity {
    public enum Kind {
        DWARF
    }

    public enum Orientation {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public enum Mode {
        IDLE,
        WALKING
    }

    public Integer id;
    public Point3 position = new Point3();
    public Kind kind = Kind.DWARF;
    public Orientation orientation = Orientation.SOUTH;
    public Mode mode = Mode.IDLE;
    Values values;

    public Entity(Integer id) {
        this.id = id;
        values = new Values(Constants.V_ENTITY_VALUES_SIZE);
    }

    public Entity(Kind kind, int x, int y, int z) {
        values = new Values(Constants.V_ENTITY_VALUES_SIZE);
        this.kind = kind;
        position.set(x, y, z);
    }

    public boolean write(ByteBuffer buffer, boolean all) {
        values.set(Constants.V_ENTITY_POS_X, position.x);
        values.set(Constants.V_ENTITY_POS_Y, position.y);
        values.set(Constants.V_ENTITY_POS_Z, position.z);
        values.set(Constants.V_ENTITY_KIND, kind.ordinal());
        values.set(Constants.V_ENTITY_ORIENTATION, orientation.ordinal());
        values.set(Constants.V_ENTITY_MODE, mode.ordinal());

        return values.write(buffer, all);
    }

    public void read(ByteBuffer buffer) {
        values.read(buffer);
        position.set(values.get(Constants.V_ENTITY_POS_X), values.get(Constants.V_ENTITY_POS_Y), values.get(Constants.V_ENTITY_POS_Z));
        kind = Kind.values()[values.get(Constants.V_ENTITY_KIND)];
        orientation = Orientation.values()[values.get(Constants.V_ENTITY_ORIENTATION)];
        mode = Mode.values()[values.get(Constants.V_ENTITY_MODE)];
    }

    public void postWrite() {
        values.nextTick();
    }

}