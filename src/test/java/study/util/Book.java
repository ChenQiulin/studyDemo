package study.util;

import lombok.Data;

import java.io.Serializable;

/**
 * study.util.Book
 *
 * @author Wilson Chen
 * @date 2016/9/29
 */
@Data
public class Book implements Serializable{

    private Integer id;

    private String name;

    private String author;

    public Book(Integer id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }
}
