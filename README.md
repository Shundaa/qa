# Como executar esse projeto.

## WSL - Ubuntu

Se voce estiver no windows, pode instalar o WSL.

Dentro do cmd usar o comando wsl --install.

sudo apt update -y && sudo apt upgrade -y

sudo apt-get install maven -y

sudo apt install openjdk-21-jdk -y

### docker compose

Para instalar use o tutorial

https://docs.docker.com/engine/install/ubuntu/

Siga os passos do Install using the apt repository
Se precisar usar esse tutorial

https://docs.docker.com/compose/install/linux/

ou esse para fazer a instalação do docker no linux

https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-ubuntu-22-04

## Compilar o Projeto

Agora o fazer um clone do projeto:
git clone https://github.com/Shundaa/qa.git

### Acessar o projeto

cd qa/

### Ajustes

Antes de compilar, ajustar o arquivo application.yaml com os dados de conexão do banco.
Se usar os dados do projeto eles ficam dentro do arquivo .env

url: jdbc:mysql://

username:

password:

### Compilar o java

mvn clean install -U

### Compilar a imagem docker

docker compose build

### Subir a imagem

docker compose up -d
