package dicom;

import org.dcm4che2.imageio.plugins.dcm.DicomImageReadParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Convert {

    public BufferedImage convert(File file){
        Iterator iter = ImageIO.getImageReadersByFormatName("DICOM");
        ImageReader reader = (ImageReader) iter.next();
        DicomImageReadParam param = (DicomImageReadParam) reader.getDefaultReadParam();
        ImageInputStream iis = null;
        BufferedImage bufferedImage = null;
        try {
            iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, false);
            bufferedImage = reader.read(0, param);
            iis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
