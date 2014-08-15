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
public class PSDHeader {

    private final String signature;
    private final int version;
    private final int nchannels;
    private final int rows;
    private final int columns;
    private final int depth;
    private final int mode;
    
    public PSDHeader(DataInputStream in) throws IOException, PSDException {
        byte[] data = new byte[4];
        in.read(data);
        signature = new String(data);
        version = in.readUnsignedShort();        
        in.skipBytes(6);
        nchannels = in.readUnsignedShort();
        rows    = in.readInt();
	columns = in.readInt();
        depth   = in.readUnsignedShort();
        mode    = in.readUnsignedShort();
        validate();
    }
    
    private void validate() throws PSDException {
        if(!signature.equals("8BPS"))
            throw new PSDException("[Header] Invalid Signature.");
        if(version != 1 &&  version != 2)
            throw new PSDException("[Header] Invalid Version.");
        if(nchannels < 1 || nchannels > 56)
            throw new PSDException("[Header] Invalid Channelcount.");
        switch(version) {
            case 1:     //PSD
                if(rows < 1 || rows > 30000)
                    throw new PSDException("[Header] Invalid Image height.");
                if(columns < 1 || columns > 30000)
                    throw new PSDException("[Header] Invalid Image width.");
            case 2:     //PSB
                if(rows < 1 || rows > 300000)
                    throw new PSDException("[Header] Invalid Image height.");
                if(columns < 1 || columns > 300000)
                    throw new PSDException("[Header] Invalid Image width.");
        }
        if(depth != 1 && depth != 8 && depth != 16 && depth != 32)
            throw new PSDException("[Header] Invalid Depth.");
        switch(mode) {
            case 0: case 1: case 2: case 3: case 4: case 7: case 8: case 9:
                break;
            default:
                throw new PSDException("[Header] Invalid Colormode.");
        }
    }

    public int getChannel() {
        return nchannels;
    }

    public int getColumns() {
        return columns;
    }

    public int getDepth() {
        return depth;
    }

    public int getMode() {
        return mode;
    }

    public int getRows() {
        return rows;
    }

    public String getSignature() {
        return signature;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "PSDHeader{" + "signature=" + signature + ", version=" + version + ", nchannels=" + nchannels + ", rows=" + rows + ", columns=" + columns + ", depth=" + depth + ", mode=" + mode + '}';
    }
}
