package me.soc.baseuid;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BaseUidTest {

  private static final long SECS_IN_YEAR = 31536000L;

  @Test
  public void testCreate() {
    for (int i = 0; i < 1000; i++) {
      System.out.println(BaseUid.create() + " " + i);
    }
  }

  @Test
  public void testNoClash() {
    String base1 = BaseUid.from(Instant.ofEpochSecond(1672531200L));
    String base2 = BaseUid.from(Instant.ofEpochSecond(1672531200L));
    assertNotEquals(base1, base2);
  }

  @Test
  public void testStartsWithDigitIn2020() {
    String base2023 = BaseUid.from(Instant.ofEpochSecond(1577836800L));
    assertTrue(base2023.startsWith("9wgoPrBJ"));
  }

  @Test
  public void testStartsWithLetterIn2021() {
    String base2023 = BaseUid.from(Instant.ofEpochSecond(1609459200L));
    assertTrue(base2023.startsWith("A9j_Dj2s"));
  }

  @Test
  public void testStartsWithLetterIn2022() {
    String base2023 = BaseUid.from(Instant.ofEpochSecond(1640995200L));
    System.out.println(Instant.ofEpochSecond(1640995200L));
    assertTrue(base2023.startsWith("ANjssJky"));
  }

  @Test
  public void testStartsWithLetterIn2023() {
    String base2023 = BaseUid.from(Instant.ofEpochSecond(1672531200L));
    assertTrue(base2023.startsWith("AakBWuT3"));
  }

  @Test
  public void testStartsWithLetterIn2024() {
    String base2024 = BaseUid.from(Instant.ofEpochSecond(1704067200L));
    assertTrue(base2024.startsWith("AokVAVA9"));
  }

  @Test
  public void testStartsWithLetterIn2038() {
    String base2038 = BaseUid.from(Instant.ofEpochSecond(2145916800L));
    assertTrue(base2038.startsWith("DsybusgL"));
  }

  @Test
  public void printExamples() {
    String base2020 = BaseUid.from(Instant.ofEpochSecond(1577836800L));
    String base2023 = BaseUid.from(Instant.ofEpochSecond(1672531200L));
    String base2024 = BaseUid.from(Instant.ofEpochSecond(1704067200L));
    String base2038 = BaseUid.from(Instant.ofEpochSecond(2145916800L));
    String base2040 = BaseUid.from(Instant.ofEpochSecond(2208988800L));
    String base2050 = BaseUid.from(Instant.ofEpochSecond(2524608000L));
    String base2060 = BaseUid.from(Instant.ofEpochSecond(2840140800L));
    String base2070 = BaseUid.from(Instant.ofEpochSecond(3155760000L));
    String base2080 = BaseUid.from(Instant.ofEpochSecond(3471292800L));
    String base2090 = BaseUid.from(Instant.ofEpochSecond(3786912000L));
    String base2100 = BaseUid.from(Instant.ofEpochSecond(4102444800L));
    String base2150 = BaseUid.from(Instant.ofEpochSecond(5680281600L));
    String base2200 = BaseUid.from(Instant.ofEpochSecond(7258118400L));
    System.out.println("2020 " + base2020);
    System.out.println("2023 " + base2023);
    System.out.println("2024 " + base2024);
    System.out.println("2038 " + base2038);
    System.out.println("2040 " + base2040);
    System.out.println("2050 " + base2050);
    System.out.println("2060 " + base2060);
    System.out.println("2070 " + base2070);
    System.out.println("2080 " + base2080);
    System.out.println("2090 " + base2090);
    System.out.println("2100 " + base2100);
    System.out.println("2150 " + base2150);
    System.out.println("2200 " + base2200);
  }

  @Test
  public void printExamplesWithNanos() {
    String base2020 = BaseUid.from(Instant.now().minusSeconds(4 * SECS_IN_YEAR));
    String base2023 = BaseUid.from(Instant.now().minusSeconds(SECS_IN_YEAR));
    String base2024 = BaseUid.from(Instant.now());
    String base2038 = BaseUid.from(Instant.now().plusSeconds(14 * SECS_IN_YEAR));
    String base2040 = BaseUid.from(Instant.now().plusSeconds(16 * SECS_IN_YEAR));
    String base2050 = BaseUid.from(Instant.now().plusSeconds(26 * SECS_IN_YEAR));
    String base2060 = BaseUid.from(Instant.now().plusSeconds(36 * SECS_IN_YEAR));
    String base2070 = BaseUid.from(Instant.now().plusSeconds(46 * SECS_IN_YEAR));
    String base2080 = BaseUid.from(Instant.now().plusSeconds(56 * SECS_IN_YEAR));
    String base2090 = BaseUid.from(Instant.now().plusSeconds(66 * SECS_IN_YEAR));
    String base2100 = BaseUid.from(Instant.now().plusSeconds(76 * SECS_IN_YEAR));
    String base2150 = BaseUid.from(Instant.now().plusSeconds(126 * SECS_IN_YEAR));
    String base2200 = BaseUid.from(Instant.now().plusSeconds(176 * SECS_IN_YEAR));
    String base2250 = BaseUid.from(Instant.now().plusSeconds(226 * SECS_IN_YEAR));
    String base2260 = BaseUid.from(Instant.now().plusSeconds(236 * SECS_IN_YEAR));
    String base2270 = BaseUid.from(Instant.now().plusSeconds(246 * SECS_IN_YEAR));
    String base2300 = BaseUid.from(Instant.now().plusSeconds(276 * SECS_IN_YEAR));
    System.out.println("~2020 " + base2020);
    System.out.println("~2023 " + base2023);
    System.out.println("~2024 " + base2024);
    System.out.println("~2038 " + base2038);
    System.out.println("~2040 " + base2040);
    System.out.println("~2050 " + base2050);
    System.out.println("~2060 " + base2060);
    System.out.println("~2070 " + base2070);
    System.out.println("~2080 " + base2080);
    System.out.println("~2090 " + base2090);
    System.out.println("~2100 " + base2100);
    System.out.println("~2150 " + base2150);
    System.out.println("~2200 " + base2200);
    System.out.println("~2250 " + base2250);
    System.out.println("~2260 " + base2260);
    System.out.println("~2270 " + base2270);
    System.out.println("~2300 " + base2300);
  }

}
