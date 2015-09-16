package cc.ly.mc.core.message;

/**
 * Created by ly on 9/11/15.
 */
public class Messages {
    public static final int VERSION_FIELD_LENGTH = 1;
    public static final int LENGTH_FIELD_LENGTH = 3;
    public static final int VERSION_LENGTH_FIELD_LENGTH = VERSION_FIELD_LENGTH + LENGTH_FIELD_LENGTH;
    public static final int FLAG_FIELD_LENGTH = 1;
    public static final int CODE_FIELD_LENGTH = 3;
    public static final int HOP_BY_HOP_FIELD_LENGTH = 4;
    public static final int END_TO_END_FIELD_LENGTH = 4;
    public static final int MESSAGE_FIELDS_LENGTH = 16;
}
