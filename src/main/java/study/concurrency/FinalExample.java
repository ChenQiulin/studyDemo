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
package study.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by ChenQiulin on 2016/8/16.
 */
public class FinalExample {
    int i;
    final int j = 0;
    static FinalExample obj;

    public FinalExample() {
        i = 1;
//        j=2;
    }

    public static void main(String[] args) throws Exception{
        System.out.println("method main begin-----");
        Thread t = new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                while (true) {
                    System.out.println(i++);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        t.join(2000);
        System.out.println("method main end-----");
    }
}
