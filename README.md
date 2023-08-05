# java--keycloak-admin-client - Criação de Client Credentials no Keycloak com Spring Boot
Repositório para estudo da lib keycloak-admin-client para criação de recursos no Keycloak utilizando o Spring Boot. A aplicação tem como objetivo o cadastro de client credentials no Keycloak, podendo armanezar essas credenciais no AWS Secret Manager. Para tornar o uso mais abrangente, é necessário o cadastro de uma client credential com acesso de `realm-admin` para cada realm do Keycloak, para que seja possível criar as client credentials de aplicações, assim tornando a aplicação mais flexível para criação de recursos no Keycloak,  tornando versatil para utilizar em qualquer realm, tanto no Keycloak quanto no RH SSO.


## Tecnologias

- Keycloak 20.0.3
- Spring 3.1.2
- Mysql
- AWS SDK V2
- Localstack
- Docker

## Execução da aplicação

Para executar a aplicaçao, deve ser executado com um banco de dados, podendo ser um banco de dados `H2` ou `MySQL` para armazenamento das credenciais de administradores de cada realm do Keycloak.

Também existe uma funcionalidade opcional que é o armazenamento das client credentials de aplicações geradas pela aplicação no AWS Secret Manager, criando a secret, criando a policy de acesso a secret, e atribuindo a policy a Role IAM informada. 

Abaixo está detalhado a execução para utilização de cada uma dessas funcionalidades:

### Configurações do Banco de Dados

#### Execução com banco de dados H2

Para executar com o banco de dados `H2`, deve executar a aplicação com o seguinte profile ativo:

```properties
spring.profiles.active=h2
```

#### Execução com banco de dados Mysql

para executar utilizando com o banco de dados Mysql, deve definir as seguintes variaveis de ambiente para execução:

- `DATASOURCE_URL`: url de acesso ao banco de dados
- `DATASOURCE_USERNAME`: usuario de acesso ao banco de dados
- `DATASOURCE_PASSWORD`: senha do usuário de acesso ao banco de dados


### Configuração AWS Secret Manager

Para configurar  o uso da AWS Secret Manager para armazeanr as credenciais, pode ser utilizado localmente `localstack` para simular as funcionalidades da AWS localmente, ou o com o uso da própria `AWS`, lembrando que irá gerar **custos para armazenar as credenciais no AWS Secret Manager**. Abaixo está detalhado como configurar a aplicação para utilizar o armazenamento de credenciais no AWS Secret Manager:


#### Execução com o LocalStack

Para executar com Localstack, primeiro deve ter o Localstack instalado na sua maquina ou ter o container Docker do Localstack em execução, neste link possui as instruções instalação do Localstack. Com o Locastack instalado e devidamente executado, deve executar a aplicação com o profile ativo de  `localstack`, alem do perfil do banco de dados:

```properties
spring.profiles.active=localstack,<perfil-do-banco-de-dados>
```


#### Execução com a AWS
Para executar criando recursos diretamente na AWS, deve ter configurada as credenciais de acesso no host, e com isso deve executar a aplicação com o profile ativo de `aws`, alem do perfil do banco de dados:

```properties
spring.profiles.active=aws,<perfil-do-banco-de-dados>
```

## Referências

- [Keycloak Admin REST Client](https://javadoc.io/doc/org.keycloak/keycloak-admin-client/latest/index.html)
- [Docker](https://www.docker.com/)
- [Localstack](https://localstack.cloud/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [AWS SDK V2](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)
- [AWS Secret Manager](https://aws.amazon.com/pt/secrets-manager/)
- [MySQL](https://www.mysql.com/)