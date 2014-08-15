package psd.section;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import psd.section.LayerMaskInfo.LayerInfo;
import psd.section.LayerMaskInfo.LayerRecord;







/**
 *
 * @author aravind
 */
public class PSDLayerMaskInfo {

    private final int length;
    private final LayerInfo layerInfo;
    private final GlobalMaskInfo maskInfo;
    
    public PSDLayerMaskInfo(DataInputStream in) throws IOException {
        length = in.readInt();
        if(length != 0) {
            layerInfo = new LayerInfo(in);        
            maskInfo = new GlobalMaskInfo(in);
        }
        else {
            layerInfo = null;
            maskInfo = null;
        }
    }

    public List<LayerRecord> getLayers() {
        return layerInfo.getLayers();
    }
    
    public List<LayerRecord> getChannels() {
        return layerInfo.getLayers();
    }
    
    @Override
    public String toString() {
        return "PSDLayerMaskInfo{" + "length=" + length + ", layerInfo=" + layerInfo + ", maskInfo=" + maskInfo + '}';
    }
}


class GlobalMaskInfo {

    private final int length;
    private final short overlay_color_space;
    private final short [] color_components;
    private final short opacity;
    private final short kind;
    
    public GlobalMaskInfo(DataInputStream in) throws IOException {
        length = in.readInt();
        overlay_color_space = in.readShort();
        color_components = new short[4];
        for (int i = 0; i < 4; i++) {
            color_components[i] = in.readShort();
        }
        opacity = in.readShort();
        kind = in.readShort();
    }

    @Override
    public String toString() {
        return "GlobalMaskInfo{" + "length=" + length + ", overlay_color_space=" + overlay_color_space + ", color_components=" + color_components + ", opacity=" + opacity + ", kind=" + kind + '}';
    }
}