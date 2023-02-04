package me.soc.baseuid;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A *BaseUid* consist of two parts:
 *
 * 1. 48bits of POSIX time in nanoseconds, specifically bits 63 to 15
 * 2. 72bits of randomness
 *
 * This selection ensures that the resulting Base64-encoded string starts with a letter until 2260 and allows the use of
 * such uids in situations that do not allow a value that starts with a digit (such as HTML attributes).
 *
 * These two parts are concatenated, and the combined 120bit ...
 *
 *          8      16      24      32      40      48      56      64      72      80      88      96      104     112     120
 * ┏━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┓
 * ┃               time (00-47)                    ┆                               rnd (48-119)                            ┃
 * ┗━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┛
 *
 * ... are then converted to an ASCII string with a modified Base64 encoding, resulting in 20 characters.
 *
 * ### Conversion to UUIDv8 Format
 *
 * BaseUids can easily be converted into UUIDv8 format if required:
 *
 *          8      16      24      32      40      48      56      64      72      80      88      96      104     112     120     128
 * ┏━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┳━┯━┯━┯━┓
 * ┃               time (00-47)                    ┆VER┆rnd (52-63)┆V┆             rnd (66-125)                                  ┆Z┃
 * ┗━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┻━┷━┷━┷━┛
 *                                                  ^               ^                                                             ^
 *                                                  |               |                                                             |
 *                                               ┌VER (constant)┐  ┌VAR (constant)┐                                   ┌Z (constant)┐
 *                                               │ 1 0 0 0      │  │ 1 0          │                                   │ 0 0        │
 *                                               └──────────────┘  └──────────────┘                                   └────────────┘
 */
public final class BaseUid {

  private static final AtomicLong PREVIOUS_TIME = new AtomicLong();

  private static final SecureRandom RANDOM;
  static {
    try {
      RANDOM = SecureRandom.getInstanceStrong();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public static String create() {
    return from(Instant.now());
  }

  public static String from(Instant instant) {
    // time in nanoseconds
    long time0 = toNanos(instant);
    PREVIOUS_TIME.compareAndExchange(time0, time0 + 0b1000000000000000);
    time0 = Math.max(time0, PREVIOUS_TIME.get());
    PREVIOUS_TIME.set(time0);

    int rand1 = RANDOM.nextInt();
    int rand2 = RANDOM.nextInt();
    int rand3 = RANDOM.nextInt();

    byte[] buffer = new byte[15];
    buffer[0] = (byte) (time0 >>> 55);
    buffer[1] = (byte) (time0 >>> 47);
    buffer[2] = (byte) (time0 >>> 39);
    buffer[3] = (byte) (time0 >>> 31);
    buffer[4] = (byte) (time0 >>> 23);
    buffer[5] = (byte) (time0 >>> 15);
    buffer[6] = (byte) (rand1);
    buffer[7] = (byte) (rand2 >>> 24);
    buffer[8] = (byte) (rand2 >>> 16);
    buffer[9] = (byte) (rand2 >>> 8);
    buffer[10] = (byte) (rand2);
    buffer[11] = (byte) (rand3 >>> 24);
    buffer[12] = (byte) (rand3 >>> 16);
    buffer[13] = (byte) (rand3 >>> 8);
    buffer[14] = (byte) (rand3);

    return Base64Lex.encode(buffer);
  }

  private static long toNanos(Instant instant) {
    return instant.getEpochSecond() * 1_000_000_000L + (long) instant.getNano();
  }

}
