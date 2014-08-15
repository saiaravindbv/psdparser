/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd.section.LayerMaskInfo;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author aravind
 */
public class LayerRecord {
    
    private final int top, left, bottom, right;
    private final int nchannels;
    private final List<ChannelImageData> channels;
    private final Map <Integer, ChannelImageData> channelsMap = new HashMap <Integer, ChannelImageData>();
    private final String signature;
    private final String blendModeKey;
    private final byte opacity;
    private final byte clipping;
    private final byte flags;
    private final byte filler;
    private final int extralen;
    private LayerMaskData maskData;
    private LayerBlendingRanges blendingRanges;
    private final String name;
    private final List<AdditionalLayerInfo> additionalInfo;
    
    private BufferedImage image;
    
    public LayerRecord(DataInputStream in) throws IOException {
        top = in.readInt();
        left = in.readInt();
        bottom = in.readInt();
        right = in.readInt();
        nchannels = in.readShort();
        channels = new ArrayList<ChannelImageData>(nchannels);

        for (int i = 0; i < nchannels; i++) {
            ChannelImageData channelData = new ChannelImageData(in);
            channels.add(channelData);
            channelsMap.put(channelData.getId(), channelData);
        }
        
        byte[] data = new byte[4];
        in.read(data);
        signature = new String(data);
        in.read(data);
        blendModeKey = new String(data);
        opacity = in.readByte();
        clipping = in.readByte();
        flags = in.readByte();
        filler = in.readByte();
        
        extralen = in.readInt();
        int extrastart = in.available();
        int remainlen = extralen;
        
        maskData = new LayerMaskData(in);
        blendingRanges = new LayerBlendingRanges(in);
        byte length = in.readByte();
	data = new byte[((length+1) + 3 & -4) - 1];
        in.read(data);
	String str = new String(data);
        name = str.substring(0, length);
        
        additionalInfo = new ArrayList<AdditionalLayerInfo>();
        int extraend = in.available();
        remainlen -= (extrastart - extraend);
        while(remainlen >= 12) {
            extrastart = in.available();
            AdditionalLayerInfo info = new AdditionalLayerInfo(in);
            additionalInfo.add(info);
            extraend = in.available();
            remainlen -= extrastart - extraend;
        }        
    }
    
    public void loadChannelImageData(DataInputStream in) throws IOException {
        for (int i = 0; i < nchannels; i++) {
            ChannelImageData channel = channels.get(i);
            channel.loadChannelImageData(this, in);
        }
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "\nLayerRecord{" + "top=" + top + ", left=" + left + ", bottom=" + bottom + ", right=" + right + ", nchannels=" + nchannels + ", channelsData=" + channels + ", signature=" + signature + ", blendModeKey=" + blendModeKey + ", opacity=" + opacity + ", clipping=" + clipping + ", flags=" + flags + ", filler=" + filler + ", extralen=" + extralen + ", maskData=" + maskData + ", blendingRanges=" + blendingRanges + ", name=" + name + '}';
    }

    public int getHeight() {
        return Math.abs(bottom - top);
    }

    public int getWidth() {
        return Math.abs(right - left);
    }
    
    public BufferedImage getImage() {
        if (image!= null) return image;
        if ((getWidth() > 0) && (getHeight() > 0) && (channels.size() == 4)) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            WritableRaster raster = image.getRaster();
            
            byte[][] channelsData = new byte[channels.size()][];
            
            for (int i = 0; i < channels.size(); i++) {
                channelsData[i] = channels.get(i).getData();
            }
            
            int i = 0;
            for (int y = 0; y < getHeight(); y++) {
		for (int x = 0; x < getWidth(); x++) {
                    raster.setPixel(x, y, new int[] {
                        channelsData[1][i], channelsData[2][i], channelsData[3][i], channelsData[0][i] });
                    i++;
                }
            }
        }
        return image;
    }

    int getMaskHeight() {
        return maskData.getHeight();
    }
    
    int getMaskWidth() {
        return maskData.getWidth();
    }
    
    int getMaskRealHeight() {
        return maskData.getRealHeight();
    }
    
    int getMaskRealWidth() {
        return maskData.getRealWidth();
    }
}