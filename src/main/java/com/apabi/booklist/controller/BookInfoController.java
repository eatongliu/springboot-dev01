package com.apabi.booklist.controller;

import com.alibaba.fastjson.JSONObject;
import com.apabi.booklist.entity.BookInfo;
import com.apabi.utils.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by liuyutong on 2017/12/5.
 */
@Controller
@RequestMapping("/booklist")
public class BookInfoController {
    private Logger logger = LoggerFactory.getLogger(BookInfoController.class);

    @Autowired
    private ConfigUtil configUtil;

    @RequestMapping("")
    public String hello(Model model) {
        model.addAttribute("test", "/nr2ktools");
        return "booklist/input";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject excelUpload(MultipartFile file, String specialName, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            logger.debug("文件的specialName为：{}", specialName);
            List<BookInfo> books = ExcelUtil.readExcel(file.getInputStream());

            //校验
            checkUp(JSONObject.toJSONString(books), specialName);

            //保存文件
//            String originalFilename = file.getOriginalFilename();
//            String newName = "original_" + originalFilename;
//            String path = configUtil.get("excel.upload.path");
//            File originalExcel = new File(path + newName);
//            file.transferTo(originalExcel);
            jsonObject.put("success", "上传成功");
        }catch (RuntimeException e) {
            jsonObject.put("error", e.getMessage());
            logger.error("Exception: {}", e.getMessage());
        } catch (IOException e) {
            jsonObject.put("error", "上传失败");
            logger.error("Exception: {}", e);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/checkup", method = RequestMethod.POST)
    @ResponseBody
    public void checkUp(@RequestParam String books, String specialName) {
        String url = configUtil.get("booksearch.api");
        List<BookInfo> sources = JSONObject.parseArray(books, BookInfo.class);
        List<BookInfo> result = new ArrayList<>(sources.size() * 5);
        try{
            logger.debug("======开始校验======");
            sources.parallelStream().forEachOrdered(source -> {
                try {
                    String name = URLEncoder.encode(source.getName(), "UTF8");
                    System.out.println(url + name);
                    HttpEntity httpEntity = HttpUtils.doGetEntity(url + name);
                    InputStream is = httpEntity.getContent();
                    JsonNode node = new Xml2Json().parseToNode(is);
                    int totalCount = node.get("TotalCount").asInt();
                    if (totalCount == 0) {
                        source.setIsExist(BookInfo.NOTEXIST);
                        result.add(source);
                        return;
                    }
                    JsonNode record = node.get("Records").get("Record");
                    if (record.isArray()) {
                        record.forEach(one -> result.add(parseBookInfo(one)));
                    }else {
                        result.add(parseBookInfo(record));
                    }
                } catch (Exception e) {
                    logger.error("Exception: {}", e);
                }
            });

            String[] fields = {"是否存在", "标识", "书名", "作者", "出版社", "分类号", "类目", "出版时间", "ISBN", "公布时间", "摘要"};
            ExcelUtil.writeExcel(generateUploadPath() + "dealed_"+specialName, "测试", 0, fields, result);
            logger.debug("======校验完成！======");
        } catch (Exception e) {
            logger.error("Exception: {}", e);
        }
    }

    @RequestMapping("/export")
    public void resultExport(HttpServletResponse response,@RequestParam String specialName) throws IOException {
        try (OutputStream output = response.getOutputStream()){
            FileInputStream input = new FileInputStream(generateUploadPath() + "/dealed_" + specialName);
            Workbook wb = new XSSFWorkbook(input);

            response.reset();
            String filename = URLEncoder.encode("书苑返回信息.xlsx", "UTF8");
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            response.setContentType("application/msexcel");
            wb.write(output);
        }catch (Exception e) {
            logger.error("Exception: {}", e);
        }
    }


    private BookInfo parseBookInfo(JsonNode record) {
        String metaId = valueOf(record.get("Identifier"));
        String title = valueOf(record.get("Title"));
        String creator = valueOf(record.get("Creator"));
        String publisher = valueOf(record.get("Publisher"));
        String isbn = valueOf(record.get("ISBN"));
        String summary = valueOf(record.get("Abstract"));
        String publishDate = valueOf(record.get("PublishDate"));
        return new BookInfo(metaId, title, creator, publisher, summary, isbn, DateUtil.getDateFromString(publishDate, "yyyyMMdd"), BookInfo.EXIST);
    }

    private String valueOf(JsonNode textNode) {
        if (textNode != null) {
            return textNode.asText();
        }
        return null;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void resultExport() {
        try{
            System.out.println("aaa");
        }catch (Exception e) {

        }
        throw new NullPointerException("ccc");
    }

    public String generateUploadPath() throws Exception{
        String root = ConfigUtil.getRootPath();
        String nr2kToolsFilesPath = configUtil.get("file.path");
        String uploadPath = configUtil.get("excel.upload.path");

        StringBuilder builder = new StringBuilder();
        builder.append(root).append("/").append(nr2kToolsFilesPath).append("/").append(uploadPath).append("/");
        return builder.toString();
    }

}