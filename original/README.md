# Original Implementation - Mobile Services Application

## Overview
This is the original implementation of the mobile services application developed for CS-320. The project demonstrates fundamental software engineering principles through three core services: ContactService, TaskService, and AppointmentService.

## Requirements
- JavaSE-1.8
- JUnit 5 testing framework

## Core Components

### Contact Service
- Unique contact ID management
- CRUD operations for contact information
- Validation rules:
  - Contact ID: Max 10 characters, unique, non-null
  - First/Last Name: Max 10 characters, non-null
  - Phone: Exactly 10 digits, non-null
  - Address: Max 30 characters, non-null

### Task Service
- Task management functionality
- CRUD operations for tasks
- Validation rules:
  - Task ID: Max 10 characters, unique, non-null
  - Name: Max 20 characters, non-null
  - Description: Max 50 characters, non-null

### Appointment Service
- Appointment scheduling system
- CRUD operations for appointments
- Validation rules:
  - Appointment ID: Max 10 characters, unique, non-null
  - Date: Must be in future, non-null
  - Description: Max 50 characters, non-null

## Testing Implementation
- Comprehensive JUnit test suite
- 100% test coverage for all services
- Validation testing for all requirements
- Edge case handling verification
