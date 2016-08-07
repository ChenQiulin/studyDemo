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
 * <p/>
 * Lazy initialization holder class 满足所有 Double-Checked Locking 满足的条件，并且没有显示的同步操作
 * <p/>
 * <p/>
 * 根据jvm规范，当某对象第一次调用LazyInitHolderSingleton.getInstance()时，LazyInitHolderSingleton类被首次主动使用，
 * jvm对其进行初始化（此时并不会调用LazyInitHolderSingleton()构造方法），然后LazyInitHolderSingleton调用getInstance()方法，
 * 该方法中，又首次主动使用了SingletonHolder类，所以要对SingletonHolder类进行初始化，初始化中，INSTANCE常量被赋值时才调用了
 * LazyInitHolderSingleton的构造方法LazyInitHolderSingleton()，完成了实例化并返回该实例。
 * 当再有对象（也许是在别的线程中）再次调用LazyInitHolderSingleton.getInstance()时，因为已经初始化过了，
 * 不会再进行初始化步骤，所以直接返回INSTANCE常量即同一个LazyInitHolderSingleton实例。
 */
public class LazyInitHolderSingleton {
    private LazyInitHolderSingleton() {
    }

    private static class SingletonHolder {
        private static final LazyInitHolderSingleton INSTANCE = new LazyInitHolderSingleton();
    }

    public static LazyInitHolderSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
