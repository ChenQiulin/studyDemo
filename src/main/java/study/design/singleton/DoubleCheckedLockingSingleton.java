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
 * "双检锁"(Double-Checked Lock)尽量将"加锁"推迟,只在需要时"加锁"(仅适用于java 5.0 以上版本,volatile保证原子操作)
 * happens-before:"什么什么一定在什么什么之前运行",也就是保证顺序性.
 * 现在的CPU有乱序执行的能力(也就是指令会乱序或并行运行,可以不按我们写代码的顺序执行内存的存取过程),
 * 并且多个CPU之间的缓存也不保证实时同步,只有上面的happens-before所规定的情况下才保证顺序性.
 * JVM能够根据CPU的特性(CPU的多级缓存系统、多核处理器等)适当的重新排序机器指令,使机器指令更符合CPU的执行特点，最大限度的发挥机器的性能.
 * 如果没有volatile修饰符则可能出现一个线程t1的B操作和另一线程t2的C操作之间对instance的读写没有happens-before，
 * 可能会造成的现象是t1的B操作还没有完全构造成功，但t2的C已经看到instance为非空，
 * 这样t2就直接返回了未完全构造的instance的引用，t2想对instance进行操作就会出问题.
 * volatile 的功能:
 * 1. 避免编译器将变量缓存在寄存器里
 * 2. 避免编译器调整代码执行的顺序
 * 优化器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。
 */
public class DoubleCheckedLockingSingleton {

    // java中使用双重检查锁定机制,由于Java编译器和JIT的优化的原因系统无法保证我们期望的执行次序。
    // 在java5.0修改了内存模型,使用volatile声明的变量可以强制屏蔽编译器和JIT的优化工作
    private volatile static DoubleCheckedLockingSingleton uniqueInstance;

    private DoubleCheckedLockingSingleton() {
    }

    public static DoubleCheckedLockingSingleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return uniqueInstance;
    }
}
