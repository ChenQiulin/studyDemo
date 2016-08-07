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
package study.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScattingAndGather {

    public static void main(String args[]){
        gather();
    }

    public static void gather()
    {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);

        byte[] b1 = {'0','1'};
        byte[] b2 = {'2','3'};
        header.put(b1);
        body.put(b2);

        ByteBuffer [] buffs = {header, body};

        try
        {
            FileOutputStream os = new FileOutputStream("src/scattingAndGather.txt");
            FileChannel channel = os.getChannel();
            channel.write(buffs);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void method1(){
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try
        {
            fromFile = new RandomAccessFile("src/fromFile.xml","rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt","rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            toChannel.transferFrom(fromChannel, position, count);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(fromFile != null){
                    fromFile.close();
                }
                if(toFile != null){
                    toFile.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
