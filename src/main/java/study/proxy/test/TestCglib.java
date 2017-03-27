package study.proxy.test;
  
import study.proxy.dao.impl.BookFacadeImpl1;
import study.proxy.proxy.BookFacadeCglib;


public class TestCglib {  
      
    public static void main(String[] args) {  
        BookFacadeCglib cglib=new BookFacadeCglib();
        BookFacadeImpl1 bookCglib=(BookFacadeImpl1)cglib.getInstance(new BookFacadeImpl1());  
        bookCglib.addBook();  
    }  
}  