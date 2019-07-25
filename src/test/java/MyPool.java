import com.oscroll.strawboat.assets.entity.IP;
import com.oscroll.strawboat.pool.ScheduledPool;

public class MyPool extends ScheduledPool {

    @Override
    public void onAvailable(IP ip) {
        System.out.println("mypool");
        super.onAvailable(ip);
    }

}
