# PVZHeroes-Stats
"Plants vs Zombies: Heroes(TM)" is a phone strategy game created in 2016 by Electronic Arts. At December 2020, it included 579 different cards, each containing different values for 13 fields. This means that the whole desk has around 7500 values, most of which have some kind of influence in the game output.

"PVZHeroes-Stats" is an application to help players to visualize and understand that huge amount of data by two ways: launching queries and creating charts. Among its main functionalities, there are:

- Cards searching applying different filters, either using the GUI or SQL.

![ModoBásico](https://user-images.githubusercontent.com/40747197/101830325-a2c3a100-3b34-11eb-925b-31c4e8a5be0d.JPG)

- Calculation of the values of functions (average, count, sum...) for groups of cards.

![ModoAvanzado](https://user-images.githubusercontent.com/40747197/101830461-d272a900-3b34-11eb-887c-d9510cb2dc1b.JPG)

- Creation of 4 different types of customizable charts, using multiple series.

![ModoGráficos](https://user-images.githubusercontent.com/40747197/101830400-bd961580-3b34-11eb-87e5-c39f134dab29.JPG)

- Customization of the app and a thorough interactive and integrated user's guide ("Sistema de Ayuda").

![image](https://user-images.githubusercontent.com/40747197/101831254-f71b5080-3b35-11eb-91e5-6e8607336498.png)

There are 3 optiones to make it run:
1. Download the installer from <a href="https://www.patreon.com/posts/pvz-heroes-stats-43893640">this link</a> and run it. You will get a manual, two exe files and a folder named "Recursos". When you run any of the programs, make sure they're in the same directory as "Recursos".
2. Download the jar files and execute them, keeping them in the same directory as "Recursos".
3. Compile the project by yourself. Take into account you will also need to specify the path to the libraries in the jar of PVZHeroes-Stats by following the next steps: 1) Open the JAR with WinRar, 2) Access to "META-INF/MANIFESTMF" and open it, 3) Sustitute the line that starts with  "ClassPath" with "Class-Path: recursos/recursosPrograma/librerías/controlsfx-8.40.14.jar recursos/recursosPrograma/librerías/sqlite-jdbc-3.30.1.jar", 4) Save the modification.

Last JAR update: December 2020.
Last installer and executables update: November 2020.
