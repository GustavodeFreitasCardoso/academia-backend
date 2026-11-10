# Sistema de Academia — Backend Java

Projeto de console desenvolvido em Java 21 e Spring Boot para o Trabalho Final de Design Patterns (Parte 1), tema **Academia**. O foco são os padrões **Builder** e **Observer**.

> O projeto não possui frontend nem API REST. Ao iniciar, a `AcademiaApplication` chama o `AcademiaClient`, que executa um cenário demonstrativo no console.

## Descrição do sistema

A aplicação gerencia, de forma simplificada, uma academia. As três entidades principais são:

| Entidade | Descrição |
|---|---|
| `Aluno` | Pessoa cadastrada na academia (pode estar ativa ou inativa). |
| `PlanoTreino` | Plano de treino de um aluno: nome, objetivo, frequência semanal, exercícios e observações. |
| `CheckIn` | Registro de presença do aluno na academia, com data e hora. |

`Exercicio` e `GrupoMuscular` são apenas apoio ao plano de treino (objeto de valor e enum).

Os dados ficam **somente em memória** (não há banco de dados) e são perdidos ao encerrar a aplicação.

## Tecnologias e pré-requisitos

- Java (JDK) 21
- Maven 3.6.3 ou superior
- Spring Boot, usado apenas para injeção de dependências e inicialização
- JUnit 5 para testes

## Como executar

Na raiz do projeto:

```bash
mvn spring-boot:run
```

Para executar os testes:

```bash
mvn test
```

Para gerar e executar o JAR:

```bash
mvn clean package
java -jar target/academia-backend-0.0.1-SNAPSHOT.jar
```

## Exemplo de utilização

Ao executar, o cenário demonstrativo realiza o fluxo completo:

1. Cadastra uma aluna (`Ana Souza`).
2. Cria um plano de treino usando `PlanoTreino.builder(...)`.
3. Realiza dois check-ins. Os três observadores reagem a cada um.
4. Remove o observador de notificação e realiza um terceiro check-in (o observador removido não é avisado).
5. Consulta os check-ins registrados e o total contabilizado pelo observador de estatísticas.

Saída aproximada (os IDs e horários variam a cada execução):

```text
[CLIENT] Aluna cadastrada: Ana Souza (ID: <uuid>)
[CLIENT] Plano 'Treino A' criado para Ana Souza.
[CLIENT] Frequência: 3 dias por semana.
         - Agachamento livre (PERNAS): 3 séries de 12 repetições
         - Supino com halteres (PEITO): 3 séries de 10 repetições
         - Remada baixa (COSTAS): 3 séries de 12 repetições

[CLIENT] Realizando dois check-ins para demonstrar o Observer...
[AUDITORIA] Check-in <uuid> registrado para o aluno <uuid> em <horário>.
[NOTIFICACAO] O aluno <uuid> realizou um check-in.
[ESTATISTICA] Total de check-ins do aluno <uuid>: 1.
...
[CLIENT] Removendo o observador de notificação e fazendo um terceiro check-in...
[AUDITORIA] ...
[ESTATISTICA] Total de check-ins do aluno <uuid>: 3.
```

## Padrões de projeto

### Builder

**Problema que resolve:** um plano de treino tem vários atributos, alguns obrigatórios (aluno, nome, objetivo) e outros opcionais (frequência, exercícios, observações). Sem o Builder, seria necessário um construtor com muitos parâmetros, fácil de errar a ordem, ou vários construtores diferentes. O Builder permite montar o objeto passo a passo, com nomes de métodos claros, e validar tudo antes de criar o plano.

**Classes envolvidas:**

- `PlanoTreino`: produto final, imutável, com construtor privado.
- `PlanoTreino.Builder`: classe interna que recebe os dados obrigatórios, configura os opcionais de forma encadeada e valida no `build()`.
- `Exercicio` e `GrupoMuscular`: dados usados na montagem do plano.
- `AcademiaClient`: usa o Builder no cenário de demonstração.

