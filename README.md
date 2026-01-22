# 🚂 Trilhos de Trens do Minecraft Usando Threads Para Controle de Concorrência

**Autor:** Gabriel Marcone Magalhaes Santos  
**Matrícula:** 202410374  
**Início:** 12/03/2025  
**Última alteração:** 04/05/2025  

---

## 📖 Sobre o Projeto
Este projeto consiste numa simulação de trens em **JavaFX** com estética do jogo **Minecraft**, desenvolvido para a disciplina de **Programação Concorrente**. Diferente da versão anterior, este projeto foca na implementação de mecanismos de **exclusão mútua** para gerenciar o acesso a regiões críticas (trilhos compartilhados). A aplicação permite testar e comparar algoritmos clássicos de sincronização de processos em um ambiente visual e dinâmico.

---

## 🎯 Objetivos do Projeto
✅ Garantir a **exclusão mútua** em duas regiões críticas distintas do trajeto.  
✅ Implementar e visualizar três soluções clássicas: **Variável de Travamento**, **Estrita Alternância** e **Algoritmo de Peterson**.  
✅ Gerenciar a execução paralela de entidades independentes através de **Threads**.  
✅ Evitar colisões e garantir a segurança dos fluxos através de variáveis **volatile** para visibilidade entre threads.  
✅ Permitir o controle individual de velocidade e a troca de cenários em tempo real.

---

## 🛠️ Componentes Utilizados
- **Java 8+** com **JavaFX** - Interface baseada em **FXML** (`scene1.fxml`, `scene2.fxml`).  
- **Multithreading**: Implementação das classes `ThreadTrem1` e `ThreadTrem2` herdando de `Thread`.  
- **Mecanismos de Sincronização**:
  - **Variável de Travamento**: Uso de flags inteiras para sinalizar ocupação.
  - **Estrita Alternância**: Uso de variável `turno` para revezamento obrigatório.
  - **Solução de Peterson**: Combinação de interesse e turno para evitar espera ocupada ineficiente.
- **Sincronização de UI**: Uso de `Platform.runLater()` para atualizar a interface gráfica a partir das threads de simulação.
- **Persistência**: **DataSingleton** para carregar as configurações de posição e algoritmo selecionadas.

---

## ⚙️ Como Executar
1. Certifique-se de ter o **Java 8** (ou superior com JavaFX) configurado.
2. Clone o repositório:
   ```bash
   git clone https://github.com/gabrielmarcone/Problema-dos-Trens-com-Controle-de-Concorrencia.git
