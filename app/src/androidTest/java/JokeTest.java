import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.JokeLoadListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**<p>
 * Created by Angad on 16-11-2016.
 * </p>
 */

@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class JokeTest extends ApplicationTestCase<Application> implements JokeLoadListener{
    private CountDownLatch signal;
    private String joke;

    public JokeTest() {
        super(Application.class);
    }

    @Test
    public void testJoke() {
        try {
            signal = new CountDownLatch(1);
            EndpointsAsyncTask task = new EndpointsAsyncTask();
            task.setJokeLoadListener(JokeTest.this);
            task.execute(getContext());
            signal.await(10, TimeUnit.SECONDS);
            assertNotNull("joke is null", joke);
            assertFalse("joke is empty", joke.isEmpty());
        } catch (Exception ex) {
            fail();
        }
    }
    @Override
    public void onJokeLoaded(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}
