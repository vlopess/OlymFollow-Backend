#!/bin/bash

CURRENT_DIR=$(pwd)

check_app_running() {
    until $(curl --output /dev/null --silent --head --fail http://localhost:$1/actuator/health); do
        printf '.'
        sleep 5
    done
    echo " -> Aplicação rodando na porta $1"
}

cleanup() {
    echo "Interrompido. Finalizando aplicações..."
    kill $EUREKA_PID $GATEWAY_PID $EMAIL_PID $MONOLITO_PID
    exit 0
}

# Iniciar Eureka Server
echo "Iniciando Eureka Server..."
cd "$CURRENT_DIR/eureka-server"
mvn spring-boot:run &
EUREKA_PID=$!
check_app_running 8761

cd ..

# Iniciar Gateway
echo "Iniciando Gateway..."
cd "$CURRENT_DIR/gateway-olympicsfollow"
mvn spring-boot:run &
GATEWAY_PID=$!

cd ..

# Iniciar API 1
echo "Iniciando email ms..."
cd "$CURRENT_DIR/EmailOlympicsFollow"
mvn spring-boot:run &
EMAIL_PID=$!

cd ..

# Iniciar API 2
echo "Iniciando API ..."
cd "$CURRENT_DIR/olympics-follow-api"
mvn spring-boot:run &
MONOLITO_PID=$!


# Configura o trap para SIGINT (Ctrl+C)
trap cleanup SIGINT

wait $EUREKA_PID $GATEWAY_PID $EMAIL_PID $MONOLITO_PID

# Configura o trap para SIGINT (Ctrl+C)
trap cleanup SIGINT
