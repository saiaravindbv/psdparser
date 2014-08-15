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
public class LayerMaskData {

    private final int size;
    private int top;
    private int left;
    private int bottom;
    private int right;
    private short defaultColor;
    private short flags;
    private short maskParam;
    
    private short realFlags;
    private short realDefaultColor;
    private int realTop;
    private int realLeft;
    private int realBottom;
    private int realRight;
    
    
    LayerMaskData(DataInputStream in) throws IOException {
        size = in.readInt();
        if(size >= 20) {
            top = in.readInt();
            left = in.readInt();
            bottom = in.readInt();
            right = in.readInt();
            defaultColor = in.readShort();
            flags = in.readShort();                
            if(size >= 36) {
                realFlags = in.readShort();
                realDefaultColor = in.readShort();
                realTop = in.readInt();
                realLeft = in.readInt();
                realBottom = in.readInt();
                realRight = in.readInt();
            }
        }
        validate();
    }
    
    private void validate() {
    }
    
    int getHeight() {
        return Math.abs(bottom - top);
    }
    
    int getWidth() {
        return Math.abs(right - left);
    }
    
    int getRealHeight() {
        return Math.abs(realBottom - realTop);
    }
    
    int getRealWidth() {
        return Math.abs(realRight - realLeft);
    }

    @Override
    public String toString() {
        return (size == 0) ? 
        "" :
        "LayerMaskData{" + "size=" + size + ", top=" + top + ", left=" + left + ", bottom=" + bottom + ", right=" + right + ", defaultColor=" + defaultColor + ", flags=" + flags + '}';
        
    }
}
