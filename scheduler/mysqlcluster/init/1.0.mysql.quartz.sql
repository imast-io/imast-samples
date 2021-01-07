CREATE DATABASE quartz_scheduler;

USE quartz_scheduler;

CREATE USER 'workeruser'@'%' IDENTIFIED BY 'workerpassword';
GRANT ALL PRIVILEGES ON quartz_scheduler.* TO 'workeruser'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;


