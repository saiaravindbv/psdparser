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
class RLEParser {

    byte [] result = null;
    int length = 0;
    
    public RLEParser(int rows, int cols, DataInputStream in) throws IOException {
        
        result = new byte[rows * cols];
        
        int[] size = new int[rows];
        for (int i = 0; i < rows; i++) {
            size[i] = in.readUnsignedShort();
            length += size[i];
        }
        
        //data = new byte[rows * cols];
        for (int i = 0; i < rows; i++) {
            byte[] packed = new byte[size[i]];
            in.read(packed);
            
            byte[] unpacked = unpack(packed, cols);
            System.arraycopy(unpacked, 0, result, i * cols, unpacked.length);
        }
    }
    
    byte[] getData() {
        return result;
    }
    
    int getLength() {
        return length;
    }

    private byte[] unpack(byte[] data, int size) {
        int writePos = 0;
	int readPos = 0;
        
        byte[] ret = new byte[size];
        
        while (readPos < data.length) {
            int n = data[readPos++];
            if (n > 0) {
                int count = n + 1;
		for (int j = 0; j < count; j++)
		    ret[writePos++] = data[readPos++];
            } else {
                byte b = data[readPos++];
		int count = -n + 1;
		for (int j = 0; j < count; j++)
                    ret[writePos++] = b;
            }
        }
        
        return ret;
    }
    
}
