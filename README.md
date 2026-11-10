# Sistema de Academia — Backend Java

Projeto de console desenvolvido em Java 21 e Spring Boot, com foco nos padrões de projeto **Builder** e **Observer** e no princípio Open/Closed.

> Neste primeiro momento, o projeto não possui frontend nem API REST. Ao iniciar a aplicação, a `AcademiaApplication` chama `AcademiaClient`, que executa um cenário demonstrativo no console.

## Requisitos

- JDK 21
- Maven 3.6.3 ou superior

## Como executar

Na raiz do projeto:

```bash
mvn spring-boot:run
```

Para executar os testes:

```bash
mvn test
```

Também é possível gerar o JAR:

```bash
mvn clean package
java -jar target/academia-backend-0.0.1-SNAPSHOT.jar
```

## O que acontece ao iniciar

1. O Spring cria os repositórios, serviços e observadores.
2. `AcademiaApplication` chama `AcademiaClient` por meio de `CommandLineRunner`.
3. O client cadastra uma aluna.
4. O client cria um plano de treino usando `PlanoTreino.Builder`.
5. O client realiza dois check-ins.
6. Os observadores de auditoria, notificação e estatísticas reagem a cada check-in.
7. O client consulta os registros e encerra a demonstração.

## Padrões de projeto

### Builder

`PlanoTreino` possui uma classe interna `Builder`. Os dados essenciais (`alunoId`, `nome` e `objetivo`) são informados na criação do builder. Os demais valores podem ser configurados por métodos encadeados. O método `build()` valida o plano e cria um objeto imutável.

A lista de exercícios é copiada com `List.copyOf`, evitando alterações externas depois da criação do plano.

### Observer

`CheckInObserver` define o contrato para comportamentos interessados em um check-in. As implementações atuais são:

- `AuditoriaFrequenciaObserver`: registra a ocorrência no console.
- `NotificacaoObserver`: simula uma notificação no console.
- `EstatisticaFrequenciaObserver`: contabiliza os check-ins por aluno.

O `CheckInService` recebe `List<CheckInObserver>` pelo construtor. O Spring injeta todos os componentes que implementam essa interface. Para adicionar um novo comportamento, basta criar outra classe que implemente `CheckInObserver` e registrá-la como componente Spring. O serviço de check-in não precisa ser modificado.

O check-in é salvo antes da notificação. Além disso, uma exceção em um observador é registrada e não impede a execução dos demais observadores.

## Organização dos pacotes

```text
com.academia.backend
├── AcademiaApplication.java
├── client
│   └── AcademiaClient.java
├── model
│   ├── Aluno.java
│   ├── CheckIn.java
│   ├── Exercicio.java
│   ├── PlanoTreino.java
│   └── enums
│       └── GrupoMuscular.java
├── observer
│   ├── CheckInObserver.java
│   └── impl
│       ├── AuditoriaFrequenciaObserver.java
│       ├── EstatisticaFrequenciaObserver.java
│       └── NotificacaoObserver.java
├── repository
│   ├── AlunoRepository.java
│   ├── CheckInRepository.java
│   ├── PlanoTreinoRepository.java
│   └── memory
│       ├── InMemoryAlunoRepository.java
│       ├── InMemoryCheckInRepository.java
│       └── InMemoryPlanoTreinoRepository.java
└── service
    ├── AlunoService.java
    ├── CheckInService.java
    └── PlanoTreinoService.java
```

## Persistência

Os repositórios atuais são implementações em memória (`ConcurrentHashMap`). Os dados existem somente enquanto a aplicação está em execução e são perdidos ao encerrá-la.

As interfaces dos repositórios separam as regras de negócio da tecnologia de armazenamento. Em uma etapa futura, podemos criar implementações com banco de dados sem precisar reescrever os serviços.

## Decisões e limites desta primeira versão

- Não há API REST nem dependência web.
- O client contém dados de demonstração; ele não é uma interface de produção.
- As notificações são simuladas pelo console.
- O projeto ainda não possui autenticação, matrícula, cobrança ou integração com serviços externos.
- A estatística do observador é mantida em memória e contabiliza os eventos recebidos durante a execução atual.
