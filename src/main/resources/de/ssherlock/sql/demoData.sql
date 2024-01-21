INSERT INTO faculty (name) VALUES ('Informatik'), ('Mathematik'), ('Wirtschaftsinformatik');

INSERT INTO course (course_name) VALUES ('Programmierung I'), ('Programmierung II'), ('Algorithmen und Datenstrukturen'),
                                        ('Software Testing'), ('Software Engineering'), ('Technische Informatik'),
                                        ('Competitive Programming'), ('Theoretische Informatik'), ('Grundlagen der Informatik'),
                                        ('Verteilte Systeme');

-- Exercises for 'Programmierung I'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
    ('Introduction to Variables and Data Types', '2024-01-19', '2024-02-05', '2024-02-12', 'Write a program in a language of your choice to declare variables and demonstrate basic data types.', 1, 0),
    ('Control Flow: Loops and Conditional Statements', '2024-01-22', '2024-02-12', '2024-02-19', 'Create a program that utilizes loops and conditional statements to solve a simple problem.', 1, 0),
    ('Functions and Modular Programming', '2024-01-25', '2024-02-19', '2024-02-26', 'Design a program with functions to perform a specific task, demonstrating the concept of modular programming.', 1, 0),
    ('Arrays and Strings', '2024-01-28', '2024-02-26', '2024-03-04', 'Write a program to manipulate arrays and strings, showcasing your understanding of these data structures.', 1, 0),
    ('Error Handling and Debugging', '2024-02-01', '2024-03-04', '2024-03-11', 'Create a program that includes error handling mechanisms and demonstrate effective debugging techniques.', 1, 0);

-- Exercises for 'Programmierung II'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
    ('Object-Oriented Programming Concepts', '2024-02-05', '2024-03-11', '2024-03-18', 'Implement a program using object-oriented programming principles, focusing on encapsulation, inheritance, and polymorphism.', 2, 0),
    ('File Handling and I/O Operations', '2024-02-08', '2024-03-18', '2024-03-25', 'Develop a program that involves reading from and writing to files, showcasing your understanding of input/output operations.', 2, 0),
    ('Exception Handling in OOP', '2024-02-12', '2024-03-25', '2024-04-01', 'Create a program that demonstrates the use of exception handling within an object-oriented context.', 2, 0),
    ('Advanced Data Structures', '2024-02-15', '2024-04-01', '2024-04-08', 'Implement and use advanced data structures like linked lists, stacks, and queues in your program.', 2, 0),
    ('Graphical User Interface (GUI) Development', '2024-02-19', '2024-04-08', '2024-04-15', 'Design a simple graphical user interface for a program, incorporating GUI elements and event handling.', 2, 0);

-- Exercises for 'Algorithmen und Datenstrukturen'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
    ('Sorting Algorithms', '2024-02-22', '2024-04-15', '2024-04-22', 'Implement and analyze different sorting algorithms such as bubble sort, quicksort, and merge sort.', 3, 0),
    ('Searching Algorithms', '2024-02-26', '2024-04-22', '2024-04-29', 'Develop a program that utilizes searching algorithms like linear search and binary search on different data structures.', 3, 0),
    ('Dynamic Programming', '2024-03-01', '2024-04-29', '2024-05-06', 'Solve a complex problem using dynamic programming techniques and optimize your solution for efficiency.', 3, 0),
    ('Graph Algorithms', '2024-03-04', '2024-05-06', '2024-05-13', 'Implement graph algorithms such as breadth-first search (BFS) and depth-first search (DFS) in a practical scenario.', 3, 0),
    ('Algorithmic Problem Solving', '2024-03-08', '2024-05-13', '2024-05-20', 'Solve algorithmic problems and discuss the efficiency and correctness of your solutions.', 3, 0);

-- Exercises for 'Software Testing'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
    ('Introduction to Software Testing', '2024-03-11', '2024-05-20', '2024-05-27', 'Write test cases for a given program and understand the importance of testing in the software development life cycle.', 4, 0),
    ('Unit Testing', '2024-03-15', '2024-05-27', '2024-06-03', 'Implement and execute unit tests for a small codebase, focusing on isolating and testing individual components.', 4, 0),
    ('Integration Testing', '2024-03-18', '2024-06-03', '2024-06-10', 'Design integration tests to ensure that different components of a system work seamlessly together.', 4, 0),
    ('System Testing', '2024-03-22', '2024-06-10', '2024-06-17', 'Create a comprehensive system test plan and execute tests to validate the entire software system.', 4, 0),
    ('Automated Testing', '2024-03-25', '2024-06-17', '2024-06-24', 'Implement automated tests for a given software project, exploring tools and frameworks for automation.', 4, 0);

-- Exercises for 'Software Engineering'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
    ('Requirements Analysis', '2024-03-29', '2024-06-24', '2024-07-01', 'Gather and document requirements for a software project, considering both functional and non-functional aspects.', 5, 0),
    ('System Design', '2024-04-01', '2024-07-01', '2024-07-08', 'Create a detailed system design for a software project, including architecture, modules, and interfaces.', 5, 0),
    ('Implementation and Coding Standards', '2024-04-05', '2024-07-08', '2024-07-15', 'Write code following established coding standards and best practices for maintainability and readability.', 5, 0),
    ('Code Review and Collaboration', '2024-04-08', '2024-07-15', '2024-07-22', 'Participate in a code review process, providing constructive feedback on your peers code and addressing feedback on your own.', 5, 0),
    ('Software Maintenance and Bug Fixing', '2024-04-12', '2024-07-22', '2024-07-29', 'Identify and fix bugs in an existing codebase, emphasizing the importance of software maintenance.', 5, 0);

