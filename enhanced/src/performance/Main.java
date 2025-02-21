package performance;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import contact.*;
import task.*;
import appointment.*;

public class Main {
    private static String generateId(String prefix, int number) {
        return String.format("%s%04d", prefix, number % 10000);
    }

    public static void main(String[] args) {
    	System.out.println("\n============================================");
        System.out.println("Starting Service Performance Testing Suite");
        System.out.println("============================================\n");
        
        ServicePerformanceTester tester = new ServicePerformanceTester();
        
        try {
            // Initialize test environment and warm up
            System.out.println("Phase 1: Initializing Test Environment");
            System.out.println("-------------------------------------");
            tester.warmup();
            System.out.println("Environment initialized successfully\n");

            // Test Contact Service
            System.out.println("Phase 2: Testing Contact Service");
            System.out.println("--------------------------------");
            System.out.println("Running load simulation...");
            final AtomicInteger contactCounter = new AtomicInteger(0);
            tester.runLoadTest("Contact Service", () -> {
                String id = generateId("CT", contactCounter.incrementAndGet());
                Contact contact = new Contact(id, "Test", "User", "1234567890", "123 Test St");
                
                ContactService.addContact(contact);
                ContactService.updateFirstName(id, "Updated");
                ContactService.updateLastName(id, "Name");
                ContactService.updatePhone(id, "9876543210");
                ContactService.updateAddress(id, "456 New St");
                ContactService.deleteContact(id);
            });

            // Test Task Service
            System.out.println("\nPhase 3: Testing Task Service");
            System.out.println("-----------------------------");
            System.out.println("Running load simulation...");
            final AtomicInteger taskCounter = new AtomicInteger(0);
            tester.runLoadTest("Task Service", () -> {
                String id = generateId("TK", taskCounter.incrementAndGet());
                Task task = new Task(id, "Test Task", "Initial Description");
                
                TaskService.addTask(task);
                TaskService.updateTaskName(id, "Updated Task");
                TaskService.updateTaskDesc(id, "Updated Description");
                TaskService.deleteTask(id);
            });

            // Test Appointment Service
            System.out.println("\nPhase 4: Testing Appointment Service");
            System.out.println("------------------------------------");
            System.out.println("Running load simulation...");
            final AtomicInteger apptCounter = new AtomicInteger(0);
            
            // Create future date for appointments
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            final Date futureDate = cal.getTime();
            
            tester.runLoadTest("Appointment Service", () -> {
                String id = generateId("AP", apptCounter.incrementAndGet());
                Date testDate = new Date(futureDate.getTime());
                Appointment appt = new Appointment(id, testDate, "Initial Appointment");
                
                AppointmentService.addAppt(appt);
                
                Calendar updateCal = Calendar.getInstance();
                updateCal.setTime(futureDate);
                updateCal.add(Calendar.HOUR, 1);
                appt.setApptDate(updateCal.getTime());
                appt.setApptDesc("Updated Description");
                AppointmentService.deleteAppt(id);
            });

        } catch (Exception e) {
            System.err.println("\nError during performance testing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            tester.shutdown();
            System.out.println("\n============================================");
            System.out.println("Finished Service Performance Testing Suite");
            System.out.println("============================================");
        }
    }
}