/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import psd.PSDException;


/**
 *
 * @author aravind
 */
public class PSDImageResources {

    private final int length;
    private final Map<Integer, ImageResourceBlock> resourceMap = new HashMap<Integer, ImageResourceBlock>();
    
    public PSDImageResources(DataInputStream in) throws IOException, PSDException {
        length = in.readInt();
        int start = in.available(), end = in.available();
        while(true) {
            if(start - end == length) break;
            ImageResourceBlock block = new ImageResourceBlock(in);
            resourceMap.put(block.getId(), block);
            end = in.available();
        }
    }

    @Override
    public String toString() {
        return "PSDImageResources{" + "length=" + length + ", resourceMap=" + resourceMap + '}';
    }
}

class ImageResourceBlock {
    private final String signature;
    private final int id;
    private final String name;
    private final int length;

    public ImageResourceBlock(DataInputStream in) throws IOException, PSDException {
        byte[] data = new byte[4];
        in.read(data);
        signature = new String(data);
        id = in.readUnsignedShort();
        
        byte len = in.readByte();
        data = new byte[((len + 2) & -2) - 1];
        in.read(data);
        name = new String(data);

        length = in.readInt();
        in.skipBytes(length);
        if (length % 2 != 0)
            in.readByte();
        validate();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ImageResourceBlock{" + "signature=" + signature + ", id=" + id + ", name=" + name + ", length=" + length + '}';
    }   

    private void validate() throws PSDException {
        if(!signature.equals("8BIM"))
            throw new PSDException("[ImageResourceBlock] Invalid Signature.");
    }
}