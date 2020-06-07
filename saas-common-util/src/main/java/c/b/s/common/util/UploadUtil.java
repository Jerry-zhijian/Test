package c.b.s.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by guiqingqing on 2018/9/20.
 */
public class UploadUtil {
    public static boolean checkExtension(MultipartFile file, List<String> extensions) {
        extensions = extensions.stream().map(extension -> extension.toLowerCase()).collect(Collectors.toList());
        String fileName = file.getOriginalFilename();
        int idx = fileName.lastIndexOf(".");
        String extension = fileName.substring(idx + 1);
        return extensions.contains(extension.toLowerCase());
    }
}