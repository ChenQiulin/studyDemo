package data.base;

public class AliDevHandBook {


    public static void main(String[] args) {

        float a  = 0.125f;

        double b = 0.125d;

        System.out.print(" 0.125f - 0.125d ==0.0  :");
        System.out.println((a-b)==0.0);


        double c = 0.8;
        double d = 0.7;

        double e = 0.6;


        System.out.print(" 0.6-0.7 ==  0.8-.07");
        System.out.println( (c-d) == (e-d));


        System.out.print("1.0/0 = ");
        System.out.println(1.0/0);


        System.out.print("0.0/0.0 = ");
        System.out.println(0.0/0.0);


        System.out.println();

      /*  Java 中>>和>>>的区别
        Java中的位运算符：
        >>表示右移，如果该数为正，则高位补0，若为负数，则高位补1；
        >>>表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0。
        表达式为：
        result = exp1 >> exp2;
        result = exp2 >>> exp2;
        表示把数exp1向右移动exp2位。
        例如：
        res = 20 >> 2;
        20的二进制为 0001 0100，右移2位后为 0000 0101，则结果就为 res = 5;
        res = -20 >> 2;
        -20的二进制为其正数的补码加1，即 1110 1011，右移2位后为 1111 1100，结果为 res = -6;
        而对于>>>符号而言：
        res = 20 >>> 2; 的结果与 >> 相同；
        res = -20 >> 2;
        -20的二进制为 1110 1011，右移2位，此时高位补0，即 0011 1010，结果为 res = 58;
        补充：
        << 是与>>对应的左移运算符，表示将exp1向左移动exp2位，在低位补0。其实，向左移动n位，就相当于乘以2^n。
        左移没有<<<运算符！*/

        System.out.print(" 20 >> 2 = ");
        System.out.println(20 >> 2);


        System.out.print(" -20 >> 2 = ");
        System.out.println( -20 >> 2);

        /// 编译出错
       // f(null);

        System.out.print(" g(double d)  g(Integer d) -->  g(1)  ");
       g(1);


       try {


       String s = null;

        System.out.print("  switch (String null)  = ");
       switch (s){
           case "1" :
               System.out.println(" case 1");break;

           case "null" :
               System.out.println(" case null");break;

           default:
               System.out.println(" case default");
               break;
       }
       }catch (Exception exp){
           System.out.println( exp.toString());
       }


    }


    static void g(double d){
        System.out.println("g(double d) ");
    }
    static void g(Integer d){
        System.out.println("g(Integer d) ");
    }


   static  void f(String s){
        System.out.println("f(String s) "+ s);
    }

    static  void f(Integer s){
        System.out.println("f(String s) "+ s);
    }

}
