package backend.app;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DriftingApplication extends Application {
    ExecutorService es = Executors.newFixedThreadPool(10);
}
