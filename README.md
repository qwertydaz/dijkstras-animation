# Final Year Project - Dijkstra's Animation

This is my code for my final year project.

## How to run

1. Clone the repository
2. Download the dependencies
3. Use the specified VM options

### Dependencies

1. [JDK 15.0.2](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)
2. [JavaFX 19](https://gluonhq.com/products/javafx/)
3. [MySQL Connector 8.0.31](https://dev.mysql.com/downloads/connector/j/)

### VM Options

```
--module-path {pathToFolder}\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\lib --add-modules=javafx.controls
```

### MySQL Database

This program uses MySQL to save and load graphs.
If you want this functionality to work, you will need to set up a database and a CSV file.

Create a file called "database_details.csv" in the following directory:

```
./src/resources/database_details.csv
```

The CSV file should contain the connection details. It must be formatted as follows:

```
{url},{username},{password}
```