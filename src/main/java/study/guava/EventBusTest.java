package study.guava;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/8/30.
 */
public class EventBusTest {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new Object() {

            @Subscribe
            public void lister(Integer integer) {
                System.out.printf("%s from int%n", integer);
            }

            @Subscribe
            public void lister(Number integer) {
                System.out.printf("%s from Number%n", integer);
            }

            @Subscribe
            public void lister(Long integer) {
                System.out.printf("%s from long%n", integer);
            }
        });

        eventBus.post(1);
        eventBus.post(1L);


        eventBus.register(new Object() {

            @Subscribe
            public void lister(DeadEvent event) {
                System.out.printf("%s=%s from dead events%n", event.getSource().getClass(), event.getEvent());
            }
        });

        eventBus.post(new Object());

    }
}
