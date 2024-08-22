FROM maven:3.9.9-sapmachine-22
WORKDIR .
COPY . .
CMD ["mvn","-Dtest=AndroidTest", "test"]
