package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Player;
import paulmodz.javabs.messages.Packet;
import paulmodz.util.Helpers;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.zip.DeflaterOutputStream;

public class SectorState extends Packet
{
  Player own;
  int location;
  
  public SectorState(Player own, int location)
  {
    id = 21903;
    this.own = own;
    this.location = location;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(new DeflaterOutputStream(b));
    
    d.write(Helpers.parseHexBinary("00217F0B0066CB9A1ECAAABBA8047F7F7F7F0000000000007F3600A41A00000000000000000700000000000000000000070109000100000008444353205465616D100001000000000000000000"));
    
    own.encodeEntry(d);
    
    d.write(Helpers.parseHexBinary("000000000000000000"));
    d.write(new byte[] { 15, (byte)location, 18, 40, 54 });
    d.write(127);
    d.write(127);
    d.write(0);
    d.write(encodeVInt(own.id));
    d.write(Helpers.parseHexBinary("00000000000000000000000100008E02F27D00000606230123012301230123002300010001000001050005010502050305040505030DA4E2019C8E030000C07C00A40100000000020000000000060DAC36A4650000800400A40100000000010000000000030DAC369C8E030000C07C00A40100000000010000000000060DA4E201A4650000800400A40100000000020000000000060DA88C01B82E0000800400A401000000000000000000000A04057D047D0407000104007F7F000000000000000500000000007F7F7F7F7F7F7F7F030DA88C0188C5030000C07C00A401000000000000000000000A04047C077A0402050306007F7F000000000000000500000000007F7F7F7F7F7F7F7F000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000961B8822961B88228837982D00000000000000000000A401A40100000000000000000000A401A40100000000000000000000A401A40100000000000000000000A401A40100000000000000000000A401A40100000000000000000000A401A401"));
    d.write(Helpers.parseHexBinary("FF011C07081A150A1A120A1A130C1C010C1A0F081A0C081A0A0C00"));
    d.writeShort(65027);
    for (int i = 0; i < own.deck.length; i++) {
      d.write(own.deck[i][0]);
      d.write(own.deck[i][1]);
      d.write(own.deck[i][2]);
    }
    d.write(Helpers.parseHexBinary("00000504050502050105030205000502000000000000000688010801000000010100000000000002000000000300000000040000000101000001000000020000000003000000000400000C000000EAFAF1D908000000000000000000000000000000000000000000000000000000000000000000000000000000000000E2"));
    d.close();
    
    ByteArrayOutputStream end = new ByteArrayOutputStream();
    end.write(new byte[] { 1, -91, 3 });
    end.write(b.toByteArray());
    
    data = end.toByteArray();
  }
}