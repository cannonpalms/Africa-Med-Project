package test;

public abstract class Benchmark<T> {

	private int N = 1;
	private boolean printAll;
	
	public Benchmark(int iterations, boolean printEachRuntime) {
		this.N = iterations;
		this.printAll = printEachRuntime;
	}
	
	public Benchmark(int iterations) {
		this.N = iterations;
	}
	
	public Benchmark(boolean printEachRuntime) {
		this.printAll = printEachRuntime;
	}
	
	public Benchmark() {
		
	}
	public abstract void run();
	
	public void runBenchmark() {
		if (printAll) {
			runBenchmarkPrintAll();
		}
		else {
			runBenchmarkStandard();
		}
	}
	
	private void runBenchmarkPrintAll() {
		long t1, t2, total = 0;
		double avg;
		
		for (int i = 0; i < N; i++) {
			t1 = System.nanoTime();
			run();
			t2 = System.nanoTime();
			
			total = total + (t2 - t1);
			
			avg = (t2 - t1) / Math.pow(10, 6);
			System.out.println(String.format("Run #%d: %fms.", i, avg));
		}
		
		avg = total / N / Math.pow(10, 6);
		
		System.out.println(String.format("Benchmark: average: %fms", avg));
	}
	
	private void runBenchmarkStandard() {
		long t1, t2, total = 0;
		double avg;
		
		for (int i = 0; i < N; i++) {
			t1 = System.nanoTime();
			run();
			t2 = System.nanoTime();
			
			total = total + (t2 - t1);
		}
		
		avg = total / N / Math.pow(10, 6);
		
		System.out.println("Benchmark: average: " + avg + "ms.");
	}
}
