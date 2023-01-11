# Verteilte Anwendungen Übungsaufgabe 4

Mögliche Punktzahl: 20 Punkte

## Deadlines

- 1. Zug 1. Gruppe: 06.02.2023
- 1. Zug 2. Gruppe: 09.02.2023
- 2. Zug 1. Gruppe: 07.02.2023
- 2. Zug 2. Gruppe: 07.02.2023 

## Aufgabestellung
In dieser Aufgabe erhalten Sie kein vollständig vorkonfigurietes Project; 
dafür jedoch ein existierendes Docker Compose File. 
Dieses Docker Compose File fährt bereits alle benötigten Komponenten für Sie hoch.
Um sich eien Überblick über die Server-Instanzen zu verschaffen, empfielt es sich einen Blick in die [docker-compose.yml](docker-compose.yml) zu werfen.

> Mit dieser Aufgabe wollen wir Elon Mask zum Zittern bringen in dem wir mittels
Kafka ihre Applikation zum Twittern bringen.

Genaugenommen können Sie bereits auf Tweets zugreifen und die neusten 
Posts Ihrer Kommelitonen lesen. Nach dem Start finden Sie ein 
VA Twitter Channel unter [http://localhost:8081](http://localhost:8081)
Leider sind diese Infromationen nur generiert und auch nur lokal 
auf einer Instanz verfügbar. 
Die Herausforderung dieser Augabe besteht darin, die Tweets nach Kafka zu schreiben bzw. aus Kafka zu lesen und somit auf allen Instanzen ihrer Applikation 
darzustellen.

1.  **(4P)** Konfigurieren Sie ein Kafka Topic namens "twitter".
Dieses Topic muss mindestens **zwei** Partitionen besitzen!
Entscheiden Sie sich für eine geeignete Retention und begründen Sie Ihre
Entscheidung bei der Projektabgabe.
Konfigurieren Sie das von Ihnen erzeugte Topic in der
[application.properties](src/main/resources/application.properties).
2.  **(4P)** Konfigurenen Sie Bauen Sie mit Hilfe von Maven die Quarkus Applikation sowie das entsprechende Docker Image. Starten sie einen Container mit der Quarkus Applikation und testen Sie die RESTful Webservices mit Hilfe eines REST Clients (z.B. curl oder Insomnia).

Sie haben isandiaos

3.  **(4P)** Konfigurieren Sie ein Kafka Topic namens "fibonacci".
Dieses Topic muss mindestens **zwei** Partitionen besitzen!
Entscheiden Sie sich für eine geeignete Retention und begründen Sie Ihre
Entscheidung bei der Projektabgabe.
Konfigurieren Sie das von Ihnen erzeugte Topic in der
[application.properties](src/main/resources/application.properties).
4.  **(8P)** Erweitern sie die Quarkus Applikation, um einen weiteren REST Endpunkt. Dieser soll unter dem Pfad `/aufgaben/1/` eine Ressource namens „zahl“ vom Media Type ``application/example`` bereitstellen. Der REST Endpunkt soll vier Methoden unterstützen: 1. Initiales Anlegen einer Zahl, 2. Abrufen der aktuellen Zahl, 3. Aktualisieren einer Zahl und 4. Löschen einer Zahl. 



# Kafka Get Started

Da es leider für [Apache Kafka](https://kafka.apache.org/) kein
offizielles Image auf Docker Hub gibt,
erhalten Sie in dieser Aufgabe ein Docker Compose File
mit einer Referenz-Installation.

Zum Starten des Kafka Clusters können Sie Docker Compose im Detached Modus ausführen:
```shell script
$docker-compose up -d
```
> **_Achtung:_**  Diese Referenz-Installation stellt ein Kafka Management UI bereit, das Sie über Ihren Browser unter http://localhost:8080/ erreichen können.

# Quarkus Get Started

Dieses Projekt nutzt Quarkus, das Supersonic Subatomic Java Framework.
Da wir in dieser Aufgabe zwei Instanzen der Quarkus Applikation paralell
betreiben wollen und immer nur ein Port von einem Prozess verwendet
werden kann, wurden zwei Profile konfiguriert:
- primary
- secondary

Wenn sie mehr über die Profil-Konfiguration wissen wollen,
dann besuchen Sie bitte die Quarkus Website:
[Configuration Reference Guide](https://quarkus.io/guides/config-reference) .

## Running the application in dev mode

Sie können die primäre Instanz Ihrer Applikation mit dem folgenden Kommando
im dev Mode starten:
```shell script
$mvn compile quarkus:dev -Dquarkus.profile=primary
```
Und die sekundäre Instanz Ihrer Applikation mit:
```shell script
$mvn compile quarkus:dev -Dquarkus.profile=secondary
```

> **_Achtung:_**  Quarkus wird im dev Mode mit einer Dev UI ausgeliefert,
die Sie entweder unter http://localhost:8081/q/dev/ oder unter
http://localhost:8082/q/dev/ erreichen können.

## Packaging and running the application

The application can be packaged using:
```shell script
$mvn package
```
It produces the `verteilte-anwendungen-kafka-1.0.0-SNAPSHOT-runner.jar` file in the `target/` directory.

The primary application instance is now runnable using 
`$java  -Dquarkus.profile=primary -jar target/verteilte-anwendungen-kafka-1.0.0-SNAPSHOT-runner.jar`
and the secondary by using
`$java  -Dquarkus.profile=secondary -jar target/verteilte-anwendungen-kafka-1.0.0-SNAPSHOT-runner.jar`.
