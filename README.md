# This application is a collaboration between two University Students

The original project can be found here -> https://github.com/catman85/JavaFX-Student-Management-System

# JavaFX Student Management System
A Java application for managing students teachers, and grades.

# Screenshots

| Login Screen  |  Student Enroll
|:-:|:-:|
| ![login_screen](https://user-images.githubusercontent.com/20374208/45230776-a66f7a00-b2d2-11e8-9900-21e8bd812cfe.jpg) | ![student enroll](https://user-images.githubusercontent.com/20374208/45230777-a66f7a00-b2d2-11e8-8830-e81f8f27cb8f.jpg) |

| Student Register  |  Student Screen
|:-:|:-:|
| ![student_register](https://user-images.githubusercontent.com/20374208/45230778-a7081080-b2d2-11e8-9c52-4b7a9da092ac.jpg) | ![student_screen](https://user-images.githubusercontent.com/20374208/45230779-a7081080-b2d2-11e8-8b3e-0a687c1d79af.jpg) |

| Teacher Register  |  Teacher Screen
|:-:|:-:|
| ![teacher_register](https://user-images.githubusercontent.com/20374208/45230780-a7081080-b2d2-11e8-8753-7ad67f63def7.jpg) | ![teacher_screen](https://user-images.githubusercontent.com/20374208/45230781-a7081080-b2d2-11e8-99ac-f6a29f74fa32.jpg) |

## Specs / Open-source libraries:

- [**Ikonli Font Icons packs**](https://aalmiray.github.io/ikonli) Ikonli provides icon packs that can be used in Java applications. Currently Swing and JavaFX UI toolkits are supported.
- [**JFoenix**](https://github.com/jfoenixadmin/JFoenix)  JavaFX Material Design Library .
- [**Sqlite-jdbc**](https://github.com/xerial/sqlite-jdbc) SQLite JDBC Driver .
- [**FX-BorderlessScene**](https://github.com/goxr3plus/FX-BorderlessScene) Undecorated JavaFX Scene with implemented move, resize, 

# How to fork and support this project

To build XR3Player, you will need:

* [JDK 9+]
* [Maven](http://maven.apache.org/) - Version 3.5.3++ recommended

Follow the above instructions and run ``mvn clean package`` , be sure that you are compiling with Java 9

## How to build

This is a Maven project you just use :

```MAVEN
mvn clean install 
```


## Use Case
Teachers and Students register in the platform.
Students log in the platform to enroll in courses and see the grades they received.
Teachers log in the platform to view their students and give them grades.

#### The "passwords.txt" file
In the root directory of the repo you will find 
a file with a list of passwords for students and teachers.
Feel free to delete the "my.db" file that is created by SQLite and start a fresh one.
