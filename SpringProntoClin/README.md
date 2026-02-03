# Aplicação SpringBoot

## Endpoints e exemplos de requisição

### Endpoints

Acesso a página home
```
http://localhost:8081/
```

Login POST
```
http://localhost:8081/auth/login
```

Requisições POST
```
http://localhost:8081/auth/register/adm
```
```
http://localhost:8081/auth/register/paciente
```
```
http://localhost:8081/auth/register/prosaude
```
```
http://localhost:8081/consulta
```

### Requisições GET
Só retornam dados do usuário logado no sistema

```
http://localhost:8081/adm/me
```
```
http://localhost:8081/paciente/me
```
```
http://localhost:8081/profSaude/me
```
```
http://localhost:8081/consulta/profissional/consultas
```
```
http://localhost:8081/consulta/paciente/consultas
```

Requisições PUT
Só realiza o update de dados do usuário logado no sistema

```
http://localhost:8081/adm/atualiza
```
```
http://localhost:8081/paciente/atualiza
```
```
http://localhost:8081/profSaude/atualiza
```
```
http://localhost:8081/consulta/{idConsulta}
```

Requisições DELETE

```
http://localhost:8081/profSaude/{idProfissionalSaude}
```
```
http://localhost:8081/consulta/{idConsulta}
```
### Exemplos de requisições

POST

````angular2html
ADMIN
{
	"nome": "maria",http://localhost:8081/consulta/{idConsulta}
	"cpf": "12345678",
	"email": "rita@gmail.com",
	"senha": "senha123",
	"userrole": "ADMIN"
}
````
````angular2html
PACIENTE
{
    "nomePaciente": "rita",
    "nomeSocial": "rita",
    "telefonePaciente": "2222",
    "cpfPaciente": "2222",
    "dataNascimento": "1990-05-15",
    "sexoPaciente": "F",
    "email": "maria.paciente@gmail.com",
    "senha": "senha123",
    "userrole": "PACIENTE"
}
````
````angular2html
Profissional Saude
{
    "nomeProfissionalSaude": "Dr. Rita Silva",
    "cpfProfissionalSaude": "38765432100",
    "especialidadeMedica": "Cardiologia",
    "telefoneProfissionalSaude": "987654321",
    "CRM": "CRM123156",
    "status": "ATIVO",
    "email": "rita.silva@medico.com",
    "senha": "senha123",
    "userrole": "PROFSAUDE"
}
````
````angular2html
CONSULTA
{
    "idconsulta": "null",
    "idPaciente": 5,
    "idProfissionalSaude": 3,
    "nomeProfissionalSaude": "Dr. Rita Silva",
    "nomePaciente": "maria",
    "nomeSocial": "maria",
    "dataConsulta": "2025-02-19T14:30:00",
    "especialidadeMedica": "Cardiologia"
}
````

PUT
````angular2html
PACIENTE
{
    "email": "rita.gomes@gmail.com",
    "senha": "123456",
    "nomesocial": "Rita Silva",
    "telefonepaciente": "5584987654321"
}
````
````angular2html
Profissional Saude
{
    "email": "rita.gomes@medica.com",
    "senha": "123456",
    "especialidademedica": "Ginecologia",
    "telefoneprofissionalsaude": "8554987654321",
    "status": "ATIVO"
}
````
````angular2html
Consulta
{
    "idconsulta": "null",
    "idPaciente": 2,
    "idProfissionalSaude": 3,
    "nomeProfissionalSaude": "Dr. Rita Silva",
    "nomePaciente": "maria",
    "nomeSocial": "maria",
    "dataconsulta": "2025-02-11T14:30:00",
    "especialidadeMedica": "Cardiologia"
}
````