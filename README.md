# Command Line Interface AI

To run the application you need first to create docker volumes for the database and the vector container:

```shell
docker volume create postgresql-data
docker volume create qdrant-data
```

## Use a local LLM

The ollama image/container should be automatically be downloaded/built from the `spring-boot-docker-compose` dependency.  

Or you can build it manually using the following command:

```shell
docker compose -f docker-compose.yaml up -d
```

Then you cand download a local LLM model using the following command (you can choose any model available on [Ollama](https://ollama.com/models)):

```shell
docker exec -it ai-ollama ollama run gemma:2b
```

## Use a remote LLM

It is also possible to use directly a remote LLM like Mistral by setting your `MISTRALAI_API_KEY` environment variable and
uncommenting the `mistralai` section in the `application.yml` file (and comment the `ollama` section).

## Run the application

After that you should be able to run the application and ask questions using the command line interface:

```shell
mvn spring-boot:run
# a shell will be opened in which you can ask questions
ask "what are the hobbies of Gilles ?"
```
