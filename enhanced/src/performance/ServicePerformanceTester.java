package performance;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import com.sun.management.OperatingSystemMXBean;

import contact.*;
import task.*;
import appointment.*;

public class ServicePerformanceTester {
    private static final int THREAD_COUNT = 10;
    private static final int TEST_DURATION_SECONDS = 10;
    private static final int WARMUP_COUNT = 1000;
    
    private final ExecutorService executorService;
    private final MemoryMXBean memoryBean;
    private final OperatingSystemMXBean osBean;
    
    public ServicePerformanceTester() {
        this.executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        this.osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        // Force garbage collection before starting
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static class PerformanceMetrics {
        private final long totalOperations;
        private final long errorCount;
        private final double averageResponseTime;
        private final long maxResponseTime;
        private final long minResponseTime;
        private final double operationsPerSecond;
        private final long peakMemoryUsage;
        private final double cpuLoad;
        
        public PerformanceMetrics(long totalOps, long errors, List<Long> responseTimes, 
                                long duration, long peakMem, double cpu) {
            this.totalOperations = totalOps;
            this.errorCount = errors;
            
            if (!responseTimes.isEmpty()) {
                this.averageResponseTime = responseTimes.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
                this.maxResponseTime = Collections.max(responseTimes);
                this.minResponseTime = Collections.min(responseTimes);
            } else {
                this.averageResponseTime = 0;
                this.maxResponseTime = 0;
                this.minResponseTime = 0;
            }
            
            this.operationsPerSecond = (double) totalOps / (duration / 1000.0);
            this.peakMemoryUsage = peakMem;
            this.cpuLoad = cpu;
        }

        public double getSuccessRate() {
            if (totalOperations == 0) return 0.0;
            if (errorCount > totalOperations) return 0.0;
            return ((double)(totalOperations - errorCount) / totalOperations) * 100.0;
        }

        @Override
        public String toString() {
            return String.format("""
                Performance Metrics:
                ------------------
                Total Operations: %d
                Error Count: %d
                Success Rate: %.2f%%
                Average Response Time: %.2f ms
                Min Response Time: %d ms
                Max Response Time: %d ms
                Operations/Second: %.2f
                Peak Memory Usage: %.2f MB
                CPU Load: %.2f%%
                """,
                totalOperations,
                errorCount,
                getSuccessRate(),
                averageResponseTime,
                minResponseTime,
                maxResponseTime,
                operationsPerSecond,
                peakMemoryUsage / (1024.0 * 1024.0),
                cpuLoad * 100
            );
        }
    }

    public void runLoadTest(String testName, Runnable operation) {
        System.out.println("\nStarting test: " + testName);
        
        // Initialize metrics collectors
        List<Long> responseTimes = new CopyOnWriteArrayList<>();
        AtomicLong successCount = new AtomicLong(0);
        AtomicLong errorCount = new AtomicLong(0);
        AtomicLong peakMemory = new AtomicLong(0);
        List<Double> cpuMeasurements = new CopyOnWriteArrayList<>();

        long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // Create and start worker threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    long endTime = System.currentTimeMillis() + (TEST_DURATION_SECONDS * 1000);
                    
                    while (System.currentTimeMillis() < endTime) {
                        long startOp = System.nanoTime();
                        
                        try {
                            // Run the operation
                            operation.run();
                            
                            // Record successful operation metrics
                            long duration = (System.nanoTime() - startOp) / 1_000_000L;
                            responseTimes.add(duration);
                            successCount.incrementAndGet();
                            
                            // Record system metrics
                            long currentMemory = memoryBean.getHeapMemoryUsage().getUsed();
                            peakMemory.updateAndGet(peak -> Math.max(peak, currentMemory));
                            
                            double cpuLoad = measureCpuLoad();
                            if (cpuLoad >= 0) {
                                cpuMeasurements.add(cpuLoad);
                            }
                            
                            // Add small delay to prevent overwhelming system
                            Thread.sleep(1);
                            
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        } catch (Exception e) {
                            errorCount.incrementAndGet();
                            System.err.println("Operation error in " + testName + ": " + e.getMessage());
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Calculate final metrics
        long duration = System.currentTimeMillis() - startTime;
        double avgCpu = cpuMeasurements.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);

        // Print results
        PerformanceMetrics metrics = new PerformanceMetrics(
            successCount.get() + errorCount.get(), // total operations
            errorCount.get(),
            responseTimes,
            duration,
            peakMemory.get(),
            avgCpu
        );
        
        System.out.println("\nResults for: " + testName);
        System.out.println(metrics);
    }

    private double measureCpuLoad() {
        return osBean.getProcessCpuLoad();
    }

    public void warmup() {
        System.out.println("Starting warmup phase...");
        
        // Warmup Contact Service
        Contact contact = new Contact("WARMUP", "John", "Doe", "1234567890", "123 Test St");
        for (int i = 0; i < WARMUP_COUNT; i++) {
            ContactService.addContact(contact);
            ContactService.deleteContact("WARMUP");
        }
        
        // Warmup Task Service
        Task task = new Task("WARMUP", "Test Task", "Test Description");
        for (int i = 0; i < WARMUP_COUNT; i++) {
            TaskService.addTask(task);
            TaskService.deleteTask("WARMUP");
        }
        
        // Warmup Appointment Service
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date futureDate = cal.getTime();
        Appointment appt = new Appointment("WARMUP", futureDate, "Test Appointment");
        for (int i = 0; i < WARMUP_COUNT; i++) {
            AppointmentService.addAppt(appt);
            AppointmentService.deleteAppt("WARMUP");
        }
        
        System.out.println("Warmup complete");
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}