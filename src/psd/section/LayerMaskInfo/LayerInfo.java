/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section.LayerMaskInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LayerInfo {

    private final int length;
    private final boolean mergedAlpha;
    private int count;
    private final List<LayerRecord> layers;
    
    public LayerInfo(DataInputStream in) throws IOException {
        length = in.readInt();
        if(length != 0) {
            count = in.readShort();
            mergedAlpha = count < 0;
            if(mergedAlpha)
                count = Math.abs(count);
            layers = new ArrayList<LayerRecord>(count);
            for (int i = 0; i < count; i++) {
                LayerRecord layer = new LayerRecord(in);
                layers.add(layer);
            }
            for (int i = 0; i < count; i++) {
                LayerRecord layer = layers.get(i);
                layer.loadChannelImageData(in);
            }
        }
        else {
            count = 0;
            mergedAlpha = false;
            layers = null;
        }
    }

    public List<LayerRecord> getLayers() {
        return layers;
    }

    @Override
    public String toString() {
        return "LayerInfo{" + "length=" + length + ", mergedAlpha=" + mergedAlpha + ", count=" + count + ", layers=" + layers + '}';
    }
    
    
}
