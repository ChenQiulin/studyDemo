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
 *
 * Eager initialization 饿汉式单例类(依赖jvm在加载类时创建唯一单例实例)
 */
public class EagerSingleton {
    // jvm保证在任何线程访问uniqueInstance静态变量之前一定先创建了此实例
    private static EagerSingleton uniqueInstance = new EagerSingleton();

    // 私有的默认构造子，保证外界无法直接实例化
    private EagerSingleton() {
    }

    // 提供全局访问点获取唯一的实例
    public static EagerSingleton getInstance() {
        return uniqueInstance;
    }
}
