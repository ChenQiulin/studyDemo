package study.spring.exception.service;


import org.springframework.stereotype.Component;

@Component
public interface TestService {
	public void exception(Integer id) throws Exception;
	
	public void dao(Integer id) throws Exception;
}
