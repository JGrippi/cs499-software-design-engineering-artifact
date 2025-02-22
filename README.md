# Mobile Services Application - Performance Testing Enhancement

## Overview
This repository contains both the original and enhanced versions of a mobile application developed for CS-320: Software Testing, Automation, and Quality Assurance at Southern New Hampshire University. The application implements three core services (ContactService, TaskService, and AppointmentService) with comprehensive unit testing and validation.

## Project Structure
- `/enhanced` - Enhanced version with performance testing and thread safety improvements
- `/original` - Original implementation focusing on CRUD operations and unit testing
- `narrative.md` - Detailed narrative explaining enhancements and learning outcomes

## Technical Requirements
Original Implementation:
- JavaSE-1.8
- JUnit 5 for testing

Enhanced Version:
- JavaSE-17
- JUnit 5 for testing
- Java concurrent utilities

## Key Features
- CRUD operations for contacts, tasks, and appointments
- Strict data validation rules
- Comprehensive JUnit test coverage
- Performance testing framework (enhanced version)
- Thread-safe implementations (enhanced version)

## Enhancement Overview
The enhancement focuses on two major areas:
1. Code Refactoring:
   - Implementation of thread-safe data structures
   - Improved synchronization mechanisms
   - Enhanced error handling
   - Optimized data access patterns

2. Performance Testing:
   - Load simulation capabilities
   - Concurrent operation testing
   - Performance metrics collection
   - Scalability analysis

## Documentation
- [Original Implementation](/original/README.md)
- [Enhanced Version](/enhanced/README.md)
- [Enhancement Narrative](narrative.md)
