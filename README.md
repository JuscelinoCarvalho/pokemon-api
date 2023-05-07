# pokemon-api
Trabalho para entrega do trabalho de Testes Automatizados I


# Pokémon Tester

Para colocar os conhecimentos adquiridos neste módulo à prova, o desafio de vocês será o
de construir uma API e combinar diferentes técnicas e ferramentas de automação de testes.
A API terá como temática o universo de Pokémon e, para isso, vocês deverão utilizar a
pokeapi.co para extrair dados e estatísticas que serão necessários para a implementação
das funcionalidades descritas abaixo.

Funcionalidades
Consulta de informações de um pokémon
Vocês deverão disponibilizar um endpoint capaz de retornar alguns dados sobre o pokémon
desejado.
Input: nome [ex: pikachu, charmander, mewtwo, etc] (string)
Outputs:
● nome (string)
● altura (int/long)
● peso (int/long)
● áreas de encontro (list)
● stats (list)
● tipos (list)
Referências:
● https://pokeapi.co/docs/v2#pokemon
● https://pokeapi.co/docs/v2#locations-section

Linha evolutiva
Vocês deverão disponibilizar um endpoint capaz de retornar a linha evolutiva do pokémon
desejado.
Input: nome [ex: pikachu, charmander, rattata, etc] (string)
Output:
● formas (list)
{
"forms": [
"rattata",

"raticate"

}
Referências:
● https://pokeapi.co/docs/v2#pokemon-species
● https://pokeapi.co/docs/v2#evolution-section

Batalhas
Vocês deverão disponibilizar um endpoint capaz de comparar dois pokémon e indicar qual
tem a maior probabilidade de sair vitorioso em um embate, com base nos atributos de cada
um.
Critérios:
● Os valores individuais de cada atributo devem ser somados para que os valores
totais de ambos os pokémons sejam comparados
● Em caso de empate, deve ser informado DRAW
● O endpoint deve ser um POST
Input:
{
"challenger": "charmander",
"challenged": "pikachu"
}
Output:
{
"winner": "pikachu" // "DRAW" em caso de empate
}
Referência: https://pokeapi.co/docs/v2#pokemon

Avaliação
Para a avaliação da atividade, serão considerados os seguintes pontos:
● Modelagem da API – a API deve ser modelada seguindo as práticas do REST,
incluindo os (mas não limitado aos) devidos códigos HTTP para cenários de sucesso
e falha
● Arquitetura MVC – models, services, controllers e repositories bem definidos
● Testes unitários com JUnit e Mockito

● Testes de integração com Spring Boot Test
● Cobertura mínima de 70% do código – para verificação de cobertura, utilizar o
Jacoco
● Testar ao menos um cenário de falha para cada fluxo das services, controllers e
repositories
AVISO: não serão consideradas exclusões de métodos e classes na cobertura de testes!

Entrega
A atividade deverá ser entregue via repositório público do Github. Para todos os efeitos,
garantir que o professor terá permissão para baixar (ou clonar) o repositório.
AVISO: códigos com erro de compilação serão descontados na nota final!

Considerações
O trabalho deverá ser feito em Java (17+), com Spring Boot (2+), e entregue pelo Github.
Quanto aos testes, estes deverão ser feitos utilizando JUnit, Mockito e Spring Boot Test.
Por fim, o gerenciador de dependências (Maven ou Gradle) e a IDE ficam à critério de cada
um.
Para a avaliação, será considerada a versão final contida na branch main. A data de
entrega será combinada com a turma, em aula.
Os trabalhos poderão ser realizados individualmente ou em grupos de até três integrantes.

Bom trabalho,
Divirtam-se!
