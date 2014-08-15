/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section.LayerMaskInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class LayerBlendingRanges {

    private final int length;
    
    LayerBlendingRanges(DataInputStream in) throws IOException {
        length = in.readInt();        
        in.skipBytes(length);
    }

    @Override
    public String toString() {
        return "LayerBlendingRanges{" + "length=" + length + '}';
    }    
}
