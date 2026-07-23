# 📚 Boletim Online

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

Sistema web de gerenciamento escolar desenvolvido como **Projeto de Conclusão do Curso Técnico em Informática da FAETEC**.

O funcionamento do sistema foi inspirado no modelo de gerenciamento acadêmico utilizado pela FAETEC, sendo desenvolvido do zero para aplicar, na prática, conhecimentos de desenvolvimento web, banco de dados e arquitetura MVC.

---

# 🎯 Objetivo

Desenvolver um sistema web capaz de gerenciar alunos, professores e administradores, permitindo o controle acadêmico de forma organizada e intuitiva.

O projeto foi desenvolvido para consolidar os conhecimentos adquiridos durante o Curso Técnico em Informática, aplicando tecnologias utilizadas no mercado de desenvolvimento Java.

---

# 🚀 Tecnologias Utilizadas

| Categoria | Tecnologias |
|-----------|-------------|
| **Back-end** | Java, Spring Boot, Spring Data JPA |
| **Front-end** | HTML5, CSS3, JavaScript, Thymeleaf |
| **Banco de Dados** | MySQL |
| **Ferramentas** | IntelliJ IDEA, MySQL Workbench, Git e GitHub |

---

# ✨ Funcionalidades

- Sistema de Login
- Painel Administrativo
- Cadastro de Alunos
- Cadastro de Professores
- Cadastro de Administradores
- Gerenciamento de Disciplinas
- Controle de Notas
- Controle de Frequência
- Controle de Aprovação
- Banco de Dados Relacional
- Controle de Usuários
- Interface Responsiva
- Arquitetura MVC

---

# 👥 Níveis de Acesso

### 👨‍💼 Administrador

- Gerenciar usuários
- Cadastrar alunos
- Cadastrar professores
- Gerenciar disciplinas
- Alterar configurações do sistema

### 👨‍🏫 Professor

- Visualizar disciplinas
- Lançar notas
- Registrar frequência
- Editar avaliações

### 👨‍🎓 Aluno

- Consultar boletim
- Visualizar notas
- Consultar frequência

---

# 🏗️ Arquitetura

O projeto foi desenvolvido utilizando a arquitetura **MVC (Model-View-Controller)**.

```
src
├── controller
├── model
├── repository
├── service
├── templates
└── static
```

---

# 📸 Capturas de Tela

## 🔐 Tela de Login

A autenticação é realizada utilizando e-mail, senha, CPF e data de nascimento.

![Tela de Login](images/TelaLogin.jpeg)

---

## 🛠️ Painel Administrativo

O painel administrativo centraliza as principais funções do sistema, permitindo ao administrador gerenciar usuários, acessar listas de alunos e professores e realizar as operações administrativas.

![Painel Administrativo](images/TelaAdm.jpeg)

---

## 👤 Cadastro de Usuário


Esta tela permite o cadastro de novos alunos, professores e administradores, registrando todas as informações necessárias para utilização do sistema.

![Cadastro de Usuários](images/TelaCadastro.jpeg)

---

## 📚 Lista de Alunos

Exibe todos os usuários cadastrados como alunos, permitindo ao administrador consultar, localizar e selecionar registros para edição.

![Lista de Alunos](images/ListaAlunos.jpeg)

---

## 👨‍🏫 Lista de Professores

Exibe todos os usuários cadastrados como professores, permitindo ao administrador consultar, localizar e selecionar registros para edição.

![Lista de Alunos](images/ListaProfessor.jpeg)

---

## 👤 Minha Conta

*(Adicionar imagem)*

```markdown
![MinhaConta](images/MinhaConta.jpeg)
```

---

## ✏️ Editar Usuário

Permite ao administrador atualizar os dados cadastrais de alunos e professores, mantendo as informações sempre corretas e atualizadas.

![Editar Usuário](images/EditarUsuario.jpeg)

---

## 👤 Minha Conta

Área destinada ao usuário autenticado, onde é possível visualizar suas informações pessoais e atualizar os próprios dados cadastrais.

![Minha Conta](images/TelaMinhaConta.jpeg)

---

## 📚 Área do Professor

Nesta página, o professor visualiza as disciplinas e períodos sob sua responsabilidade, acessando rapidamente as funcionalidades de lançamento de notas e frequência.

![Área do Professor](images/TelaProfessor.jpeg)

---

## 📝 Lançamento de Notas

Permite ao professor registrar e editar as notas dos alunos, além de cadastrar avaliações para cada disciplina e turma.

![Lançamento de Notas](images/BoletimTurma.jpeg)

---

## 📅 Lançamento de Frequência

Tela destinada ao registro da frequência dos alunos, permitindo informar o total de aulas ministradas e a quantidade de presenças de cada estudante.

![Lançamento de Frequência](images/LançarFrequencia.jpeg)

---

## 🎓 Boletim do Aluno

Nesta área, o aluno pode acompanhar seu desempenho acadêmico, consultando notas, frequência e a situação em cada disciplina.

![Boletim do Aluno](images/BoletimAluno.jpeg)

# ▶️ Como Executar

1. Clone este repositório.
2. Configure o banco de dados MySQL.
3. Atualize as credenciais no arquivo `application.properties`.
4. Execute o projeto utilizando o IntelliJ IDEA.
5. Acesse:

```
http://localhost:8080
```

---

# 👨‍💻 Desenvolvedor

**Jhonatan Miguel Souza Silva**

Curso Técnico em Informática — FAETEC

GitHub:
https://github.com/jmiguelsouza

---

# 📄 Licença

Projeto desenvolvido exclusivamente para fins acadêmicos.