**Como funciona:**

```java
PlanoTreino plano = PlanoTreino.builder(aluno.id(), "Treino A", "Condicionamento físico")
        .frequenciaSemanal(3)
        .adicionarExercicio(new Exercicio("Agachamento livre", GrupoMuscular.PERNAS, 3, 12))
        .observacoes("Priorizar a execução correta dos movimentos.")
        .build();
```

O `build()` rejeita planos inválidos (frequência fora de 1 a 7 ou nenhum exercício). A lista de exercícios é copiada com `List.copyOf`, então o plano não muda depois de criado.

### Observer

**Problema que resolve:** quando um aluno faz check-in, vários comportamentos precisam reagir (auditoria, notificação, estatística). Sem o Observer, o `CheckInService` precisaria conhecer e chamar cada um deles, e cada novo comportamento exigiria alterar o serviço. Com o Observer, o serviço apenas avisa quem estiver inscrito.

**Classes envolvidas:**

- `CheckInService`: o **Subject** (observável). Mantém a lista de observadores, permite `registrarObserver` e `removerObserver` e notifica todos após salvar o check-in.
- `CheckInObserver`: a interface (contrato) dos observadores, com o método `aoRealizarCheckIn(CheckIn)`.
- `AuditoriaFrequenciaObserver`: registra a ocorrência no console.
- `NotificacaoObserver`: simula uma notificação no console.
- `EstatisticaFrequenciaObserver`: contabiliza os check-ins por aluno.
- `CheckIn`: o evento enviado aos observadores.

**Como funciona:** o Spring injeta no `CheckInService` todos os componentes que implementam `CheckInObserver`, e o serviço também permite registrar e remover observadores em tempo de execução. O check-in é salvo antes da notificação, e uma falha em um observador é registrada em log sem impedir os demais.

Para adicionar um novo comportamento, basta criar uma classe que implemente `CheckInObserver`. O `CheckInService` não precisa ser modificado (princípio Open/Closed).

## Organização dos pacotes

```text
com.academia.backend
├── AcademiaApplication.java
├── client/        AcademiaClient
├── model/         Aluno, CheckIn, Exercicio, PlanoTreino, enums/GrupoMuscular
├── observer/      CheckInObserver, impl/ (Auditoria, Notificacao, Estatistica)
├── repository/    interfaces e memory/ (implementações em memória)
└── service/       AlunoService, CheckInService, PlanoTreinoService
```

## Testes

- `PlanoTreinoBuilderTest`: valores padrão, imutabilidade da lista e validações do Builder.
- `CheckInServiceTest`: notificação dos observadores, falha isolada de um observador e registro/remoção de observadores.

## Decisões e limites desta versão

- Não há API REST, banco de dados, autenticação, matrícula ou cobrança.
- O client contém dados de demonstração; ele não é uma interface de produção.
- As notificações são simuladas pelo console.
- O "Plano" do projeto é um plano de treino do aluno.

## Declaração de utilização de IA

- **Modelo utilizado:** Claude (Anthropic), Claude Sonnet 5.5.
- **Partes com auxílio da IA:**
  - Análise e diagnóstico do repositório em relação aos requisitos do professor.
  - Adição de `registrarObserver` e `removerObserver` no `CheckInService`, com testes e ajuste do cenário de demonstração.
  - Correção do `pom.xml` (versão do Java).
  - Redação deste README.
  - _(Equipe: complementar aqui qualquer outra parte da implementação inicial que tenha recebido auxílio de IA.)_
- Todo o código foi revisado e testado pela equipe.

## Contribuição individual

> A definir pela equipe.

| Integrante | Contribuição |
|---|---|
| _Nome do integrante 1_ | _a preencher_ |
| _Nome do integrante 2_ | _a preencher_ |
| _Nome do integrante 3_ | _a preencher_ |
