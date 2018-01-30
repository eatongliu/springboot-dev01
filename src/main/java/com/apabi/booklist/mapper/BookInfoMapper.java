package com.apabi.booklist.mapper;

import com.apabi.booklist.entity.BookInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyutong on 2017/12/5.
 */
public interface BookInfoMapper {
    List<BookInfo> findAll();
    List<BookInfo> findByConditions(Map<String, Object> params);
}
