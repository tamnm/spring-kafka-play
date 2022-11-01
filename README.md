# spring-kafka-play
This is a playground for learning kafka in Spring boot
[Open in gitpod](https://gitpod.io/#https://github.com/tamnm/spring-kafka-play)

## add hosts for kafka

The gitpod will do this step automatically.
if the `127.0.0.1 kafka` does not exist in the hosts then we need to add manually

```
#file: /etc/hosts

127.0.0.1 kafka
```

## Install java

The gitpod will do this step automatically.
if `java` does not exist in the hosts then we need to add manually

```bash
sudo apt-get install -y openjdk-11-jre
```

## Install maven

The gitpod will do this step automatically.
if `mvn` does not exist in the hosts then we need to add manually

```bash
sudo apt-get install -y maven
```

## Run the kafka

Using docker-compose to  start the kafak (Zookeper, kafka borker, kafka UI).

```bash
docker-compose up -d
```

## run the service

To start the spring-boot service: use the `mvn`

```bash
mvn spring-boot:run
```

## Simulate the job

To send a single request:

```
# simulate a 10s duration job
curl -H "Content-Type: application/json" -d '{"duration":10}' http://localhost:8080/mail/job
```

## Load Test

To send a numbers of the request
```bash
hey -n ${total-request} -c ${concurrent} -m POST -d '{"durtion": ${dur_in_second}}' -H "Content-Type: application/json" http://localhost:8080/mail/job

#example to send 1000 request with 10s job
hey -n 1000 -c 100 -m POST -d '{"durtion": 10}' -H "Content-Type: application/json" http://localhost:8080/mail/job

```
## To get the public URL for the Web
```
gp url 8080
```

## To get the public URL for kafka UI
```
gp url 8081
```