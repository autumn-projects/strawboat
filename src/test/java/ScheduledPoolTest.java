import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.pool.ScheduledPool;

public class ScheduledPoolTest {

    public static void main(String[] args) {
        ScheduledPool scheduledPool = new ScheduledPool();
        new Thread(scheduledPool::execute).start();
        while (true) {
            IP ip = scheduledPool.take();
            System.out.println(ip.getAddress() + ":" + ip.getPort());
        }
    }
}
