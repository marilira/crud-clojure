# Clojure Hello World API

Uma API web extremamente simples construída com Clojure e [http-kit](https://github.com/http-kit/http-kit).

Este repositório inclui um arquivo de coleção do [Insomnia](https://insomnia.rest/) para facilitar o teste da API.
#### Como usar
1. Abra o Insomnia.
2. Vá em **Application → Preferences → Data → Import Data → From File**.
3. Selecione o arquivo `Insomnia.yaml` presente neste repositório.
4. A coleção será importada com todas as rotas e exemplos de requisição.

## Endpoints
### GET `/`
- **Descrição:** Retorna um texto simples confirmando que a API está ativa.
- **Response:**
```json
{
	"message": "CRUD API in Clojure"
}
```
### PUT `/product`
- **Descrição:** Adiciona um novo produto.
- **Request:**
```json
{"name": "p1", "price": 2}
```
- **Response:**
```json
{
	"name": "p1",
	"price": 2
}
```
### GET `/products`
- **Descrição:** Retorna todos os produtos.
- **Response:**
```json
{
	"1": {
		"name": "p1",
		"price": 2
	},
	"2": {
		"name": "p2",
		"price": 2
	}
}
```
### GET `/product/:id`
- **Descrição:** Retorna os dados do produto especificado.
- **Response:**
`/product/1`
```json
{
	"name": "p1",
	"price": 2
}
```
### PATCH `/product/:id`
- **Descrição:** Atualiza os dados do produto especificado.
- **Request:**
```json
{"name": "p7"}
```
- **Response:**
`/product/1`
```json
{
	"name": "p7",
	"price": 2
}
```
### DELETE `/product/:id`
- **Descrição:** Remove o produto especificado.
- **Response:**
`/product/1`
```json
{
	"message": "Produto 1 foi removido"
}
```

## Pré-requisitos

Você precisará da **Clojure CLI** instalada no seu sistema. Siga as instruções oficiais aqui:
[clojure.org/guides/install_clojure](https://clojure.org/guides/install_clojure)

## Como Rodar

A maneira de rodar o servidor é através da Clojure CLI usando o alias definido no `deps.edn`:

```bash
clj -M:run
```

O servidor iniciará na porta `8080`. Você pode acessar em: [http://localhost:8080](http://localhost:8080)

Se está no Linux, pode testar o servidor com o seguinte comando:

```bash
curl http://localhost:8080
```
