# Spring AI RAG & tool calling

This application supports both OpenAI and Ollama as LLM providers through Spring profiles.

## Configuration Profiles

### Using OpenAI (Default)

1. Set your API keys:
```shell
export OPENAI_API_KEY=your-openai-api-key-here
export TAVILY_API_KEY=your-tavily-api-key-here
```

2. Start infrastructure services:
```shell
docker compose up -d
```

3. Run the application with OpenAI profile:
```shell
mvn spring-boot:run -Dspring-boot.run.profiles=openai
```

### Using Ollama (Local LLM)

1. Download the required models:
```shell
# Download the chat model (deepseek-r1:1.5b)
docker exec -it ai-ollama ollama pull deepseek-r1:1.5b

# Download the embedding model
docker exec -it ai-ollama ollama pull nomic-embed-text
```

You can also use other models from [Ollama models](https://ollama.com/models) by setting the environment variables `OLLAMA_CHAT_MODEL` and `OLLAMA_EMBEDDING_MODEL`.

2. Set your Tavily API key for web search:
```shell
export TAVILY_API_KEY=your-tavily-api-key-here
```

3. Run the application with Ollama profile:
```shell
mvn spring-boot:run -Dspring-boot.run.profiles=ollama
```

## Environment Variables

### OpenAI Profile
- `OPENAI_API_KEY` - Your OpenAI API key (required)
- `TAVILY_API_KEY` - Your Tavily API key for web search (required for web search functionality)

### Ollama Profile
- `TAVILY_API_KEY` - Your Tavily API key for web search (required for web search functionality)
- `OLLAMA_BASE_URL` - Ollama server URL (default: http://localhost:11400)
- `OLLAMA_CHAT_MODEL` - Chat model name (default: deepseek-r1:1.5b)
- `OLLAMA_EMBEDDING_MODEL` - Embedding model name (default: nomic-embed-text)

## Use the application

You can use the application by running the following command:

```shell
# Basic LLM query
basic "what is the capital of France?"

# Query the vector store with RAG
vector "what are the hobbies of Gilles?"

# Use database tools
tool "what is the name of the person whose email address is 'john.doe@example.com'"

# Web search with LLM
web "what is the latest news about AI?"
```
