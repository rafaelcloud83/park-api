<h1>EstacionaPark - API de gerenciamento de estacionamento</h1> 

<p align="center">
  <img src="https://img.shields.io/static/v1?label=java&message=language&color=blue&style=for-the-badge&logo=JAVA"/>  
  <img src="https://img.shields.io/static/v1?label=spring&message=framework&color=blue&style=for-the-badge&logo=SPRING"/>   
  <img src="https://img.shields.io/static/v1?label=STATUS&message=CONCLUIDO&color=GREEN&style=for-the-badge"/>
</p>

# Introdução

<h2>Com o crescimento constante da frota de veículos nas áreas urbanas, 
a demanda por soluções eficientes de gestão de estacionamento tornou-se 
cada vez mais relevante. Nesse contexto, o Sistema de Estacionamento de 
Veículos (EstacionaPark) surge como uma ferramenta essencial para organizar, controlar e 
otimizar o uso de vagas em estacionamentos públicos e privados. Este 
sistema tem como objetivo principal oferecer uma gestão automatizada, 
segura e prática para o registro de entrada e saída de veículos, cálculo 
de tarifas, controle de vagas disponíveis e geração de relatórios. Com 
uma interface intuitiva, o sistema contribui para a melhoria da mobilidade, 
redução de falhas humanas e aumento da eficiência operacional.</h2>

## Sumário

