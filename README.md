# smartVienna
Statistics tool for the vienna public transport system

With this program it is possible to track statios of the vienna public transport system.

---------------------------------------------------------
## Design

The main goal is to implement a platform which allows to analyze the delays of a stations of the Vienna public transport system. Therefore, the real-time data API from the Vienna public transport is used to fetch the data with sensors and publish the data to a message broker, i.e. rabbitMQ. In this scenario, the sensors run a lightweight Java application which pulls the data from the API and publish it to the message broker. Such sensors could run on a Raspberry PI or, due to simplicity, on a local client. The analyzing and processing happens in a sping boot application which is responsible for analyzing, storing and presenting the data for the end customer. 

The data for the station and the real time api of the Wiener Linien can be found here:
https://www.data.gv.at/katalog/dataset/add66f20-d033-4eee-b9a0-47019828e698

This system consists of 3 different parts:

1. Sensor program
2. rabbitMQ (CloudAQMP)
3. SmartVienna Application

## Sensor program

In order to emulate a IoT sensor a maven project is responsible for accessing the vienna public transport API and publishing the data to rabbitMQ. The sensor program can also adapt the interval of the request for a specific station.

This program can be found in /sensor_smartVienna

Please insert the URI of rabbitMQ CloudAMQP in Main.java and in RequestThread.java (see java comments).
Please also insert a valid Wiener Linien API Key in the RequestThread.java (see java comments).

Build and start this program:

1. mvn install
2. mvn exec:java -D exec.mainClass=Main

## CloudAMQP (RabbitMQ as a Service)

The RabbitMQ as a Service CloudAMQP offers a free MOM instance. The sensor program publish the data a queue and the SmartVienna Application fetches and processes the data.

## SmartVienna Application

This application runs on a ec2 instance.
Please insert the URI of rabbitMQ CloudAMQP in MonitorStations.java (see comments).

### Frontend

As frontend a angular 4 app displays all station details and the delays.

### Backend

As backend a spring boot application analyzed the data from the message queue, calculate the statistics and offers the REST API for the angular frontend.

Build and start this program:

1. ./mvnw
2. yarn start

## Access the application

The service runs on ec2 instance on port 8080.
For example:
http://ec2-54-201-136-143.us-west-2.compute.amazonaws.com:8080
