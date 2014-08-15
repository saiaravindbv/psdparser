/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section.LayerMaskInfo;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author aravind
 */
public class AdditionalLayerInfo {

    private final String signature;
    private final String key;
    private final int length;
    
    AdditionalLayerInfo(DataInputStream in) throws IOException {
        byte[] data = new byte[4];
        in.read(data);
        signature = new String(data);
        in.read(data);
        key = new String(data);
        length = in.readInt();
        in.skipBytes(length);
    }
    
}
