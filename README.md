
# NoteTaking PRO

## Overview  
A simple to-do application developed with Java Swing, offering a clean and minimalistic dark mode design. Created to learn and explore Java Swing for GUI development.

## Features  
- Clean and Minimalistic Design: Welcoming and intuitive interface.
- Customizable Notes: Create and personalize notes according to your preferences.
- Organize Notes: Sort your notes either alphabetically or by the most recent modifications.
- Star Important Notes: Highlight important notes for easy access.
- Mark Notes as Done: Keep track of completed tasks or notes.
- Recycle Bin: Restore deleted notes with ease using the bin feature.
- Simple Settings: Configure settings effortlessly.

## Showcasing the application

![UsernameFrame](applicationImages/UsernameFrame.png)
![WelcomingFrame](applicationImages/WelcomingFrame.png)
![HomeMenu](applicationImages/HomeMenu.png)
![EditingNote](applicationImages/EditingNote.png)
![StaredMenu](applicationImages/StaredMenu.png)
![DoneMenu](applicationImages/DoneMenu.png)
![BinMenu](applicationImages/BinMenu.png)
![SettingsMenu](applicationImages/SettingsMenu.png)

## How to install and test it

### Pre-requisites
- Install JRE (Java RunTime Environment, which include libraries, Java Virtual Machine (JVM), and other important components to run a java application).
  - To check if it's already installed open the Command Prompt or terminal and type 'java -version' and press enter, it should display the version of the JRE if installed (Version 17 or above reccomended). 

- Using the Oracle JRE download page
  
  - Access https://www.oracle.com/pt/java/technologies/downloads,
  - Download the corresponding installer,
  -  Run the installer,
  - Follow the installation instructions,
  - Restart your machine,
  - To verify if the installation was a success open the Command Prompt or terminal and type `java -version` and press enter, it should display the version of the JRE installed. 

- Alternative using Linux (Ubuntu)
  
  Open the terminal,
  Type the following:
  ```
    sudo apt update
    sudo apt install openjdk-17-jre 
  ```

### After installing the JRE
1) Run the program by downloading the code from this repository (Code > Dowload ZIP) and save it in a location of your choice
2) Navigate to/Open the corresponding 'RunNoteTakingPRO' (Windows/Linux/Mac) folder
3) Run/Double click the 'runNoteTakingPRO.bat' or 'runNoteTakingPRO.sh' file
4) If it doesn't work you may need to grant executable permissions to this script to ensure it can run the application.  
On linux: `chmod +x runNoteTakingPRO.sh`
6) The application should be running correctly.

Alternatively just open your Command Prompt or Terminal, navigate to the directory where the .jar file is located and run the following code:  
`java -jar NoteTakingPRO.jar`

## License  
Although the code was developed solely by me, you are free to test and modify it on your machine as you wish.
