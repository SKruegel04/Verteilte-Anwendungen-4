# Aufgabe 1

- Dienste starten mit
  
  ```bash
  docker-compose up
  ```
  
  > **Hinweis (Windows 11):**
  > Sollte ein Port-Fehler auftreten, z.B.
  > 
  > Error response from daemon: Ports are not available: exposing port TCP 0.0.0.0:8080 -> 0.0.0.0:0: listen tcp 0.0.0.0:8080: bind: An attempt was made to access a socket in a way forbidden by its access permissions.
  > einmal
  > 
  > net stop winnat
  > net start winnat
  > 
  > ausführen und dann nochmal versuchen.
- Kafka UI unter http://localhost:8080/ aufrufen
- Links zu "Topics" und dann "Add a topic" anklicken
  - Topic Name: twitter
  - Number of Partitions: 2
  - Time to retain data (in ms): 604800000 (7 Tage)
    - Tweets haben eine hohe Aktualitätsrate und sind nach 7 Tagen nicht mehr relevant
  - "Create Topic" anklicken
- Datei [src/main/resources/application.properties](src/main/resources/application.properties) öffnen
- Dort folgende Konfiguration hinzufügen:
  
  ```
  # Twitter Consumer
  mp.messaging.incoming.twitter.connector=smallrye-kafka
  mp.messaging.incoming.twitter.topic=twitter
  mp.messaging.incoming.twitter.value.deserializer=org.apache.kafka.common.serialization.StringDeserializer

  # Twitter Producer
  mp.messaging.outgoing.twitter.connector=smallrye-kafka
  mp.messaging.outgoing.twitter.topic=twitter
  mp.messaging.outgoing.twitter.value.serializer=org.apache.kafka.common.serialization.StringSerializer
  ```
  
  Das ist Konfiguration für das Lesen von Messages (Consumer, Incoming) sowie
  das Schreiben von Messages (Producer, Outgoing) auf das Topic "twitter".

# Aufgabe 2

- Datei [src/main/java/de.berlin.htw/boundary/dto/TwitterProducer.java](src/main/java/de.berlin.htw/boundary/dto/TwitterProducer.java) öffnen
- Wir entfernen die bisherige `Event<Tweet>`-Implementierung (Property `twitterChannel`) inkl. Annotations
- Wir fügen stattdessen folgende Property hinzu:
  
  ```java
  class TwitterProducer {
    // ...
    @Inject
    @Channel("twitter-producer")
    Emitter<String> producer;
    // ...
    public void sendMessage(final String message) {
        producer.send(message);
    }
    // ...
  }
  ```
  
  Alles andere darin kann entfernt werden.
- Datei [src/main/java/de.berlin.htw/boundary/dto/TwitterConsumer.java](src/main/java/de.berlin.htw/boundary/dto/TwitterConsumer.java) öffnen
- Wir entfernen die beiden Methoden `onEvent` und `onAsyncEvent` inkl. Annotations und fügen stattdessen folgende ein:
  
  ```java
    class TwitterConsumer {
        // ...
        @Incoming("twitter-consumer")
        public void consume(String tweet) {
            try {
                queue.put(tweet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // ...
    }
  ```
  
  Dieses Prinzip wurde vom `FibonacciConsumer` übernommen.
- Damit wir die letzten 10 Tweets konsistent anzeigen können, speichern wir diese im Consumer in einer Liste
- Dazu fügen wir folgende Property hinzu:
  
  ```java
  class TwitterConsumer {
    // ...
    private final List<String> lastTweets = new ArrayList<>(10);
    // ...
    @Incoming("twitter-consumer")
    public void consume(String tweet) {
        try {
            queue.put(tweet);
            if (lastTweets.size() > 10) {
                lastTweets.remove(0);
            }
            lastTweets.add(tweet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // ...
  }
  ```
- Weiteres Terminal öffnen (Das `docker compose up` terminal offen lassen)
- Applikation bauen und ausführen:

  ```shell
  mvn compile quarkus:dev "-Dquarkus.profile=primary"
  ```
  
  Zweite Instanz ebenfalls starten:

  ```shell
  mvn compile quarkus:dev "-Dquarkus.profile=secondary"
  ```
- http://localhost:8081/ aufrufen, nach 10 Sekunden sollten neue Tweets erscheinen
- http://localhost:8082/ aufrufen, nach 10 Sekunden sollten neue Tweets erscheinen
