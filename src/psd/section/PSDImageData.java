/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import psd.section.LayerMaskInfo.ChannelImageData;

/**
 *
 * @author aravind
 */
public class PSDImageData {

    private static final int COMPRESSION_INVALID = -1;
    private static final int COMPRESSION_RAW = 0;
    private static final int COMPRESSION_RLE = 1;
    private static final int COMPRESSION_ZIP = 2;
    private static final int COMPRESSION_ZIP_PREDICT = 3;
    
    private final short compression;
    private final List<ChannelImageData> channelsInfo;
    
    public PSDImageData(DataInputStream in) throws IOException {
        compression = in.readShort();
        channelsInfo = new ArrayList<ChannelImageData>();
    }
    
}
