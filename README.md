# ProntoClin
## Sistema de gestão clínica médica: ProntoClin
### Funcionalidades principais do sistema:

1. **Agendamento de consultas**: Pacientes podem agendar, cancelar ou remarcar consultas online com médicos de diferentes especialidades.
2. **Gerenciamento de prontuários médicos**: Os médicos podem acessar e atualizar os prontuários médicos dos pacientes, registrando diagnósticos, prescrições e exames realizados.
3. **Notificações automáticas**: O sistema envia notificações automáticas aos pacientes lembrando-os das consultas e de exames periódicos.
4. **Relatórios de saúde**: O sistema gera relatórios de saúde para os pacientes, como histórico de consultas, exames realizados e evolução de tratamentos.
5. **Autenticação e controle de acesso**: Diferentes níveis de acesso (pacientes, médicos, administradores) devem ser implementados para garantir a privacidade dos dados


## Tecologias

Certifique de ter instalado em sua estação de trabalho o [docker](https://www.docker.com/) para executar o sistema.
Com o docker instalado seu computador as seguintes tecnologias serão baixadas e executadas por ele:

![postgres](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white)

![springboot](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white)

![react](https://img.shields.io/badge/React-61DAFB.svg?style=for-the-badge&logo=React&logoColor=black)



## Banco de dados

![Esquema do Bando de Dados](documentacao/EsquemaDB/dbProntoClin.jpeg)

#### Como acessar o banco de dados

1. Acesse o pgadmin pelo host `localhost:15432` no browser
2. No campo login informe o email  prontoclin@gmail.com e senha postgres 
    > conforme foi definido no seu arquivo docker-compose.yml nos campos PGADMIN_DEFAULT_EMAIL e PGADMIN_DEFAULT_PASSWORD

3. Ao fazer login, selecione Servers --> Register --> Server ou em Add New Server
4. Em Register Server preencha os campos

    ~~~~
    General

        Name: postgres (aleatorio)
    ~~~~
    ~~~~
    Connection

        Host name/address: cnprontoclin (nome do container)
        Port: 5432
        Maintenance database: dbprontoclin
        username: prontoclin
        password: postgres
    ~~~~
    > Preencha os dados em Connection conforme foi definido no seu container do Postgres no arquivo docker-compose.yml 


### Como executar o sistema

Execute os comando a seguir no diretórtio onde está o arquivo docker-compose.yml para

Criar e executar todos os containers:

```
docker compose up --build -d
```

Parar todos os containers em execução

```
docker compose stop
```

Remover todos os containers ou remover todos os volumes dos containers 

```
docker compose down 

ou

docker compose down --volumes
```

Executar todos os containers criados

```
docker compose start
```