-- Exercises for 'Technische Informatik'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
  ('Digital Circuits and Logic Design', '2024-04-15', '2024-07-29', '2024-08-05', 'Design and simulate digital circuits, exploring concepts of logic gates, flip-flops, and registers.', 6, 0),
  ('Processor Architecture', '2024-04-19', '2024-08-05', '2024-08-12', 'Study and analyze the architecture of a processor, focusing on instruction sets, registers, and execution pipelines.', 6, 0),
  ('Memory Hierarchy and Cache Design', '2024-04-22', '2024-08-12', '2024-08-19', 'Explore memory hierarchy in computer systems and design a basic cache memory system.', 6, 0),
  ('Peripheral Devices and Interfaces', '2024-04-26', '2024-08-19', '2024-08-26', 'Understand the role of peripheral devices in computer systems and design interfaces for effective communication.', 6, 0),
  ('Embedded Systems Programming', '2024-04-29', '2024-08-26', '2024-09-02', 'Write programs for embedded systems, considering resource constraints and real-time requirements.', 6, 0);

-- Exercises for 'Competitive Programming'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
  ('Basic Algorithmic Challenges', '2024-05-03', '2024-09-02', '2024-09-09', 'Solve basic algorithmic challenges to strengthen problem-solving skills for competitive programming.', 7, 0),
  ('Dynamic Programming in Competitive Programming', '2024-05-06', '2024-09-09', '2024-09-16', 'Apply dynamic programming techniques to solve more complex problems encountered in competitive programming.', 7, 0),
  ('Graph Theory in Competitive Programming', '2024-05-10', '2024-09-16', '2024-09-23', 'Solve problems involving graph theory concepts like shortest paths, connectivity, and graph traversal.', 7, 0),
  ('Data Structures for Competitive Programming', '2024-05-13', '2024-09-23', '2024-09-30', 'Master the use of various data structures such as heaps, priority queues, and advanced arrays in competitive programming.', 7, 0),
  ('Code Optimization and Contest Strategies', '2024-05-17', '2024-09-30', '2024-10-07', 'Learn techniques for optimizing code and develop effective strategies for competitive programming contests.', 7, 0);

-- Exercises for 'Theoretische Informatik'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
  ('Formal Languages and Automata', '2024-05-20', '2024-10-07', '2024-10-14', 'Understand formal languages and automata theory, and design a finite automaton for a given language.', 8, 0),
  ('Turing Machines and Computability', '2024-05-24', '2024-10-14', '2024-10-21', 'Explore Turing machines and their role in determining the computability of problems.', 8, 0),
  ('Complexity Theory and NP-Hard Problems', '2024-05-27', '2024-10-21', '2024-10-28', 'Study complexity classes and analyze NP-hard problems to understand the limits of algorithmic efficiency.', 8, 0),
  ('Formal Proofs and Mathematical Logic', '2024-05-31', '2024-10-28', '2024-11-04', 'Construct formal proofs using mathematical logic, demonstrating correctness and soundness.', 8, 0),
  ('Algorithmic Decision Problems', '2024-06-03', '2024-11-04', '2024-11-11', 'Solve algorithmic decision problems and analyze their complexity in terms of tractability and decidability.', 8, 0);

-- Exercises for 'Grundlagen der Informatik'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
  ('Introduction to Information Technology', '2024-06-07', '2024-11-11', '2024-11-18', 'Explore the fundamentals of information technology, including hardware, software, and networks.', 9, 0),
  ('Database Management Systems', '2024-06-10', '2024-11-18', '2024-11-25', 'Design a simple relational database schema and write SQL queries to retrieve and manipulate data.', 9, 0),
  ('Web Technologies and HTML/CSS', '2024-06-14', '2024-11-25', '2024-12-02', 'Develop a basic webpage using HTML and CSS, understanding the principles of web technologies.', 9, 0),
  ('Introduction to Data Science', '2024-06-17', '2024-12-02', '2024-12-09', 'Explore basic concepts of data science and analyze a small dataset using statistical methods.', 9, 0),
  ('Computer Ethics and Social Impact', '2024-06-21', '2024-12-09', '2024-12-16', 'Discuss ethical considerations in computing and analyze the social impact of technology.', 9, 0);

-- Exercises for 'Verteilte Systeme'
INSERT INTO exercise (name, publish_date, recommended_deadline, obligatory_deadline, description, course_id, reminder_mail_sent)
VALUES
  ('Introduction to Distributed Systems', '2024-06-24', '2024-12-16', '2025-01-06', 'Understand the basic concepts of distributed systems and design a simple distributed application.', 10, 0),
  ('Network Protocols and Communication', '2024-06-28', '2025-01-06', '2025-01-13', 'Implement communication protocols and design a network architecture for a distributed system.', 10, 0),
  ('Distributed Databases and Consistency Models', '2024-07-01', '2025-01-13', '2025-01-20', 'Explore distributed databases and analyze different consistency models in distributed systems.', 10, 0),
  ('Building a Distributed Application', '2024-03-15', '2024-03-22', '2024-03-29', 'Hands-on exercise to design and implement a distributed application.', 10, 0),
  ('Load Balancing Techniques', '2024-03-01', '2024-03-08', '2024-03-15', 'Examine various load balancing techniques in the context of distributed systems.', 10, 0);

