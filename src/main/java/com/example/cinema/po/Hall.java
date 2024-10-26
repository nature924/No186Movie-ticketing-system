package com.example.cinema.po;

/**
 * @author fjj
 * @date 2019/4/28 5:09 PM
 */
public class Hall {
    private Integer id;
    private String name;
    private Integer row;
    private Integer column;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