* [Diagrama de Entidade Relacionamento](#diagrama-de-entidade-relacionamento---der)
* [Requisitos do projeto](#requisitos-do-projeto)
* [Tecnologias e Ferramentas utilizadas](#tecnologias-e-ferramentas-utilizadas)
* [Execução do projeto localmente](#execução-do-projeto-localmente)
    * [com execução manual do jar](#1---com-execução-manual-do-jar)
    * [com imagem Docker Local](#2---com-imagem-docker-local)
* [Documentação Swagger](#documentação-swagger)
* [Endpoints](#endpoints)
    * [Públicos](#públicos)
    * [Usuários](#usuários)
    * [Clientes](#clientes)
    * [Vagas](#vagas)
    * [Estacionamentos](#estacionamentos)

## Diagrama de Entidade Relacionamento - DER

[voltar ao início](#sumário)

![der](imagens/der.png)

## Requisitos do Projeto

[voltar ao início](#sumário)

**1. Requisitos e Configurações**
- A API deverá ser configurada com o Timezone e Locale do país.
- A API deverá usar um sistema de auditoria.
- Registrar a data de criação e última modificação dos registros.
- Registrar o usuário que criou e o último que modificou um registro.
- Configurar o acesso a uma base de dados para o ambiente de desenvolvimento.

**2. Requisitos / Usuários**
- O usuário deverá ter um e-mail, o qual será usado como username (deve ser único).
- O usuário deverá ter uma senha de 6 caracteres.
- O usuário deverá ter um perfil de administrador ou cliente.
- O usuário será criado sem a necessidade de autenticação.
- O usuário poderá ser localizado pelo identificador gerado.
  - O administrador, quando autenticado, poderá recuperar qualquer usuário pelo id.
  - O cliente, quando autenticado, poderá somente recuperar seus próprios dados de usuário.
- O usuário poderá alterar a senha.
  - Apenas quando estiver autenticado e somente o próprio usuário terá essa autorização.
- O administrador poderá listar todos os usuários quando estiver autenticado.
- Documentar todos os recursos criados.
- Realizar testes de integração do tipo ponto a ponto (end-to-end) dos recursos criados.

**3. Requisitos / Autenticação**
- Implementar um sistema de segurança e autenticação com JSON Web Token (JWT).
- Documentar o recurso de autenticação.
- Realizar testes sobre o sistema de autenticação.

**4. Requisitos / Clientes**

O cadastro de cliente só será possível depois de realizado o cadastro como usuário. Um
usuário poderá ter apenas um único cadastro como cliente e um cliente poderá estar
vinculado a um único usuário.
- Todas as ações necessitam de autenticação.
- O cadastro vai conter dados como nome completo, cpf (deve ser único).
- O cadastro deve estar vinculado ao usuário.
- O cliente deverá realizar um cadastro com o nome completo e cpf.
  - Apenas o cliente está autorizado.
- Um cliente poderá ser localizado pelo identificador gerado no cadastro.
  - Apenas o administrador está autorizado.
- Um administrador poderá listar todos os clientes.
- O cliente poderá recuperar os próprios dados via token.
- Documentar todos os recursos criados.
- Realizar testes de integração do tipo ponto a ponto (end-to-end) dos recursos criados.

**5. Requisitos / Vagas**
- Todas ações requerem autenticação e são restritas ao administrador.
- Cada vaga deverá conter um código único que não deve ser o id.
- Cada vaga deverá conter um status de livre ou ocupada.
- Uma vaga poderá ser localizada pelo código.
- Documentar todos os recursos criados.
- Realizar testes de integração do tipo ponto a ponto (end-to-end) dos recursos criados.

**6. Requisitos / Estacionamentos**

Recurso será responsável por controlar as entradas e saídas de veículos.
Apenas clientes cadastrados podem estacionar.
Para estacionar o cliente deve fornecer o CPF cadastrado.

Os requisitos a seguir requerem autenticação e são restritos ao administrador.
- Ao estacionar um veículo as seguintes informações devem ser armazenadas: placa, marca, 
modelo, cor, data de entrada, cpf.
- O processo de registro de um estacionamento deverá gerar um número de recibo único que será 
armazenado junto ao demais dados de entrada.
- Ao deixar o estacionamento as seguintes informações devem ser registradas: data de saída, valor 
do período estacionado e valor do desconto.
- Uma vaga de estacionamento poderá ser ocupada por muitos veículos, desde que, não ao mesmo tempo.
- O desconto deve ser concedido sempre após o cliente completar 10 estacionamentos (entrada e 
saída). A porcentagem de desconto será de 30% sobre o valor a pagar do próximo estacionamento 
completado (entrada e saída).
- O estacionamento deverá ser vinculado a uma vaga e ao cliente proprietário do veículo.
- Ao retirar o veículo o cliente deverá informar o número do recibo gerado na data de entrada.

Os requisitos a seguir requerem autenticação e são acessíveis pelo administrador e/ou cliente.
- Apenas um administrador poderá realizar a operação de check-in e check-out.
- Administrador e cliente possuem permissão para localizar os dados de entrada no estacionamento 
pelo número do recibo.
- O administrador poderá listar os estacionamentos pelo CPF do cliente.
- O cliente poderá listar apenas os seus próprios dados de estacionamentos.

O custo do estacionamento.
- Primeiros 15 Minutos – 5.00
- Primeiros 60 Minutos – 9.25
- A partir dos primeiros 60 Minutos iniciais, inclua no custo de 9.25 a cobrança adicional 
de 1.75 para cada faixa de 15 Minutos adicionais aos primeiros 60 minutos
- Use a tabela de cálculos para entender o processo

Tabela de cálculos (exemplos).

![calculo](imagens/imagem1.png)

- Documentar todos os recursos criados.
- Realizar testes de integração do tipo ponto a ponto (end-to-end) dos recursos criados.

Relatório em Pdf.

- O cliente poderá gerar um relatório em Pdf com sua lista de estacionamentos.

## Tecnologias e Ferramentas utilizadas

[voltar ao início](#sumário)

* **Java 21**
* **Spring Boot 3**
* **Spring Security 6**
* **API REST**
* **IntelliJ IDEA Community Edition 2025**
* **Jaspersoft Studio 6.21.3**
* **PostgreSQL 15**
* **DBGate ou DBeaver**
* **Insomnia**
* **Maven**
* **Docker**
* **Git e GitHub**

## Execução do projeto localmente

[voltar ao início](#sumário)

Para rodar a aplicação, será necessário ter instalado:

* **Git**
* **Java 21**
* **Maven - utilizei a versão 3.9.9**
* **Docker - utilizei a versão 28.2.2**
* **Docker Compose - utilizei a versão 2.35.1**

OBS:. Na primeira execução do projeto, será criado um usuário Admin com senha `123456` e o email `admin@email.com`.

Primeiramente clone o projeto com o comando:

- Clonar com HTTPS

```shell
git clone https://github.com/rafaelcloud83/park-api.git
```

Entre no diretório raiz do projeto com o comando:

```shell
cd park-api
```

Para executar o projeto, existem duas formas:

### 1 - com execução manual do jar

Depois de clonar o projeto e estar no diretório raiz, realize o `build` sem os testes da aplicação executando o comando:

```shell
./mvnw clean install -DskipTests
```

Depois de realizar o `build da aplicação`, será necessário subir o PostgreSQL com o Docker Compose utilizando o comando:

```shell
docker-compose -f docker-compose-postgresql.yaml up -d
```

Depois de subir o PostgreSQL, execute a aplicação com o comando:

```shell
java -jar target/park-api-0.0.1.jar
```

Para parar o projeto, basta executar o comando:

```shell
docker-compose -f docker-compose-postgresql.yaml down
```

### 2 - com imagem Docker Local

Depois de clonar o projeto e estar no diretório raiz. Execute o comando abaixo para gerar a imagem Docker Local, executá-la e vizualizar os Logs.

```shell
docker-compose up --build -d && docker-compose logs -f 
```

Para parar o projeto, basta executar o comando:

```shell
docker-compose down
```

## Documentação Swagger

[voltar ao início](#sumário)

Para acessar a documentação da API e os Endpoints, acesse o link: http://localhost:8080/docs-park.html

![swagger](imagens/imagem2.png)

## Endpoints

[voltar ao início](#sumário)

URL base (baseURL): http://localhost:8080/api/v1

### Públicos

[voltar ao início](#sumário)

Nos endpoints públicos, nenhuma autenticação é necessária.

<br>

**POST** - baseURL/usuarios
- Cria um novo usuário.
- Retorna dados do usuário criado.

Requisição:
```json
{
  "username": "rafael@email.com",
  "password": "123456"
}
```
Resposta: 201
```json
{
  "id": 2,
  "username": "rafael@email.com",
  "role": "CLIENTE"
}
```

<br>

**POST** - baseURL/auth
- Realiza o login do usuário e retorna um token.

Requisição:
```json
{
  "username": "rafael@email.com",
  "password": "123456"
}
```
Resposta: 200
```json
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWZhZWxAZW1haWwuY29tIiwiaWF0IjoxNzQ5NTc3OTMyLCJleHAiOjE3NDk1Nzk3MzIsInJvbGUiOiJDTElFTlRFIn0.YWny6fbUS0pJNTQfTrX1FK_yiNA5jp3QyDtPswrRMxM"
}
```

<br>

### Usuários

[voltar ao início](#sumário)

Contém todas as operações relacionadas aos usuários do sistema.

Nos endpoints de Usuários, a autenticação por token é necessária, menos na criação do usuário.

<br>

**PATCH** - baseURL/usuarios/{id}
- {id} é o id do usuário. Ex: 2.
- Altera a senha do usuário logado.
- Requer autenticação com role Admin ou Cliente.

Requisição:
```json
{
  "senhaAtual":"123456",
  "novaSenha":"654321",
  "confirmaSenha":"654321"
}
```
Resposta: 204
- Requer uma nova autenticação com a nova senha.

<br>

**GET** - baseURL/usuarios/{id}
- {id} é o id do usuário. Ex: 2.
- Retorna um usuário pelo id.
- Requer autenticação com role Admin ou Cliente.

Resposta: 200
```json
{
"id": 2,
"username": "rafael@email.com",
"role": "CLIENTE"
}
```

<br>

**GET** - baseURL/usuarios
- Retorna todos os usuários.
- Requer autenticação com role Admin.

Resposta: 200
```json
[
  {
    "id": 1,
    "username": "admin@email.com",
    "role": "ADMIN"
  },
  {
    "id": 2,
    "username": "rafael@email.com",
    "role": "CLIENTE"
  }
]
```

<br>

### Clientes

[voltar ao início](#sumário)

Contém todas as operações relacionadas aos clientes do sistema.

Nos endpoints de Clientes, a autenticação por token é necessária.

<br>

**POST** - baseURL/clientes
- Cria um novo cliente para o usuário logado.
- Retorna dados do cliente.
- Requer autenticação com role Cliente.

Requisição:
```json
{
  "nome":"Rafael Silva",
  "cpf":"15461776043"
}
```
Resposta: 201
```json
{
  "id": 1,
  "nome": "Rafael Silva",
  "cpf": "15461776043"
}
```

<br>

**GET** - baseURL/clientes/detalhes
- Retorna dados do cliente logado.
- Requer autenticação com role Cliente.

Resposta: 200
```json
{
  "id": 1,
  "nome": "Rafael Silva",
  "cpf": "15461776043"
}
```

<br>

**GET** - baseURL/clientes/{id}
- {id} é o id do cliente. Ex: 1.
- Retorna dados do cliente.
- Requer autenticação com role Admin.

Resposta: 200
```json
{
  "id": 1,
  "nome": "Rafael Silva",
  "cpf": "15461776043"
}
```

<br>

**GET** - baseURL/clientes
- Retorna dados de todos os clientes.
- Requer autenticação com role Admin.

Resposta: 200
```json
{
  "content": [
    {
      "cpf": "15461776043",
      "nome": "Rafael Silva",
      "id": 1
    }
  ],
  "first": true,
  "last": true,
  "size": 5,
  "totalPages": 1,
  "totalElements": 1,
  "page": 0,
  "pageElements": 1
}
```

<br>

### Vagas

[voltar ao início](#sumário)

Contém todas as operações relacionadas às vagas do sistema.

Nos endpoints de Vagas, a autenticação por token é necessária.

<br>

**POST** - baseURL/vagas
- Cria um nova vaga.
- Requer autenticação com role Admin.

Requisição:
```json
{
  "codigo":"A-01",
  "status":"LIVRE"
}
```
Resposta: 201

<br>

**GET** - baseURL/vagas/{codigo}
- {codigo} é o código da vaga. Ex: A-01.
- Retorna dados da vaga.
- Requer autenticação com role Admin.

Resposta: 200
```json
{
  "id": 1,
  "codigo": "A-01",
  "status": "LIVRE"
}
```

<br>

### Estacionamentos

[voltar ao início](#sumário)

Contém todas as operações relacionadas a entrada e saída do estacionamento.

Nos endpoints de Estacionamentos, a autenticação por token é necessária.

**POST** - baseURL/estacionamentos/check-in
- Cria uma entrada (checkIn) de veículos.
- Retorna dados do checkIn.
- Requer autenticação com role Admin.

Requisição:
```json
{
  "placa":"AAA-1111",
  "marca":"Chevrolet",
  "modelo":"Corsa",
  "cor":"Preto",
  "clienteCpf":"15461776043"
}
```
Resposta: 201
```json
{
  "placa": "AAA-1111",
  "marca": "Chevrolet",
  "modelo": "Corsa",
  "cor": "Preto",
  "clienteCpf": "15461776043",
  "recibo": "20250610-193710",
  "dataEntrada": "2025-06-10 19:37:10",
  "vagaCodigo": "A-01"
}
```

<br>

**GET** - baseURL/estacionamentos/check-in/{recibo}
- {recibo} é o número do recibo gerado na entrada do veículo. Ex: 20250610-193710.
- Retorna os dados do checkIn.
- Requer autenticação com role Admin ou Cliente.

Resposta: 200
```json
{
  "placa": "AAA-1111",
  "marca": "Chevrolet",
  "modelo": "Corsa",
  "cor": "Preto",
  "clienteCpf": "15461776043",
  "recibo": "20250610-193710",
  "dataEntrada": "2025-06-10 19:37:10",
  "vagaCodigo": "A-01"
}
```

<br>

**PUT** - baseURL/estacionamentos/check-out/{recibo}
- {recibo} é o número do recibo gerado na entrada do veículo. Ex: 20250610-193710.
- Gera uma saída (checkOut) de veículo pelo recibo.
- Retorna dados do checkOut.
- Requer autenticação com role Admin.

Resposta: 200
```json
{
  "placa": "AAA-1111",
  "marca": "Chevrolet",
  "modelo": "Corsa",
  "cor": "Preto",
  "clienteCpf": "15461776043",
  "recibo": "20250610-193710",
  "dataEntrada": "2025-06-10 19:37:10",
  "dataSaida": "2025-06-10 19:44:55",
  "vagaCodigo": "A-01",
  "valor": 5.00,
  "desconto": 0.00
}
```

<br>

**GET** - baseURL/estacionamentos/cpf/{cpf}
- {cpf} é o número do cpf do cliente. Ex: 15461776043.
- Retorna todos os estacionamentos do cliente pelo cpf.
- Requer autenticação com role Admin.

Resposta: 200
```json
{
  "content": [
    {
      "dataEntrada": "2025-06-10 19:37:10",
      "recibo": "20250610-193710",
      "placa": "AAA-1111",
      "marca": "Chevrolet",
      "modelo": "Corsa",
      "cor": "Preto",
      "dataSaida": "2025-06-10 19:44:55",
      "valor": 5.00,
      "desconto": 0.00,
      "clienteCpf": "15461776043",
      "vagaCodigo": "A-01"
    }
  ],
  "first": true,
  "last": true,
  "size": 5,
  "totalPages": 1,
  "totalElements": 1,
  "page": 0,
  "pageElements": 1
}
```

<br>

**GET** - baseURL/estacionamentos/cliente
- Retorna todos os estacionamentos do cliente logado.
- Requer autenticação com role Cliente.

Resposta: 200
```json
{
  "content": [
    {
      "dataEntrada": "2025-06-10 19:37:10",
      "recibo": "20250610-193710",
      "placa": "AAA-1111",
      "marca": "Chevrolet",
      "modelo": "Corsa",
      "cor": "Preto",
      "dataSaida": "2025-06-10 19:44:55",
      "valor": 5.00,
      "desconto": 0.00,
      "clienteCpf": "15461776043",
      "vagaCodigo": "A-01"
    }
  ],
  "first": true,
  "last": true,
  "size": 5,
  "totalPages": 1,
  "totalElements": 1,
  "page": 0,
  "pageElements": 1
}
```

<br>

**GET** - baseURL/estacionamentos/relatorio
- Retorna um relatório em PDF com todos os estacionamentos do cliente logado.
- Requer autenticação com role Cliente.

Resposta: 200
