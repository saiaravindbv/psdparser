package psd;

import psd.section.PSDColorModeData;
import psd.section.PSDHeader;
import psd.section.PSDImageResources;
import psd.section.PSDLayerMaskInfo;
import psd.section.PSDImageData;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PSD {

    private final PSDHeader header;
    private final PSDColorModeData colorMode;
    private final PSDImageResources imageResources;
    private final PSDLayerMaskInfo layerMaskInfo;
    private final PSDImageData imageData;
    
    public PSD(InputStream input) throws IOException, PSDException {
        DataInputStream in = new DataInputStream(input);
        header = new PSDHeader(in);
        
        colorMode = new PSDColorModeData(in);
        colorMode.validate(header);
        
        imageResources = new PSDImageResources(in);
        layerMaskInfo = new PSDLayerMaskInfo(in);
        imageData = new PSDImageData(in);
    }

    public PSDColorModeData getColorMode() {
        return colorMode;
    }

    public PSDHeader getHeader() {
        return header;
    }

    public PSDImageResources getImageResources() {
        return imageResources;
    }

    public PSDLayerMaskInfo getLayerMaskInfo() {
        return layerMaskInfo;
    }

    public PSDImageData getImageData() {
        return imageData;
    }

    @Override
    public String toString() {
        return "PSD{" + "\nheader=" + header + ", \ncolorMode=" + colorMode + ", \nimageResources=" + imageResources + ", \nlayerMaskInfo=" + layerMaskInfo + ", "
                + "\nimageData=" + imageData + '}';
    }
}
