package me.soc.baseuid;

final class Base64Lex {

  private static final char[] CHARS = {
      '-',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      '_',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
      'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
  };

  static String encode(byte[] src) {
    var buffer = new StringBuilder(20);
    for (int i = 0; i < 15;) {
      int bits = (src[i++] & 0xFF) << 16 | (src[i++] & 0xFF) <<  8 | (src[i++] & 0xFF);
      buffer.append(CHARS[(bits >>> 18) & 0b111111]);
      buffer.append(CHARS[(bits >>> 12) & 0b111111]);
      buffer.append(CHARS[(bits >>> 6)  & 0b111111]);
      buffer.append(CHARS[bits & 0b111111]);
    }
    return buffer.toString();
  }

}
