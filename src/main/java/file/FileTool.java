package file;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FileTool {

    public static File newFolder(String path){
        File folder_new = new File(path);
        if (!folder_new.exists()) {
            folder_new.mkdir();
        }
        return folder_new;
    }

    public static void delete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
            file.delete();
        } else {
            file.delete();
        }
    }

    public static ArrayList<File> getFiles(String path){
        File folder = new File(path);
        File[] files = folder.listFiles();
        return new ArrayList<File>(Arrays.asList(files));
    }
}
