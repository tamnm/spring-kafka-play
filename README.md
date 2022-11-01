# spring-kafka-play
This is a playground for learning kafka in Spring boot
[Open in gitpod](https://gitpod.io/#https://github.com/tamnm/spring-kafka-play)

## 1. add hosts for kafka

The gitpod will do this step automatically.
<details>
<summary>Add hosts manually</summary>

```bash
echo "127.0.0.1 kafka" | sudo tee -a /etc/hosts
```
</details>
<br/>

## 2. Install java

The gitpod will do this step automatically. 

<details>
<summary>Install the java manual</summary>

```bash
sudo apt-get install -y openjdk-11-jre
```
</details>
<br/>


## 3. Install maven

The gitpod bwill do this step 
automatically. 
<details>
<summary>Install the Maven manual</summary>

```bash
sudo apt-get install -y maven
```
</details>
<br/>

## 4. Install hey

[Hey](https://github.com/rakyll/hey) is the tool for the load testing, similar to the ab tool of Apache.

The gitpod will do this step automatically. 

<details>
<summary>Install Hey manually</summary>

```bash
sudo apt-get install -y hey
```
</details>
<br/>

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
