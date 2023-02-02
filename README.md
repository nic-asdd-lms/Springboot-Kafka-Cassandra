# Springboot-Kafka-Cassandra
Basic implemantation of Springboot, Kafka and Cassandra integration


## Setup

1. Create kafka topic orgCreation
2. Create keyspace `lms` and table `org` in cassandra

## Run

1. Start kafka and cassandra
2. Run `organisation-management/src/main/java/org/igot/OrgManagement.java`
3. Run `data-management/src/main/java/org/igot/DataManagement.java`
4. Execute curl:
```
curl --location --request POST 'http://localhost:7001/org/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "mapid": "546546",
"orgcode": "S52324",
"orgname": <Org Name>,
"parentmapid": <value>,
"sborgid": null,
"sborgtype": null,
"sbrootorgid": null,
"sbsuborgtype": null
}'
```
