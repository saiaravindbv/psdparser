/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section.LayerMaskInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aravind
 */
public class ChannelImageData {
    
    private static final int COMPRESSION_INVALID = -1;
    private static final int COMPRESSION_RAW = 0;
    private static final int COMPRESSION_RLE = 1;
    private static final int COMPRESSION_ZIP = 2;
    private static final int COMPRESSION_ZIP_PREDICT = 3;
    
    private final short id;
    private int row_length;
    private int length;
    private short compression;
    private List<Integer> RLE_lengths;
    private byte[] data;
    
    ChannelImageData(DataInputStream in) throws IOException {       
        id = in.readShort();
        length = in.readInt();
        compression = COMPRESSION_INVALID;
    }
    
    void loadChannelImageData(LayerRecord layer, DataInputStream in) throws IOException {
        int rows, cols;
        if(id == -2) {
            rows = layer.getMaskHeight();
            cols = layer.getMaskWidth();
        } else if(id == -3) {
            rows = layer.getMaskRealHeight();
            cols = layer.getMaskRealWidth();
        } else {
            // channel has dimensions of the layer
            rows = layer.getHeight();
            cols = layer.getWidth();
        }        
        
        if(length >= 2) compression = in.readShort();
        if(length <= 2) return;
        
        row_length = (cols * 8 + 7) / 8;
        
        switch(compression) {
            case COMPRESSION_RAW:
                length = row_length * rows;
                if(layer.getHeight() != 0) {
                    data = new byte[length];
                    in.read(data);
                }                
                break;
            case COMPRESSION_RLE:
                RLEParser rleparser = new RLEParser(rows, cols, in);
                length = rleparser.getLength();
                data = rleparser.getData();
                break;
            case COMPRESSION_ZIP: 
            case COMPRESSION_ZIP_PREDICT:
            case COMPRESSION_INVALID: 
            default:
                System.out.println("[Layer Channel Image Data] Unsupported compression.");
                System.exit(-1);
                break;
        }
    }
    
    private void read_rle_lengths(int height, DataInputStream in) throws IOException {
        RLE_lengths = new ArrayList<Integer>(height);
        for (int row = 0; row < height; row++) {
            int value = in.readShort();
            RLE_lengths.add(row, value);
        }
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return "ChannelImageData{" + "id=" + id + ", length=" + length + ", compression=" + compression + '}';
    }    
}
