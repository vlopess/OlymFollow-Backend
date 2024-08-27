![Logo](https://github.com/vlopess/OlymFollow-FrontEnd/blob/main/olymfollow-front/src/assets/LogoOlympicsFollow.png?raw=true)
# Olympics Follow Backend

Sistema de Visualização do Quadro de Medalhas das Olimpíadas, desenvolvido utilizando Spring Boot no backend e React.js no frontend, além de
implementar mensageria com RabbitMQ.

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)

#### Esse projeto foi desenvolvido utilizando as seguintes tecnologias:
![](https://skillicons.dev/icons?i=java,spring,rabbitmq,postgres,eclipse,idea)
## Autores

- [@vlopess](https://www.github.com/vlopess)
- [@sergiocerq](https://www.github.com/sergiocerq)

## Funcionalidades

- Endpoints para visualização de quadro de medalhas
- Endpoints para cadastro de usuários
- Notificação por email

## Documentação da API

`baseURL = "http://localhost:8084/olympics-follow-api/";`

### User Controller

#### Busca o usuário pelo ID

```curl
  GET baseURL + /user
```

| Parâmetro (query) | Tipo       | Descrição                                       |
|:------------------| :--------- |:------------------------------------------------|
| `id`              | `string` | **Obrigatório**. O id do usuário que você quer. |

#### Busca o usuário pelo username

```curl
  GET baseURL + /user/${username}
```

| Parâmetro   | Tipo       | Descrição                                             |
| :---------- | :--------- |:------------------------------------------------------|
| `username`      | `string` | **Obrigatório**. O Username do usuário que você quer. |

#### Busca todos os usuários

```curl
  GET baseURL + /user/GetAll
```

`Sem parâmetro`

#### Apaga o usuário pelo ID
```curl
  DELETE baseURL + /user/${id}
```

| Parâmetro | Tipo       | Descrição                                              |
|:----------| :--------- |:-------------------------------------------------------|
| `id`      | `string` | **Obrigatório**. O id do usuário que você quer apagar. |

#### Cria um usuário

```curl
  POST baseURL + /user/register
```

| Parâmetro  | Tipo       | Descrição                                       |
|:------------------| :--------- |:------------------------------------------------|
| `id`              | `string` | **Obrigatório**. O id do usuário que você quer. |

#### Segue um país, para receber notificação

```curl
  POST baseURL + /user/subscribe/${userID}/${countryID}
```

| Parâmetro      | Tipo       | Descrição                         |
|:-----------------| :--------- |:----------------------------------|
| `userID`         | `string` | **Obrigatório**. O id do usuário. |
| `countryID`      | `string` | **Obrigatório**. O id do país.    |

#### Deixa de seguir um país

```curl
  DELETE baseURL + /user/unsubscribe/${userID}/${countryID}
```

| Parâmetro       | Tipo       | Descrição                         |
|:-----------------| :--------- |:----------------------------------|
| `userID`         | `string` | **Obrigatório**. O id do usuário. |
| `countryID`      | `string` | **Obrigatório**. O id do país.    |z

### Auth Controller

#### Faz login com google

```curl
  POST baseURL + /authenticate/google
```

| Parâmetro (Request body) | Tipo     | Descrição                                                            |
|:-------------|:---------|:---------------------------------------------------------------------|
| `{"accessToken": "string"}`         | `objeto` | **Obrigatório**. O token de acesso do usuário fornecido pelo google. |


### Medal Controller

#### Busca todas as medalhas

```curl
  GET baseURL + /OlymFollow/medalhas
```
`Sem parâmetro`

#### Cadastra uma medalha (Obs: somente usuários admin podem cadastrar)

```curl
  POST baseURL + /OlymFollow/medalhas/cadastrar
```

| Parâmetro                                                                                          | Tipo     | Descrição                            |
|:---------------------------------------------------------------------------------------------------|:---------|:-------------------------------------|
| `{ "tipoMedalha": "string","nomeAtleta": "string","esporte": "string","countryID": "string"}`      | `objeto` | **Obrigatório**. A model de medalha. |


### Esporte Controller

#### Busca todas os esportes

```curl
  GET baseURL + /esportes
```
`Sem parâmetro`

### Country Controller

#### Busca todas os países

```curl
  GET baseURL + /countries
```
`Sem parâmetro`

## Feedback

Se você tiver algum feedback, por favor nos deixe saber por meio de [victorldev8@gmail.com](mailto:victorldev8@gmail.com)

## Licença

[MIT](https://choosealicense.com/licenses/mit/)

