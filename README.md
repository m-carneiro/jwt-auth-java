# JWT Authentication em Java

Este projeto é uma implementação simples de autenticação JWT (JSON Web Token) em Java. Ele fornece uma maneira fácil e segura de autenticar e autorizar usuários em aplicações web. O projeto utiliza a biblioteca [JSON Web Token for Java](https://github.com/jwtk/jjwt) (JJWT) para criar e validar tokens.

## Índice

- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Uso](#uso)
- [Testes](#testes)

## Pré-requisitos

Antes de começar, certifique-se de que você tenha o seguinte instalado e configurado em seu sistema:

- Java 11 ou superior
- Maven (opcional, mas recomendado)

## Instalação

1. Clone o repositório em seu computador:

```bash
git clone https://github.com/m-carneiro/jwt-auth-java.git
```

2. Navegue até a pasta do projeto:
```bash
cd jwt-auth-java
```
3. Opcionalmente, compile o projeto com o Maven:
```bash
mvn clean install
```

## Configuração
1. Abra o arquivo `src/main/resources/application.properties` e atualize as propriedades conforme necessário. Por exemplo, você pode definir o tempo de expiração do token JWT e o algoritmo de assinatura.

2. Configure sua conexão de banco de dados e outros detalhes específicos do aplicativo no arquivo `src/main/resources/application.properties`.

## Uso
1. Inicie a aplicação executando a classe principal `com.example.jwtauth.JwtAuthApplication` em sua IDE ou usando o comando Maven:
```bash
mvn spring-boot:run
```
2. A aplicação estará disponível em `http://localhost:8080`. Utilize as rotas de autenticação e autorização fornecidas para gerenciar os tokens JWT.

## Testes
1. Para executar os testes, execute o comando Maven:
```bash
mvn test
```

<hr>

![img.png](img.png)