package study.spring.exception.dao;

import org.springframework.stereotype.Repository;

import study.spring.exception.exception.BusinessException;
import study.spring.exception.exception.ParameterException;

@Repository("testDao")
public class TestDao {
	public void exception(Integer id) throws Exception {
		switch(id) {
		case 1:
			throw new BusinessException("12", "dao12");
		case 2:
			throw new BusinessException("22", "dao22");
		case 3:
			throw new BusinessException("32", "dao32");
		case 4:
			throw new BusinessException("42", "dao42");
		case 5:
			throw new BusinessException("52", "dao52");
		default:
			throw new ParameterException("Dao Parameter 2  Error");
		}
	}
}
