package com.apabi.common.vo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class BeanResult implements Serializable {

    private static final String ERROR = "ERROR";
    private static final String SUCCESS = "SUCCESS";

    private String status;
    private String cause;
    private Object data;


    public static BeanResult error(String cause) {
        BeanResult bean = new BeanResult();
        bean.status = ERROR;
        bean.cause = cause;

        return bean;
    }


    public static BeanResult success(Object data) {
        BeanResult bean = new BeanResult();
        bean.status = SUCCESS;
        bean.data = data;

        return bean;
    }


    public static BeanResult success(Integer total, List<?> rows) {
        BeanResult bean = new BeanResult();
        bean.status = SUCCESS;
        bean.data = new ListResult(total, rows);

        return bean;
    }


    public String getStatus() {
        return status;
    }


    public String getCause() {
        return cause;
    }


    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }

    private static class ListResult implements Serializable {

        private Integer total;
        private List<?> rows;

        ListResult(Integer total, List<?> rows) {
            this.total = total;
            this.rows = rows;
        }

        public Integer getTotal() {
            return total;
        }


        public List<?> getRows() {
            return rows;
        }
    }
}
