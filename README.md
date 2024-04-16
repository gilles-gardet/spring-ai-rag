# Command Line Interface AI

To run the application you need first to download a LLM into ollama.  
The ollama image/container should be automatically be downloaded/built from the `spring-boot-docker-compose` dependency.

You will then have to download a LLM (the smallest version of gemma for example):

```shell
docker exec -it ai-ollama ollama run gemma:2b
```

It is also possible to use directly a remote LLM like Mistral by setting your `MISTRALAI_API_KEY` environment variable and
uncommenting the `mistralai` section in the `application.yml` file (and comment the `ollama` section).

> [!CAUTION]
> Actually there is a limitation on PgVector which allows at most 2000 dimensions for HNSW indexes (see spring-ai
> documentation).  
> We should then choose a large-language-model which would have less "embedding dimensions" (like gemma:2b for example)

After that you should be able to run the application and ask questions using the command line interface:

```shell
ask "what are the hobbies of Gilles ?"
```
