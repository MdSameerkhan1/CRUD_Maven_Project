package com.hibernate.CRUD.CRUD;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class StudentCrud {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
       
        StandardServiceRegistry st = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(st).getMetadataBuilder().build();
        SessionFactory factory = meta.getSessionFactoryBuilder().build();

        char option;

        do {
            System.out.println("Choose an action:");
            System.out.println("1. Create Student");
            System.out.println("2. Update Student");
            System.out.println("3. Read Students");
            System.out.println("4. Delete Student");
            System.out.println("Enter 'n' to exit:");

            option = scanner.next().charAt(0);

            switch (option) {
                case '1':
                    createStudent(factory);
                    break;
                case '2':
                    updateStudent(factory);
                    break;
                case '3':
                    readStudent(factory);
                    break;
                case '4':
                   deleteStudent(factory);
                    break;
                case 'n':
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }

        } while (option != 'n');

        factory.close();
        scanner.close();
    }

    private static void createStudent(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Take input from the user for Student details
            System.out.println("Enter Student ID:");
            long studentid= scanner.nextLong();

            System.out.println("Enter First Name:");
            String firstname = scanner.next();

            System.out.println("Enter Last Name:");
            String lastName = scanner.next();

            System.out.println("Enter Mobile:");
            String mobile = scanner.next();

            System.out.println("Enter Email:");
            String email = scanner.next();
           
            
            // Create a new Student instance
            Student st = new Student();
            st.setStudentid(studentid);
            st.setFirstname(firstname);
            st.setLastname(lastName);
            st.setMobile(mobile);
            st.setEmail(email);
          
            // Save the Student to the database
            session.save(st);

            transaction.commit();
            System.out.println("Student created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Prompt the user for a student ID
            System.out.println("Enter student ID to update:");
            long studentIdToUpdate = scanner.nextLong();

            // Retrieve the student from the database based on the ID
            Student studentToUpdate = session.get(Student.class, studentIdToUpdate);

            if (studentToUpdate != null) {
                // Display current student mobile number
                System.out.println("Current Mobile Number: " + studentToUpdate.getMobile());

                // Update student mobile number based on user input
                System.out.println("Enter new Mobile Number:");
                String newMobileNumber = scanner.next();
                studentToUpdate.setMobile(newMobileNumber);

                // Save the updated student
                session.update(studentToUpdate);
                transaction.commit();
                System.out.println("Mobile Number updated successfully.");
            } else {
                System.out.println("Student with ID " + studentIdToUpdate + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void readStudent(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Query to fetch all students
            Query stt = session.createQuery("from student_crud");
            Iterator it   = ((org.hibernate.Query) stt).iterate();

            // Display student details
            while (it.hasNext()){
            	Student st = (Student) it.next();
                System.out.println("ID: " + st.getStudentid() +
                        "\tFirstName: " + st.getFirstname() +
                        "\tLastName: " + st.getLastname() +
                        "\tMobile: " + st.getMobile() +
                        "\tEmail: " + st.getEmail());
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static void deleteStudent(SessionFactory factory) {
    	try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
long studentid;
// Prompt the user for a student ID
System.out.println("Enter student ID to delete:");
 studentid = scanner.nextLong();
Student st = session.get(Student.class,studentid);
session.delete(st);
transaction.commit();
System.out.println("Student Deleted");
    	}
    }
}
