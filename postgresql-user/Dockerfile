FROM postgres:15.2-alpine
MAINTAINER YR

ADD /user-service/ddl/*.sql /docker-entrypoint-initdb.d/
ADD /user-service/dml/*.sql /docker-entrypoint-initdb.d/