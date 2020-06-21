package com.xuecheng.manage_cms;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test01 {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;
   @Test
    public void testRestTemplate(){
       ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
       System.out.println(forEntity);
   }
   //GRIDFS存储文件
   @Test
    public void testFreeMarker() throws FileNotFoundException {
        //要存储的文件
       File file = new File("D:\\GitProjects\\xc-edu\\back_end\\xc-service-manage-cms\\src\\main\\resources\\templates\\index_banner.ftl");
       FileInputStream fileInputStream = new FileInputStream(file);
       ObjectId objectId = gridFsTemplate.store(fileInputStream, "轮播图测试文件");
       String fileId = objectId.toString();
       System.out.println(fileId);
   }
}
