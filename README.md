# smartVienna
Statistics tool for the vienna public transport system

---------------------------------------------------------
This system consists of 3 different parts:

1. Sensor program
2. rabbitMQ (CloudAQMP)
3. SmartVienna Application

## Sensor program

In order to emulate a IoT sensor a maven project is responsible for accessing the vienna public transport API and publishing the data to rabbitMQ. The sensor program can also adapt the interval of the request for a specific station.

This program can be found in /sensor_smartVienna

Build and start this program:

1. mvn install
2. mvn exec:java -D exec.mainClass=Main

## CloudAMQP (RabbitMQ as a Service)

The RabbitMQ as a Service CloudAMQP offers a free MOM instance. The sensor program publish the data a queue and the SmartVienna Application fetches and processes the data.

## SmartVienna Application

This application runs on a ec2 instance.

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
