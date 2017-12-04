package study.guava.current;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/12/1.
 */
public class ListenableFutureTest {


    public static void main(String[] args) {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ListenableFuture<Explosion> explosion = service.submit(new Callable<Explosion>() {
            public Explosion call() {
                return pushBigRedButton();
            }
        });
        Futures.addCallback(explosion, new FutureCallback<Explosion>() {
            // we want this handler to run immediately after we push the big red button!
            public void onSuccess(Explosion explosion) {
                walkAwayFrom(explosion);
            }
            public void onFailure(Throwable thrown) {
                battleArchNemesis(); // escaped the explosion!
            }
        });


        /////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////



    }

    public static Explosion pushBigRedButton(){
        System.out.println("pushBigRedButton.....");
        Explosion x = new Explosion();
        return  x;
//        throw  new RuntimeException("运行失败");
    }

    public static void battleArchNemesis(){
        System.out.println("battleArchNemesis.....");
    }
    public static void walkAwayFrom( Explosion explosion){
        System.out.println("walkAwayFrom....");

    }



}
