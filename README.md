# spring-kafka-play
This is a playground for learning kafka in Spring boot
[Open in gitpod](https://gitpod.io/#https://github.com/tamnm/spring-kafka-play)

## add hosts for kafka
```
#file: /etc/hosts

127.0.0.1 kafka
```

## Install java
```bash
sudo apt-get install -y openjdk-11-jre
```

## Install maven
```bash
sudo apt-get install -y maven
```

## Run the kafka
```bash
docker-compose up -d
```

## run the service
```bash
mvn spring-boot:run
```

## Simulate the job
```
# simulate a 10s duration job
curl -H "Content-Type: application/json" -d '{"duration":10}' http://localhost:8080/mail/job
```
