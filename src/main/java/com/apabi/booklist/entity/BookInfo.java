package com.apabi.booklist.entity;

import java.util.Date;
import java.util.List;

public class BookInfo {
    public static final Integer EXIST = 1;
    public static final Integer NOTEXIST = 0;

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 标识
     */
    private String metaId;
    /**
     * 书名
     */
    private String name;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 分类号
     */
    private String categoryNum;
    /**
     * 类目
     */
    private String category;
    /**
     * 摘要
     */
    private String summary;
    /**
     * ISBN
     */
    private String isbn;
    /**
     * 出版时间
     */
    private Date publishDate;
    /**
     * 公布时间
     */
    private Date promulgateDate;

    private Integer isExist;

    public BookInfo() {
    }

    public BookInfo(String metaId, String name, String author, String publisher, String summary, String isbn, Date publishDate, Integer isExist) {
        this.metaId = metaId;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.summary = summary;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.isExist = isExist;
    }

    public Integer getIsExist() {
        return isExist;
    }

    public void setIsExist(Integer isExist) {
        this.isExist = isExist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetaId() {
        return metaId;
    }

    public void setMetaId(String metaId) {
        this.metaId = metaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPromulgateDate() {
        return promulgateDate;
    }

    public void setPromulgateDate(Date promulgateDate) {
        this.promulgateDate = promulgateDate;
    }

    public void setProperty(String column, String value) {
        switch (column){
            case "书名": {
                this.name = value;
                break;
            }
            case "出版社": {
                this.publisher = value;
                break;
            }
            case "作者": {
                this.author = value;
                break;
            }
            case "ISBN": {
                this.isbn = value;
                break;
            }
            default: {
                throw new RuntimeException("【" + column + "】字段不符合标准！");
            }
        }

    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", metaId='" + metaId + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", categoryNum='" + categoryNum + '\'' +
                ", category='" + category + '\'' +
                ", summary='" + summary + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publishDate=" + publishDate +
                ", promulgateDate=" + promulgateDate +
                ", isExist=" + isExist +
                '}';
    }
}
