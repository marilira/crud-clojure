# Clojure Hello World API

Uma API web extremamente simples construída com Clojure e [http-kit](https://github.com/http-kit/http-kit).

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
