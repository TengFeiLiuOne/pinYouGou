package cn.itcast.core.controller.upload;

import cn.itcast.core.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.utils.fdfs.FastDFSClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/upload")
public class UploadController {

    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 文件图片
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile.do")
    public Result upload(MultipartFile file){

        try {
            String conf="classpath:fdfs/fdfs_client.conf";
//            创建一个文件系统客户端对象
            FastDFSClient fastDFSClient = new FastDFSClient(conf);
//            获取文件名
            String originalFilename = file.getOriginalFilename();
            String extname = FilenameUtils.getExtension(originalFilename);
            String path = fastDFSClient.uploadFile(file.getBytes(), extname, null);
            System.out.println(path);
            path=FILE_SERVER_PATH+path;
            return new Result(true,path);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }

    @RequestMapping("/dataImportBrand.do")
    public Result dataImportBrand(MultipartFile file){
        try {
            InputStream ins = file.getInputStream();
            //根据上述创建的输入流 创建工作簿对象
            Workbook wb = WorkbookFactory.create(ins);
            //得到第一页 sheet
            //页Sheet是从0开始索引的
            Sheet sheet = wb.getSheetAt(0);
            //利用foreach循环 遍历sheet中的所有行
            int i = 0;
            List<Brand> list = new ArrayList<>();
            for (Row row : sheet) {
                if (i==0){  //第一行是标题
                    i++;
                    continue;
                }
                Brand brand = new Brand();
                //遍历row中的所有方格
                int j = 0;
                for (Cell cell : row) { //0:name 1:first_char
                    if (j==0){
                        brand.setName(cell.toString());
                    }
                    if (j==1){
                        brand.setFirstChar(cell.toString());
                    }
                    j++;
                }
                //添加到集合中
                list.add(brand);
            }
            //关闭输入流
            ins.close();
            return new Result(true,"上传成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }

}
