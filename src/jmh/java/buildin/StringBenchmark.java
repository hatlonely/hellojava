package buildin;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StringBenchmark {
    @Benchmark
    public void stringAdd1() {
        for (int i = 0; i < 100; i++) {
            String str = "hello" + "world";
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StringBenchmark.class.getSimpleName())
                .forks(2).build();
        new Runner(options).run();
    }
}
