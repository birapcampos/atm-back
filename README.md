Interview GSW
Trata-se de uma aplicação que simula a entrega de notas quando um cliente efetua um saque em um caixa eletrônico.

Requisitos
Os requisitos básicos são os seguintes:

1) Entregar o menor número de notas;
2) Somente será possível sacar o valor solicitado com as notas disponíveis;
3) Saldo do cliente será cadastrado;
4) Quantidade de notas infinita;
5) Notas disponíveis de R$ 100,00; R$ 50,00; R$ 20,00 e R$ 10,00
6) O Cliente não poderá entrar no negativo;
7) Fazer o CRUD de cliente juntamente com seu saldo;
8) Garantir no máximo 5 usuários ao mesmo tempo.

Instruções para executar o projeto

O projeto pode ser executado pela ide de sua preferencia ou através do arquivo .jar localidado no projeto na pasta abaixo:
\atm-back\target\atm-back.jar

Linha de comando : java -jar atm-back.jar --spring.datasource.username=h2sa --spring.datasource.password
 
Após subir a aplicação executar através do navegador: http://localhost:8080/swagger-ui.html#/
Para acessar o banco de dados e verificar os dados: http://localhost:8080/h2
User Name: h2sa Password: 

