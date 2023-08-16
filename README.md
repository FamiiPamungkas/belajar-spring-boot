# Build
Build Project into jar files with this command :
```
mvn clean package
```

# Running 

Specify active profiles based on environment :

```
java -jar .\security-0.0.1-SNAPSHOT.jar --spring.profiles.active=stb
```
# Deploy
## 1. Create Service
Create service located in /etc/systemd/system/<service-name>.service
```
[Unit]
Description=<description>
After=network.target

[Service]
Type=simple
User=<user>
ExecStart=/path/to/<script-name>.sh
WorkingDirectory=/path/to/script-directory
Restart=always

[Install]
WantedBy=multi-user.target
```
## 2. Create Script
Create script that run jar files, example :
```
#!/bin/bash

ACTIVE_PROFILE="stb"

java -jar <app-name>.jar --spring.profiles.active=$ACTIVE_PROFILE
```
## 3. Run Service
Run Service using this command :
```
systemctl start <service-name>.service
```
