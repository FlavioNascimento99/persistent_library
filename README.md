# Documentação
Flavio Nascimento
Novembro de 2024

### Descrição
Sistema desenvolvido com objetivo de aprofundar conhecimentos em persistência de objetos e conceito em separação de responsabilidades com *Data Access Object*. Se trata de um software de gerenciamento de vendas de uma Biblioteca/Livraria com diversas entidades e relacionamentos, a princípio, fazendo uso de um Banco de Dados embarcado, o DB4o (DataBase 4 object), mas que no futuro próximo, será devidamente adaptado para conectar-se a bancos de dados relacional (PostgreSQL) e não-relacional (MongoDB).

### Conceitos
O conceito de separação de responsabilidade nada mais é que, uma maneira mais controlada de desenvolvimento de software, onde separamos a camada de acesso ao banco de dados da camada lógica.


### Entidades
As entidades presentes dentro do sistema são: 
  - Livro
  - Cliente
  - Venda
  - ItemVenda

Livro por si só é uma Entidade independente, diferente de Cliente que faz uma Venda que contém ItemVenda que é composto por uma lista de Livro.

### Consutas base
Na versão base do sistema, existem algumas consultas superficiais, como listagem de livros, clientes e vendas.
  - Quais os clientes que gastaram mais de N?
  - Quais livros foram vendidos na data X?
  - Quais os livros com mais de N vendas?
