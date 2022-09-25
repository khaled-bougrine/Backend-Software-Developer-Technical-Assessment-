# Backend Software Developer Technical Assessment

## Task 01
## Project Structure
So the folder structure of this mini-project, will end up looking something like this:
```
.
./eclipse-temurin-service/                 # Our service for eclipse temurin
./nginx/                # Files for configuration of our NGINX instance
./docker-compose.yml
```
#### eclipse-temurin-service/Dockerfile
```docker
FROM eclipse-temurin:11
RUN mkdir /opt/app
COPY target/eclipse-temurin-service-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 9443
CMD ["java", "-jar", "/opt/app/eclipse-temurin-service-0.0.1-SNAPSHOT.jar"]

```

#### nginx/nginx.conf
```nginx
events {
    worker_connections 1024;
}

http {

    resolver 127.0.0.11 valid=10s;

    server {
        listen 443 ssl;

        ssl_certificate /etc/nginx/ssl/nginx.crt;
        ssl_certificate_key /etc/nginx/ssl/nginx.key;


        location / {
            proxy_pass http://temurin:9443;
        }

    }
}
```
#### ./docker-compose.yml
```yaml
version: '3'
services:
  temurin:
    build: eclipse-temurin-service/.
  nginx:
    image: nginx
    ports:
      - "443:443"
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf:ro
      - ./nginx/ssl:/etc/nginx/ssl:ro
      - ./nginx/index.html:/app/html:ro
```
## Task 02:
An API hosted in the Docker environment that generates an email address based on the parameters of the request and an expression.
## Input
this api can accept any number of query parameters named "input1", "input2", "input3"...
### Example
So the folder structure of this mini-project, will end up looking something like this:
```
.
Input1  :  “Jean-Louis”
Input2  :  “Jean-Charles Mignard” 
Input3  :  “external”
Input4  :  “peoplespheres” 
Input5  : “fr” 

```
the expression is a parameter called expression
```
.

expression :input1.eachWordFirstChars(1) ~ '.' ~ (input2.wordsCount() > 1 ? input2.lastWords(- 1).eachWordFirstChars(1) ~ input2.lastWords(1) : input2 ) ~ '@' ~ input3 ~ '.' ~ input4 ~ '.' ~ input5

```
this expression can 
- Take any number of characters from inputs at any position from any word or all words
- Concatenate selected characters or string
- Filter prohibited email characters

```
.
input1.eachWordFirstChars(1)  # get a character from the first index of all words in input number 1
input1.eachWordSecondChars(2)  # get two characters from the secondary index of all words in input number 1
.
etc

input1.eachWordLastChars(2)                  # Our service for delivering tea
input1.lastWords(1)              # get the last word of input number 1
input1.firsttWords(1)              # get the first word of input number 1
input1.SecondtWords(1)              # get the Second word of input number 1
etc

```
test the api 
``` 
$ curl http://localhost:8080/api/v1.0/emails-generator?input1=Jean-Louis&input2=Jean-Charles Mignard&input3=external”&input4=peoplespheres&input5=fr
&expression=input1.eachWordFirstChars(1) ~ '.' ~ (input2.wordsCount() > 1 ? input2.lastWords(-1).eachWordFirstChars(1) ~ input2.lastWords(1) : input2 ) ~ '@' ~ input3 ~ '.' ~ input4 ~ '.' ~ input5
> 
{
"data": [
{
"id": "jl.jccharlesmignard@external.peoplespheres.fr",
"value": "jl.jccharlesmignard@external.peoplespheres.fr"
}
]
}


```


