# spring-kafka-play
This is a playground for learning kafka in Spring boot

## Setup Kafka using helm

Setup kafka by follow the instruction here 👉https://bitnami.com/stack/kafka/helm

## Port-forwarding the borker

```bash
$kubectl port-forward service/my-release-kafka 9092
```

## Edit the hosts file 

```hosts
127.0.0.1 my-release-kafka-0.my-release-kafka-headless.default.svc.cluster.local
```

## Setup the Kafka UI

```bash
helm repo add kafka-ui https://provectus.github.io/kafka-ui
helm install kafka-ui kafka-ui/kafka-ui --set envs.config.KAFKA_CLUSTERS_0_NAME=local --set envs.config.KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=my-release-kafka:9092
```

Details: https://github.com/provectus/kafka-ui/tree/master/charts/kafka-ui
