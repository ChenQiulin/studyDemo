/**
 * Created by ChenQiulin on 2016/8/7.
 * <p/>
 * 1.描述:
 * Singleton(单例)是设计模式的一种,为了保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * 2.主要特点:
 * 1)单例类确保自己只有一个实例(构造函数私有:不被外部实例化,也不被继承)。
 * 2)单例类必须自己创建自己的实例。
 * 3)单例类必须为其他对象提供唯一的实例。
 * 3.单例模式的应用：
 * 资源管理器,回收站,打印机资源,线程池,缓存,配置信息类,管理类,控制类,门面类,代理类通常被设计为单例类
 * 如果程序有多个类加载器又同时使用单例模式就有可能多个单例并存就要找相应解决方法了
 */
package study.design.singleton;