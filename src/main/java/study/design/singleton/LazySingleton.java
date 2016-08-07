/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package study.design.singleton;

/**
 * Created by ChenQiulin on 2016/8/7.
 * 如果开销比较大，希望用到时才创建就要考虑延迟实例化,或者Singleton的初始化需要某些外部资源(比如网络或存储设备),就要用后面的方法了.
 * Lazy initialization 懒汉式单例类
 *
 * 同步一个方法可能造成程序执行效率下降100倍，完全没有必要每次调用getInstance都加锁，
 * 事实上我们只想保证一次初始化成功，其余的快速返回而已,如果在getInstance频繁使用的地方就要考虑重新优化了.
 */
public class LazySingleton {
    private static LazySingleton uniqueInstance;

    private LazySingleton() {
    }

    public static synchronized LazySingleton getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new LazySingleton();
        return uniqueInstance;
    }
}
