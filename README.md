Se quiser a descrição do desafio está neste repositório aqui [desafioVagaUol](https://github.com/uolhost/test-backEnd-Java)

Para rodar a aplicação certifique-se de que tenha o maven e o java instalados na sua máquina

📌 Desafio de vaga Uol Host

Breve descrição do projeto, explicando o que ele faz e seu propósito.

📦 Pré-requisitos

Antes de iniciar, certifique-se de que você tem os seguintes requisitos instalados:

`Java 21+
Maven 3.8+`

Disclaimer: O maven wrapper está configurado para rodar mesmo sem uma instância do maven instalada, por precaução tenha o maven instalado.

🚀 Passo a passo para rodar o projeto

1️⃣ Clonar o repositório

`git clone https://github.com/ValentePG/DesafioDeVagaUol.git
cd DesafioDeVagaUol`

2️⃣ Instalar dependências

`./mvnw clean install  # Linux/macOS
mvnw.cmd clean install  # Windows`

3️⃣ Rodar a aplicação

`./mvnw spring-boot:run  # Linux/macOS
mvnw.cmd spring-boot:run  # Windows`

Ou, caso prefira executar diretamente o JAR:

`java -jar target/desafio-vaga-uol-0.0.1-SNAPSHOT.jar`

4️⃣ Acessar a API

`API: http://localhost:8080
Documentação Swagger: http://localhost:8080/swagger-ui.html`

5️⃣ Rodar os testes (opcional)

`./mvnw test`

🛠️ Tecnologias Utilizadas

Java 21
Spring Boot 3.4.2

📄 Licença

Este projeto está sob a licença MIT - veja o arquivo LICENSE para mais detalhes.
