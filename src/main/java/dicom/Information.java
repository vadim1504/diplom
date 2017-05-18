package dicom;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

public class Information {

    private DicomObject getDicom(File file){
        DicomObject object = null;
        try {
            DicomInputStream dis = new DicomInputStream(file);
            object = dis.readDicomObject();
            dis.close();
        } catch (Exception e) {
            System.exit(0);
        }
        return object;
    }

    public HashMap<String,String> getInfo(String path){
        File folder = new File(path);
        File file = folder.listFiles()[0];
        return listHeader(getDicom(file));
    }

    public Integer getInstanceNumber(File file){
        return Integer.valueOf(listHeader(getDicom(file)).get("Instance Number"));
    }

    private HashMap<String,String> listHeader(DicomObject object) {
        HashMap<String,String> map = new HashMap<>();
        Iterator iter = object.datasetIterator();
        while(iter.hasNext()) {
            DicomElement element = (DicomElement) iter.next();
            int tag = element.tag();
            try {
                String tagName = object.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = object.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        listHeader(element.getDicomObject());
                        continue;
                    }
                }
                String tagValue = object.getString(tag);
                map.put(tagName,tagValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
