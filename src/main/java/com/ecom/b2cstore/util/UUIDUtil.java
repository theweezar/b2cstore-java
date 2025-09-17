package com.ecom.b2cstore.util;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.UUID;

public class UUIDUtil {
    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static UUID generateNameUUIDFromCurrentTime() {
        long currentTimeMillis = Instant.now().toEpochMilli();
        long randomLong = UUID.randomUUID().getMostSignificantBits();
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 2)
                .putLong(currentTimeMillis)
                .putLong(randomLong);
        return UUID.nameUUIDFromBytes(buffer.array());
    }
}
