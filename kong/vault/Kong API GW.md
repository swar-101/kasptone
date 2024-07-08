
1. Create a custom Docker network to allow the containers to discover and communicate with each other:

```bash
docker network create kong-net
```

2. Start a Postgres container :

```bash
docker run -d --name kong-database \
 --network=kong-net \
 -p 5432:5432 \
 -e "POSTGRES_USER=kong" \
 -e "POSTGRES_DB=kong" \
 -e "POSTGRES_PASSWORD=kongpass" \
 postgres:13
```

⚠️ `POSTEGRES_USER` and `POSTGRES_DB` values are set to `kong`. This is the default value that Kong Gateway expects.
`POSTGRES_PASSWORD` can be set to any string.


3. Prepare the Kong database:

```bash
docker run --rm --network=kong-net \
-e "KONG_DATABASE=postgres" \
-e "KONG_PG_HOST=kong-database" \
-e "KONG_PG_PASSWORD=kongpass" \
-e "KONG_PASSWORD=test" \
kong/kong-gateway:3.7.1.1 kong migrations bootstrap
```

💡
- `KONG_DATABASE` : Specifies the type of database that Kong is using.
- `KONG_PG_HOST` : The name of the Postgres Docker container that is communicating over the `kong-net` network, from the previous step.
- `KONG_PG_PASSWORD` : The password that you set when bringing up the Postgres container in the previous step.
- `{IMAGE_NAME:TAG} kong migrations bootstrap` : In order, this is Kong Gateway container name and tag, followed by the command to Kong, followed by the command to Kong to prepare the Postgres database.

