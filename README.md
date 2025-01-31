# 📌 Desafio de vaga Uol Host

Basicamente um programa que permite a criação de um jogador e busca por jogadores registrados.
Baseado em sua escolha entre `Vingadores` e `Liga da justiça`, o sistema gera um `Codinome` (p.ex `Hulk` ou `Batman`) vindos de 2 arquivos estáticos externos (um xml e outro json) e o usuário é cadastrado com sucesso.
Os Codinomes não podem se repetir.

Descrição detalhada do desafio está [aqui](https://github.com/uolhost/test-backEnd-Java)

# 📦 Pré-requisitos

Antes de iniciar, certifique-se de que você tem os seguintes requisitos instalados:

`Java 21+`
`Maven 3.8+`

**Disclaimer: O maven wrapper está configurado para rodar mesmo sem uma instância do maven instalada, oque permite que os comandos listados abaixo funcionem, porém por precaução tenha o maven instalado.**

# 🚀 Passo a passo para rodar o projeto

1️⃣ Clonar o repositório

`git clone https://github.com/ValentePG/DesafioDeVagaUol.git`<br>
`cd DesafioDeVagaUol`

2️⃣ Instalar dependências

`./mvnw clean install  # Linux/macOS`<br>
`mvnw.cmd clean install  # Windows`

3️⃣ Rodar a aplicação

`./mvnw spring-boot:run  # Linux/macOS`<br>
`mvnw.cmd spring-boot:run  # Windows`

Ou, caso prefira executar diretamente o JAR:

`java -jar target/desafio-vaga-uol-0.0.1-SNAPSHOT.jar`

4️⃣ Acessar a API

`API: http://localhost:8080`<br>
`Documentação Swagger: http://localhost:8080/swagger-ui.html`

5️⃣ Rodar os testes (opcional)

`./mvnw test`

# 🛠️ Tecnologias Utilizadas

`Java 21`
`Spring Boot 3.4.2`

📄 Licença

Este projeto está sob a licença MIT - veja o arquivo LICENSE para mais detalhes.
