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

Then you can download a local LLM model using the following command (you can choose any model available on [Ollama](https://ollama.com/models)):

```shell
docker exec -it ai-ollama ollama run gemma:2b
```

## Use a remote LLM

It is also possible to directly use a remote LLM like ChatGPT by setting your `OPENAI_API_KEY` environment variable and
uncommenting (if it's the case) the `openai` section in the `application.yml` file (and comment the `ollama` section).

## Run the application

After that you should be able to run the application and ask questions using the command line interface:

```shell
mvn spring-boot:run
```

## Use the application

You can use the application by running the following command:

```shell
# ask a question using the RAG system
rag "what are the hobbies of Gilles ?"
# interact with the database using LLM tools
tool "what is the name of the person whos email address is 'john.doe@example.com'"
```
