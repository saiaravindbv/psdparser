/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section;

import java.io.DataInputStream;
import java.io.IOException;
import psd.PSDException;

/**
 *
 * @author aravind
 */
public class PSDColorModeData {

    private final int length;
    private final String data;
    
    public PSDColorModeData(DataInputStream in) throws IOException {
        length = in.readInt();
        if(length > 0) {
            byte[] d = new byte[length];
            in.read(d);
            data = new String(d);
        } else {
            data = null;
            in.skipBytes(length);
        }
    }
    
    public void validate(PSDHeader header) throws PSDException {
        if(header.getMode() == 2 && length == 0)
            throw new PSDException("[Color Mode Data] Color mode data should be present for duotone image.");
        if(header.getMode() == 8 && length != 768)
            throw new PSDException("[Color Mode Data] Length should be 768 for indexed color mode.");
    }

    @Override
    public String toString() {
        return "PSDColorModeData{" + "length=" + length + ", data=" + data + '}';
    }    
}
