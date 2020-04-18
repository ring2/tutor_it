package xyz.ring2.admin.core.controller;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author :     weiquanquan
 * @date :       2020/3/1 19:54
 * description:
 **/
@RestController
@Slf4j
public class CourseController {

    @PostMapping("/uploadImg")
    public String uploadImg(@RequestParam MultipartFile file) throws IOException {
        if (file != null) {
            String originName = file.getOriginalFilename();
            String suffix = originName.substring(originName.lastIndexOf("."));
            String storeFile = UUID.randomUUID().toString().substring(0, 15) + suffix;
            System.out.println(storeFile);
            File file1 = new File("E:\\testUpload" + "\\" + storeFile);
            if (file1.exists()) {
                file1.delete();
            }
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(file1));
            if (true)
                throw new NullPointerException();
            return "success";
        }
        return "failure";
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") String stringName, HttpServletResponse response) throws Exception {
        String reg = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(reg);
        System.out.println(simpleDateFormat.format(new Date()));
        File file = new File("E:\\testUpload\\" + stringName);
        FileInputStream fileInputStream = new FileInputStream(file);
        response.setContentType("application/x-msdownload;charset=utf-8");//设置输出格式
        response.setHeader("Content-disposition", "attachment;filename="
                + stringName);
        FileCopyUtils.copy(fileInputStream, response.getOutputStream());
    }

    /**
     * 测试文件上传效率
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        testArrayListSort();
        /*long l = System.nanoTime();
        testFileCopy();
        System.out.println(System.nanoTime() - l);
        long a = System.nanoTime();
        testFileCopy1();
        System.out.println((System.nanoTime()) - a);
*/
    }

    public static void testFileCopy() throws Exception {
        File file = new File("D:\\Adobe\\Demo4.java");
        File file1 = new File("D:\\Adobe\\Demo.java");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileInputStream fileInputStream = new FileInputStream(file1);
        byte[] bytes = new byte[4096];
        int total;
        while ((total = fileInputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, total);
        }
    }

    public static void testFileCopy1() throws Exception {
        File file = new File("D:\\Adobe\\Demo3.java");
        File file1 = new File("D:\\Adobe\\Demo.java");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileInputStream fileInputStream = new FileInputStream(file1);
        FileCopyUtils.copy(fileInputStream, fileOutputStream);
    }

    public static void testArrayListSort() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("11");
        arrayList.add("12");
        arrayList.add("13");
        arrayList.add("11");
        arrayList.add("12");
        arrayList.add("14");
        arrayList.add("16");

        for (int i = 0 ; i < arrayList.size(); i++){
            if (i == 1)
            arrayList.remove(2);
            System.out.println(i);
        }
        System.out.println(arrayList.toString());

    }
}
