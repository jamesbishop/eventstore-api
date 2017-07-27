package uk.co.blackcell.eventsourcing;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringUtils {
    public static <T> String toString(T command) {
        String simpleName = command.getClass().getSimpleName();
        Map<String, Object> values = new HashMap<String, Object>();
        for (Field field : command.getClass().getDeclaredFields()) {
            if (!field.getName().equals("uncommittedEvents")) {
                try {
                    field.setAccessible(true);
                    Object object = field.get(command);
                    if (object instanceof UUID) {
                        object = ((UUID) object).getLeastSignificantBits() % 255;
                    }
                    values.put(field.getName(), object);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return simpleName + " " + values;
    }
}
