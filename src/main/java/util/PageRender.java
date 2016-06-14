package util;

import java.util.List;

/** 
 *页面数据渲染器  与Renderer的区别在于  Renderer是处理行的； 当需要通过附近行计算当前行的值时使用PageRenderer
 *@author Chenql
 */

public interface PageRender<S,V> {
	
	/**
	 * 将源数据集经过转换，输出转换后结果
	 * @param srcData
	 * @return 转换后集合
	 * @throws Exception
	 */
	public List<V> render(List<S> srcData) throws Exception;

}