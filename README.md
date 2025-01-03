# Crud api #
Requisitos obrigatórios para rodar a aplicação
- java 1.11 ou superior (Pois existem lambdas e não são suportadas em versões passadas)

## Especificações da aplicação ##
- Banco em memória h2

## Para rodar a aplicação ##
Será necessário um ambiente de desenvolvimento com maven 4.0, java 1.11 ou superior. Após a configuração
- Escolha o local do projeto
- Clone o projeto do GIT
- Realize um clean install do maven e builde o projeto

## Para testar a aplicação ##
Extraia a collection produto.postman_collection.json e importe no postman para realizar os testes
- Ao lado do workspace no canto superior direito clique em import
- Arraste ou selecione o arquivo 

## Falando um pouco sobre a apicação ##
Essa solução foi pensada exclusivamente para o problema apresentado.
Foi desenvolvida pensando em reutilização de codigo, metodos eficazes e sem duplicidade de codigo.
Utilizando JPA como especificação e H2 como banco de dados.
